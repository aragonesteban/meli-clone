package com.meli.shared.extensions

import android.content.Context
import android.widget.Toast

fun Context.showToastMessage(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}