package com.ciastek.library.common

import android.content.Context

class StringProviderImpl(private val context: Context): StringProvider {

    override fun getString(stringId: Int): String =
        context.getString(stringId)

    override fun getString(stringId: Int, str1: String, str2: String): String =
            context.getString(stringId, str1, str2)
}
