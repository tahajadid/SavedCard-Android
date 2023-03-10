package tahadeta.example.savedcards.data

import com.google.gson.annotations.SerializedName

data class CardInfo(
    @SerializedName("title") var title: String? = null,
    @SerializedName("number") var number: String? = null,
    @SerializedName("fullName") var fullName: String? = null,
    @SerializedName("expirationMonth") var expirationMonth: String? = null,
    @SerializedName("expirationYear") var expirationYear: String? = null,
    @SerializedName("cvv") var cvv: String? = null,
    @SerializedName("rib") var rib: String? = null,
    @SerializedName("themeId") var themeId: String? = null,
    @SerializedName("cardType") var cardType: String? = null
)
