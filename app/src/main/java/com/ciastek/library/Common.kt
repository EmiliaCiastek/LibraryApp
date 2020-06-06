package com.ciastek.library

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import java.util.Properties

fun getApiUrl(resources: Resources): String {
    resources.openRawResource(R.raw.config).use { rawConfig ->
        val properties = Properties()
        properties.load(rawConfig)
        return properties.getProperty("api_url")
    }
}

fun showErrorMessage(context: Context?) {
    context?.let {
        Toast.makeText(context,
                       context.getString(R.string.not_user_friendly_error_message),
                       Toast.LENGTH_LONG).show()
    }
}
