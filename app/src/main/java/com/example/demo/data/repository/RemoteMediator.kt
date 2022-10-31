package com.example.demo.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.demo.data.api.ApiService
import com.example.demo.data.data.model.CharacterData
import com.example.demo.data.data.model.RemoteKeys
import com.example.demo.data.room.DataBase
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RemoteMediator(
    private val api: ApiService,
    private val db: DataBase,
) : RemoteMediator<Int, CharacterData>() {
override suspend fun initialize(): InitializeAction {
    return InitializeAction.LAUNCH_INITIAL_REFRESH
}

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, CharacterData>
    ): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as Int
        }

        try {
                val response = api.getDataFromAPI(page).body()
            val isEndOfList =
                response?.info?.next == null
                        || response.toString().contains("error")
                        || response.results.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.getCharacterDao().removeAll()
                    db.getRemoteKeyDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1

                val dataList : MutableList<CharacterData> = response?.results!!.toMutableList()

                val keys = mutableListOf<RemoteKeys>()
                dataList.forEach { entity->
                    val keyEntity = RemoteKeys(entity.id!!, prevKey = prevKey, nextKey = nextKey)
                    keys.add(keyEntity)
                }

                val tables = mutableListOf<CharacterData>()
                dataList.forEach { entity->
                    tables.add(entity)
                }

                db.getRemoteKeyDao().insertAll(keys)
                db.getCharacterDao().insertAll(tables)

            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, CharacterData>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CharacterData>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.getRemoteKeyDao().remoteKeysCharacterId(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, CharacterData>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { character -> db.getRemoteKeyDao().remoteKeysCharacterId(character.id!!) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, CharacterData>): RemoteKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { character -> db.getRemoteKeyDao().remoteKeysCharacterId(character.id!!) }
    }}