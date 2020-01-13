package com.onefootball.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val SPLASH_DURATION = 5000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed(
            {

                goToSearchResultsActivity(this)
            },
            SPLASH_DURATION
        )
    }

    private fun goToSearchResultsActivity(activity: Activity) {
        startActivity(Intent(activity, NewsActivity::class.java))
        finish()
    }
}