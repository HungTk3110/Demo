package com.example.demo.data.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "remoteKey")
data class RemoteKeys(
    @PrimaryKey
    @ColumnInfo
    val characterId: Int?,
    @ColumnInfo
    val prevKey:Int?,
    @ColumnInfo
    val nextKey:Int?
)
