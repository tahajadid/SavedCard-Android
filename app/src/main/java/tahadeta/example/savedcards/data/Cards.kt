package tahadeta.example.savedcards.data

import com.google.gson.annotations.SerializedName

data class Cards(
    @SerializedName("listOfCards") var listOfCards: MutableList<tahadeta.example.savedcards.data.CardInfo> = arrayListOf()
)
