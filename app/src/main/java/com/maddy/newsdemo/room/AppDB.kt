package com.maddy.newsdemo.room

import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maddy.newsdemo.model.DataConverter
import com.maddy.newsdemo.model.NewsData

@TypeConverters(DataConverter::class)
@Database(entities = [NewsData::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}