package com.example.firebasetutor

import android.text.InputFilter
import android.text.Spanned

class MinLengthInputFilter(private val minLength: Int) : InputFilter {
    override fun filter(
        source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int
    ): CharSequence? {
        val newLength = (dest?.length ?: 0) + (source?.length ?: 0) - (dend - dstart)
        return if (newLength < minLength) "" else null
    }
}
