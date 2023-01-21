package com.example.savedcards.util

import com.example.savedcards.data.Shortcut

object Constants {

    const val MY_CARDS = "MY_CARDS"

    val LIST_OF_SHORTCUTS = arrayListOf(
        Shortcut(
            "Scan your card",
            "0",
            false
        ),
        Shortcut(
            "Secure the app access",
            "1",
            true
        ),
        Shortcut(
            "Application info",
            "2",
            false
        )
    )
}
