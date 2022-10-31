package com.example.demo.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demo.data.data.model.RemoteKeys

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: MutableList<RemoteKeys>)

    @Query("SELECT * FROM remoteKey WHERE characterId = :id")
    suspend fun remoteKeysCharacterId(id: Int): RemoteKeys?

    @Query("DELETE FROM remoteKey")
    suspend fun deleteAll()
}