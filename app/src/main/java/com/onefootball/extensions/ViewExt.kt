package com.onefootball.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(
    snackBarText: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    messageAction: CharSequence = "Reload",
    listener: () -> Unit? = {}
): Snackbar {
    return Snackbar.make(this, snackBarText, duration)
        .setAction(messageAction) {
            listener.invoke()
        }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
