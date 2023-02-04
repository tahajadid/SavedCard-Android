package tahadeta.example.savedcards.util

import tahadeta.example.savedcards.data.CardInfo
import tahadeta.example.savedcards.data.Cards
import tahadeta.example.savedcards.data.ProfileUser
import tahadeta.example.savedcards.data.Profiles
import tahadeta.example.savedcards.util.Constants.FIRST_STEP
import tahadeta.example.savedcards.util.Constants.FROM_HOME

internal var currentCard: CardInfo? = null
internal var currentCardSelected: CardInfo? = null
internal var mySessionCards: Cards? = null
internal var stepPinCodeFragment = FIRST_STEP

internal var mySessionProfiles: Profiles? = null
internal var addedProfile = ProfileUser()
internal var connectedProfile = ProfileUser()

internal var scanNumberCard: String = ""
internal var scanDateCard: String = ""
internal var scanNameCard: String = ""

internal var comeFrom: String = FROM_HOME
