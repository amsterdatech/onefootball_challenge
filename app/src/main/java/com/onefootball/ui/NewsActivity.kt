package com.onefootball.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.onefootball.R
import com.onefootball.ui.model.News
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class NewsActivity : AppCompatActivity() {

    private val adapter by lazy {
        NewsAdapter {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(it.newsLink))
            )
        }
    }

    val viewModel = NewsViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(viewModel)


        viewModel.success.observe(this, Observer {
            adapter.items += it
            adapter.notifyDataSetChanged()

        })

        viewModel.error.observe(this, Observer {

        })

        viewModel.loading.observe(this, Observer {

        })

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(activity_news_recycler_view) {
            layoutManager =
                LinearLayoutManager(this@NewsActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.activity_vertical_margin).toInt()
                )
            )
            adapter = this@NewsActivity.adapter
        }
    }
}