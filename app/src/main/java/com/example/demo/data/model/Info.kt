package com.example.demo.data.data.model

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("count")
    val count : Int? = 0,
    @SerializedName("pages")
    val pages : Int? = 0,
    @SerializedName("next")
    val next : String? ="",
    @SerializedName("prev")
    val prev : String? = ""
)