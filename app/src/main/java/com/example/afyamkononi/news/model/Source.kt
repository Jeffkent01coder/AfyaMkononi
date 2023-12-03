package com.example.afyamkononi.news.model

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
): java.io.Serializable