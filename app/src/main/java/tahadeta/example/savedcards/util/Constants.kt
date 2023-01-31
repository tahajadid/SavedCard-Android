package tahadeta.example.savedcards.util

import tahadeta.example.savedcards.data.Shortcut

object Constants {

    const val MASTERCARD_TYPE = "MASTERCARD_TYPE"
    const val VISACARD_TYPE = "VISACARD_TYPE"

    val LIST_OF_SHORTCUTS = arrayListOf(
        Shortcut(
            "Secure the app access",
            "0",
            true
        ),
        Shortcut(
            "Application info",
            "1",
            true
        )
    )

    val LIST_OF_LAST_SHORTCUTS = arrayListOf(
        Shortcut(
            "Secure the app access",
            "0",
            true
        ),
        Shortcut(
            "Scan your card",
            "1",
            false
        ),
        Shortcut(
            "Application info",
            "2",
            false
        )
    )

    /**
     * State of steps n change pin code
     */
    const val FIRST_STEP = "FIRST_STEP"
    const val SECOND_STEP = "SECOND_STEP"

    /**
     * SaredPref Var:
     */
    const val MY_CARDS = "MY_CARDS"
    const val APP_PIN_CODE = "APP_PIN_CODE"
    const val BIOMETRIC_CREDENTIAL = "BIOMETRIC_CREDENTIAL"
    const val FINGERPRINT_ACTIVATED = "FINGERPRINT_ACTIVATED"
    const val ONBOARDING = "ONBOARDING"
}
