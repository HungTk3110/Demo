package com.example.demo.data.data.model

import com.example.demo.data.data.model.CharacterData
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("info")
    val info: Info? = null,
    @SerializedName("results")
    val results: MutableList<CharacterData>
)