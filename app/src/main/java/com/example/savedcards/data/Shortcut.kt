package com.example.savedcards.data

import com.google.gson.annotations.SerializedName

data class Shortcut(
    @SerializedName("title") var title: String? = null,
    @SerializedName("iconId") var iconId: String? = null,
    @SerializedName("isEnabled") var isEnabled: Boolean? = true
)
