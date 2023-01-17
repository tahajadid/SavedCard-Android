package com.example.savedcards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var navController: NavController
        lateinit var activityInstance: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityInstance = this

        navController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
    }
}
