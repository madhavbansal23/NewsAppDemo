package com.maddy.newsdemo.di

import android.app.Application
import dagger.Provides
import javax.inject.Singleton
import androidx.room.Room
import com.maddy.newsdemo.room.AppDB
import com.maddy.newsdemo.room.NewsDao
import dagger.Module


@Module(includes = [AppModule::class])
class DBModule(mApplication: Application) {
    private val newsDB: AppDB = Room.databaseBuilder(mApplication, AppDB::class.java, "News.db").build()

    @Singleton
    @Provides
    internal fun providesRoomDatabase(): AppDB {
        return newsDB
    }


    @Singleton
    @Provides
    internal fun providesProductDao(newsDB: AppDB): NewsDao {
        return newsDB.newsDao()
    }
}