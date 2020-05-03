package com.maddy.newsdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maddy.newsdemo.Appcontroller
import com.maddy.newsdemo.backend.repository.NewsRepository
import com.maddy.newsdemo.model.BaseModel
import com.maddy.newsdemo.model.NewsData
import java.util.*
import javax.inject.Inject


class NewsViewModel : ViewModel() {

    var newsLiveData: MutableLiveData<BaseModel<ArrayList<NewsData>>>

    init {
        this.newsLiveData = MutableLiveData()
        Appcontroller.app.mApiComponent.inject(this)
    }

    @Inject
    lateinit var mRepository: NewsRepository

    fun getNewsList(): MutableLiveData<BaseModel<ArrayList<NewsData>>> {
        newsLiveData = mRepository.fetchNewsList()
        return newsLiveData
    }

}