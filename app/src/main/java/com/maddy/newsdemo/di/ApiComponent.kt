package com.maddy.newsdemo.di

import com.maddy.newsdemo.backend.api.NewsApi
import com.maddy.newsdemo.backend.repository.NewsRepository
import com.maddy.newsdemo.viewmodel.NewsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiHelper::class, AppModule::class, DBModule::class])
interface ApiComponent {

    val newsApi: NewsApi

    fun inject(repo: NewsRepository)
    fun inject(newsVM: NewsViewModel)
}