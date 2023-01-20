package com.example.savedcards.data

import com.google.gson.annotations.SerializedName

data class Cards(
    @SerializedName("listOfCards") var listOfCards: MutableList<CardInfo> = arrayListOf()
)
