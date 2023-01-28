package com.example.savedcards.util

import com.example.savedcards.data.CardInfo
import com.example.savedcards.data.Cards
import com.example.savedcards.util.Constants.FIRST_STEP

internal var currentCard: CardInfo? = null
internal var currentCardSelected: CardInfo? = null
internal var mySessionCards: Cards? = null
internal var stepPinCodeFragment = FIRST_STEP
