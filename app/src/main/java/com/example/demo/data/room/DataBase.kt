package com.example.demo.data.room

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demo.data.data.model.CharacterData
import com.example.demo.data.data.model.RemoteKeys
import com.example.demo.data.room.CharacterDao
import com.example.demo.data.room.RemoteKeyDao

@Database(entities = [CharacterData::class , RemoteKeys::class] , version = 1 )
abstract class DataBase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao
    abstract fun  getRemoteKeyDao(): RemoteKeyDao

}