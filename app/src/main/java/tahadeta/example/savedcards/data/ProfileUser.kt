package tahadeta.example.savedcards.data

import com.google.gson.annotations.SerializedName

data class ProfileUser(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("pin") var pin: String? = null
)