package com.maddy.newsdemo.di

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maddy.newsdemo.backend.api.NewsApi
import com.maddy.newsdemo.backend.repository.NewsRepository
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiHelper {

    val BASE_URL = "https://newsapi.org/v2/";

    @Provides
    @Singleton
    internal fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        gsonBuilder.setDateFormat("yyyy-MM-dd")
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideOkhttpClient(cache: Cache, application: Application): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
        client.readTimeout(30, TimeUnit.SECONDS)
        client.connectTimeout(15, TimeUnit.SECONDS)
        client.writeTimeout(60, TimeUnit.SECONDS)
        client.addInterceptor(interceptor)
        return client.build();
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build();
    }


    @Provides
    @Singleton
    internal fun getNewsApi(retroFit: Retrofit): NewsApi {
        return retroFit.create(NewsApi::class.java)
    }


    @Provides
    @Singleton
    fun provideNewsRepository():NewsRepository{
        return NewsRepository()
    }

}