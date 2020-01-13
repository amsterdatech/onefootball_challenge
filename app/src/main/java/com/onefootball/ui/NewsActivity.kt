package com.onefootball.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.onefootball.R
import com.onefootball.extensions.hide
import com.onefootball.extensions.show
import com.onefootball.extensions.snackBar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsActivity : AppCompatActivity() {

    private val adapter by lazy {
        NewsAdapter {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(it.newsLink))
            )
        }
    }

    private val viewModel: NewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(viewModel)

        viewModel.success.observe(this, Observer {
            activity_news_recycler_view.show()
            adapter.items += it
            adapter.notifyDataSetChanged()

        })

        viewModel.error.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                activity_news_recycler_view.hide()
                activity_news_loading.hide()
                activity_news_root
                    .snackBar(getString(R.string.generic_error)) {
                        viewModel.loadNews()
                    }
                    .show()
            }
        })

        viewModel.loading.observe(this, Observer {
            if (it) {
                activity_news_loading.show()
            } else {
                activity_news_loading.hide()
            }
        })

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(activity_news_recycler_view) {
            layoutManager =
                LinearLayoutManager(
                    this@NewsActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.activity_vertical_margin).toInt()
                )
            )
            adapter = this@NewsActivity.adapter
        }
    }
}