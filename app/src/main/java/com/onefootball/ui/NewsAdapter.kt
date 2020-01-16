package com.onefootball.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.onefootball.R
import com.onefootball.ui.model.News
import kotlinx.android.synthetic.main.news_card_item.view.*
import kotlin.properties.Delegates

class NewsAdapter(private val action: (News) -> Unit? = {}) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var items: MutableList<News> by Delegates.observable(mutableListOf()) { _, _, _ -> notifyDataSetChanged() }

    companion object {

        const val LIST_ITEM_TYPE = 0
        const val BANNER_TYPE = 1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {

            BANNER_TYPE -> {
                ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.news_card_item,
                        parent,
                        false
                    )
                )
            }
            else -> {
                ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.news_list_item,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return BANNER_TYPE
        }

        return LIST_ITEM_TYPE
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = items[position]
        holder.bind(news)

        holder.itemView.custom_view_news_content_root.setOnClickListener {
            action.invoke(news)
        }

        holder.itemView.news_view.setOnClickListener{
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
