package com.example.demo.data.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CharacterData")
data class CharacterData(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo
    @SerializedName("name")
    val name: String? = "",
    @ColumnInfo
    @SerializedName("species")
    val species: String? = "",
    @ColumnInfo
    @SerializedName("image")
    val image: String? = "")
