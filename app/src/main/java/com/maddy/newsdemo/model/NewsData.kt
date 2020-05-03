package com.maddy.newsdemo.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["title"])
data class NewsData(
    val title: String,
    val description: String,
    val author: String,
    @SerializedName("urlToImage")
    val url_to_image: String,
    @SerializedName("publishedAt")
    val published_at: String,
    val source: Source,
    val url:String

) {
    data class Source(
        val name: String
    )
}
