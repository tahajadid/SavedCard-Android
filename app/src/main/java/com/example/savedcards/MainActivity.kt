package com.example.savedcards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var navController: NavController
        lateinit var activityInstance: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize ModelPreferencesManager
        ModelPreferencesManager.with(this.application)

        setContentView(R.layout.activity_main)

        activityInstance = this

        // initialize FirebaseApp
        FirebaseApp.initializeApp(this.application)

        navController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
    }
}
