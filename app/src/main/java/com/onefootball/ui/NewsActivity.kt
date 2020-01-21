package com.onefootball.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.onefootball.R
import com.onefootball.commons.Browser
import com.onefootball.commons.MarginItemDecoration
import com.onefootball.commons.SingleLiveEvent
import com.onefootball.extensions.hide
import com.onefootball.extensions.show
import com.onefootball.extensions.snackBar
import com.onefootball.ui.model.News
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsActivity : AppCompatActivity() {

    private val adapter by lazy {
        NewsAdapter {
            Browser.openIntern(this@NewsActivity, it.newsLink)
        }
    }

    private val viewModel: NewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(viewModel)

        viewModel.success.observe(this, Observer {
            handleSuccess(it)
        })

        viewModel.error.observe(this, Observer {
            handleError(it)
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

    private fun handleError(it: SingleLiveEvent<Throwable>?) {
        it?.getContentIfNotHandled()?.let {
            activity_news_recycler_view.hide()
            activity_news_loading.hide()
            activity_news_root
                .snackBar(getString(R.string.generic_error)) {
                    viewModel.loadNews()
                }
                .show()
        }
    }

    private fun handleSuccess(it: List<News>) {
        activity_news_recycler_view.show()
        adapter.updateItems(it)
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