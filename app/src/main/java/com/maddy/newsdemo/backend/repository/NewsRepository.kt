package com.maddy.newsdemo.backend.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.maddy.newsdemo.Appcontroller
import com.maddy.newsdemo.backend.api.NewsApi
import com.maddy.newsdemo.di.ApiComponent
import com.maddy.newsdemo.model.BaseModel
import com.maddy.newsdemo.model.NewsData
import com.maddy.newsdemo.room.NewsDao
import com.maddy.newsdemo.util.Constants
import com.maddy.newsdemo.util.Utils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class NewsRepository {

    val newsListLiveData: MutableLiveData<BaseModel<ArrayList<NewsData>>> = MutableLiveData()

    init {
        val apiComponent: ApiComponent = Appcontroller.app.mApiComponent
        apiComponent.inject(this)
    }

    @Inject
    lateinit var newsApi: NewsApi

    @Inject
    lateinit var newsDao: NewsDao

    fun fetchNewsList(): MutableLiveData<BaseModel<ArrayList<NewsData>>> {

        // Fetch offline news
        fetchNewsOffline()

        if (!Utils.isNetworkAvailable()) {
            Toast.makeText(
                Appcontroller.app.applicationContext,
                "Internet not connected",
                Toast.LENGTH_LONG
            ).show()
        }

        val postListInfo: Call<BaseModel<ArrayList<NewsData>>> =
            newsApi.getPaymentTypes("in", Constants.NEWS_API_KEY, 50)
        postListInfo.enqueue(object : Callback<BaseModel<ArrayList<NewsData>>> {
            override fun onFailure(call: Call<BaseModel<ArrayList<NewsData>>>, t: Throwable) {
//                val errorData = BaseModel("error", "error loading list", arrayListOf<NewsData>())
//                newsListLiveData.postValue(errorData)
                Toast.makeText(
                    Appcontroller.app.applicationContext,
                    "Error loading data, Try again later",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<BaseModel<ArrayList<NewsData>>>,
                response: Response<BaseModel<ArrayList<NewsData>>>
            ) {
                if (response.code() == 200) {
                    // Insert news in DB
                    insertNews(response.body()?.articles)
                    //newsListLiveData.postValue(response.body());
                } else {
//                  val errorData = BaseModel("error", "error loading list", arrayListOf<NewsData>())
//                  newsListLiveData.postValue(errorData)
                    Toast.makeText(
                        Appcontroller.app.applicationContext,
                        "Error loading data, Try again later",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
        return newsListLiveData
    }

    // Live data triggered when all records from DB loaded
    private fun fetchNewsOffline() {
        doAsync {
            val result = newsDao.getNews()
            uiThread {
                val newsArraylist = ArrayList<NewsData>()
                newsArraylist.addAll(result)
                val dbData = BaseModel("ok", "", newsArraylist)
                newsListLiveData.postValue(dbData)
            }
        }
    }

    fun insertNews(newsList: ArrayList<NewsData>?) {
        doAsync {

            var needsUpdate = false
            if (newsList != null) {
                for (item in newsList) {
                    val inserted = newsDao.insertNews(item)
                    if (inserted == -1L) {
                        val updated = newsDao.insertNews(item)
                        if (updated > 0) {
                            needsUpdate = true
                        }
                    } else {
                        needsUpdate = true
                    }
                }
            }

            uiThread {
                if (needsUpdate)
                    fetchNewsOffline()
            }
        }
    }


}