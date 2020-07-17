package com.ciastek.library.common

import android.content.Context
import android.widget.Toast
import com.ciastek.library.R

fun showErrorMessage(context: Context?, message: String? = null) {
    context?.let {
        Toast.makeText(context,
                       message ?: context.getString(R.string.not_user_friendly_error_message),
                       Toast.LENGTH_LONG).show()
    }
}
