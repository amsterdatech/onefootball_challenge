package com.onefootball.ui

import androidx.recyclerview.widget.DiffUtil
import com.onefootball.ui.model.News

class NewsDiffUtils(
    private val freshNews: List<News>,
    private val oldNews: List<News>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldNews.size
    }

    override fun getNewListSize(): Int {
        return freshNews.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val freshItem = freshNews[newItemPosition]
        val oldItem = oldNews[oldItemPosition]
        return freshItem.title.equals(oldItem.title)
    }


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val freshItem = freshNews[newItemPosition]
        val oldItem = oldNews[oldItemPosition]
        return freshItem.title.equals(oldItem.title) &&
                freshItem.imageURL.equals(oldItem.imageURL) &&
                freshItem.newsLink.equals(oldItem.newsLink) &&
                freshItem.resourceName.equals(oldItem.resourceName) &&
                freshItem.resourceURL.equals(oldItem.resourceURL)
    }
}