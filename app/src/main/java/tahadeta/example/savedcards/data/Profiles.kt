package tahadeta.example.savedcards.data

import com.google.gson.annotations.SerializedName

data class Profiles(
    @SerializedName("listOfProfiles") var listOfProfiles: MutableList<ProfileUser> = arrayListOf()
)
