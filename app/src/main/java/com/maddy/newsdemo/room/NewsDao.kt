package com.maddy.newsdemo.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maddy.newsdemo.model.NewsData

@Dao
interface NewsDao {

    /* Method to insert the movies fetched from api
     * to room */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    fun insertNews(news: NewsData?):Long

    /* Method to fetch the movies stored locally */
    @Query("SELECT * FROM `NewsData`")
    fun getNews(): List<NewsData>

}