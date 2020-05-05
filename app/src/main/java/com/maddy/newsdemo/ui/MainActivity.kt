package com.maddy.newsdemo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.maddy.newsdemo.adapter.NewsListAdapter
import com.maddy.newsdemo.databinding.ActivityMainBinding
import com.maddy.newsdemo.model.NewsData
import com.maddy.newsdemo.viewmodel.NewsViewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutResourceId: Int
        get() = com.maddy.newsdemo.R.layout.activity_main;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbarText("Top News")
        hideBack()
        getNewsApi()
    }

    private fun getNewsApi() {
        val newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        newsViewModel.getNewsList().observe(this, Observer { newsData ->
            hideProgress()
            showData()
            setAdapter(newsData.articles);
        })
    }
    // setting adapter
    private fun setAdapter(newsList: ArrayList<NewsData>) {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = NewsListAdapter(this@MainActivity, newsList)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )

    // onclick adapter
            (adapter as NewsListAdapter).onItemClick = { url ->
                val i: Intent = Intent(this@MainActivity, DetailActivity::class.java)
                i.putExtra("url", url)
                startActivity(i)
            }
        }


    }

}
