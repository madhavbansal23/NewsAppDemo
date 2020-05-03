package com.maddy.newsdemo.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromSource(value:NewsData.Source): String {
        val gson = Gson()
        val type = object : TypeToken<NewsData.Source>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSource(value: String): NewsData.Source {
        val gson = Gson()
        val type = object : TypeToken<NewsData.Source>() {}.type
        return gson.fromJson(value, type)
    }
}