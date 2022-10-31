package com.example.demo.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demo.data.data.model.CharacterData

@Dao
interface CharacterDao {

    @Query("SELECT * FROM CharacterData")
    fun getAllCharacterData(): PagingSource<Int, CharacterData>

    @Query("DELETE FROM CharacterData")
    suspend fun removeAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: MutableList<CharacterData>)
}