package com.onefootball.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.onefootball.R
import com.onefootball.model.News
import kotlinx.android.synthetic.main.news_item.view.*
import kotlin.properties.Delegates

class NewsAdapter(private val action: (News) -> Unit? = {}) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var items: List<News> by Delegates.observable(mutableListOf()) { _, _, _ -> notifyDataSetChanged() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = items[position]
        holder.bind(news)

        holder.itemView.custom_view_news_content_root.setOnClickListener {
            action.invoke(news)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: News) {
            itemView.news_title.text = item.title
            itemView.news_view.load(url = item.imageURL)
            itemView.resource_icon.load(url = item.resourceURL)
            itemView.resource_name.text = item.resourceName
        }
    }
}
