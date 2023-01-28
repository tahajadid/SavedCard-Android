package com.example.savedcards.util

import com.example.savedcards.data.Shortcut

object Constants {

    const val MASTERCARD_TYPE = "MASTERCARD_TYPE"
    const val VISACARD_TYPE = "VISACARD_TYPE"

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

    /**
     * SaredPref Var:
     */
    const val MY_CARDS = "MY_CARDS"
    const val APP_PIN_CODE = "APP_PIN_CODE"
    const val BIOMETRIC_CREDENTIAL = "BIOMETRIC_CREDENTIAL"
    const val FINGERPRINT_ACTIVATED = "FINGERPRINT_ACTIVATED"
}
