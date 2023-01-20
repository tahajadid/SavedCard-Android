package com.example.savedcards.util.numberCardUtil

import android.widget.EditText

object ScratchCardUtil {
    /**
     * function that verify phone number
     */
    fun isValidNumber(phoneNumber: String): Boolean {
        return phoneNumber.length == 19
    }
    /**
     * function to add separator to number card
     */
    fun setSeparatorFormatNumber(editText: EditText) {
        editText.addTextChangedListener(object : SeparatorTextWatcher(' ', 4) {
            override fun onAfterTextChanged(text: String) {
                editText.run {
                    setText(text)
                    setSelection(text.length)
                }
            }
        })
    }

    /**
     * function that add separator to scratch number passed number
     * Like 1111 2222 33...
     */
    fun addSeparatorToNumber(number: String): String {
        return number.chunked(4).joinToString(" ")
    }
}
