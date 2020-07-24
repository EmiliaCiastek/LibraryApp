package com.ciastek.library.common

import androidx.annotation.StringRes

interface StringProvider {

    fun getString(@StringRes stringId: Int): String

    fun getString(@StringRes stringId: Int, str1: String, str2: String): String
}
