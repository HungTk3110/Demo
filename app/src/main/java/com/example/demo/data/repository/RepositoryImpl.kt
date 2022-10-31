package com.example.demo.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.demo.data.api.ApiService
import com.example.demo.data.data.model.ApiResponse
import com.example.demo.data.data.model.CharacterData
import com.example.demo.data.repository.PagingSource
import com.example.demo.data.repository.RemoteMediator
import com.example.demo.data.repository.Repository
import com.example.demo.data.room.DataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private var apiService: ApiService,
    private val db: DataBase,
) : Repository {

    private var list = mutableListOf<CharacterData>()

    override fun getApi(): Flow<PagingData<CharacterData>> {
        return Pager(
            config = PagingConfig(pageSize = 10, maxSize = 200),
            pagingSourceFactory = { PagingSource(apiService) }
        ).flow
    }

    override fun getApiV2(): Flow<PagingData<CharacterData>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10, maxSize = 50),
            remoteMediator = RemoteMediator(apiService, db)
        ) {
            db.getCharacterDao().getAllCharacterData()
        }.flow
    }

    override suspend fun getApiWithPage(page: Int ): MutableList<CharacterData>{
        return ( kotlin.runCatching {
            apiService.getDataFromAPI(query = page).body()?.results
        }.getOrNull()) ?: mutableListOf()
    }

    override fun getApiWithLiveData(): MutableLiveData<MutableList<CharacterData>> {
        return MutableLiveData<MutableList<CharacterData>>().also {live->
            val call = apiService.getDataFromApi(1)
            call.enqueue(object : Callback<ApiResponse> {
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.v("DEBUG : ", t.message.toString())
                }

                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>,
                ) {
                    live.value = response.body()?.results
                }
            })
        }
    }

    override fun getApiWWithWorker(): MutableList<CharacterData> {
        val call = apiService.getDataFromApi(3)

        call.enqueue(object : Callback<ApiResponse>{
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                list = response.body()?.results!!
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }
        })
        return list
    }


}