package com.example.savedcards.ui.splashScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.savedcards.R
import com.example.savedcards.databinding.FragmentSplashBinding
import com.example.savedcards.util.Constants
import com.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_splash,
            container,
            false
        )

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        val getPinCreated = ModelPreferencesManager.get<String>(Constants.APP_PIN_CODE)
        Log.d("SECRETLOG","equl : "+getPinCreated)
        if (getPinCreated == null) {
            findNavController().navigate(R.id.homeFragment)
        } else {
            findNavController().navigate(R.id.loginFragment)
        }
    }
}
