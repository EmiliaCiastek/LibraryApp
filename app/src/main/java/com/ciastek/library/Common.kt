package com.ciastek.library

import android.content.Context
import android.widget.Toast

fun showErrorMessage(context: Context?) {
    context?.let {
        Toast.makeText(context,
                       context.getString(R.string.not_user_friendly_error_message),
                       Toast.LENGTH_LONG).show()
    }
}
