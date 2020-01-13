package com.onefootball.commons

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession
import androidx.core.content.ContextCompat
import com.onefootball.R


object Browser : CustomTabsServiceConnection() {
    private const val CHROME_PACKAGE_NAME = "com.android.chrome"

    private var customTabsSession: CustomTabsSession? = null
    private var customTabsClient: CustomTabsClient? = null
    private var customTabsIntent: CustomTabsIntent? = null

    override fun onCustomTabsServiceConnected(name: ComponentName, client: CustomTabsClient) {
        customTabsClient = client
        customTabsClient?.warmup(0L)
        customTabsSession = customTabsClient?.newSession(null)
    }

    override fun onServiceDisconnected(name: ComponentName) {
        customTabsClient = null
        customTabsSession = null
    }

    fun warm(context: Context) {
        CustomTabsClient.bindCustomTabsService(context, CHROME_PACKAGE_NAME, this)

        customTabsIntent = CustomTabsIntent.Builder(customTabsSession)
            .setShowTitle(true)
            .enableUrlBarHiding()
            .addDefaultShareMenuItem()
            .setShowTitle(true)
            .setExitAnimations(context, android.R.anim.fade_in, android.R.anim.fade_out)
            .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .build()

        customTabsIntent?.intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    fun cool(context: Context) {
        context.unbindService(this)
        customTabsClient = null
        customTabsSession = null
    }

    fun openIntern(context: Context, url: String?) {
        if (url != null) {
            try {
                customTabsIntent?.launchUrl(context, Uri.parse(url))
            } catch (notFoundException: ActivityNotFoundException) {
                Toast.makeText(context, R.string.not_found_activity, Toast.LENGTH_SHORT).show()
            }
            return
        }
        Toast.makeText(context, R.string.not_found_activity, Toast.LENGTH_SHORT).show()
    }

    fun openExternal(context: Context, url: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (url != null && intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(
                Intent.createChooser(
                    intent,
                    context.getString(R.string.chooser_title)
                )
            )
            return
        }

        Toast.makeText(context, R.string.not_found_activity, Toast.LENGTH_SHORT).show()
    }
}