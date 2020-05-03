package com.maddy.newsdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.maddy.newsdemo.BR
import com.maddy.newsdemo.R
import com.maddy.newsdemo.databinding.ItemNewsBinding
import com.maddy.newsdemo.model.NewsData

class NewsListAdapter(var context: Context, var newsList: ArrayList<NewsData>) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    lateinit var onItemClick: ((String) -> Unit?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemNewsBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_news, parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList.get(position))
    }

    override fun getItemCount(): Int = newsList.size

    class ViewHolder(
        val binding: ItemNewsBinding, val onItemClick: (String) -> Unit?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {
            binding.setVariable(BR.news, data)
            binding.executePendingBindings()

            binding.clRoot.setOnClickListener {
                onItemClick.invoke(binding.news?.url.toString())
            }
        }
    }
}