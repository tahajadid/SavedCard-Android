package com.example.savedcards.ui.home.secureApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.savedcards.R
import com.example.savedcards.databinding.FragmentSecureAppBinding

class SecureAppFragment : Fragment() {

    private lateinit var binding: FragmentSecureAppBinding

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
            R.layout.fragment_secure_app,
            container,
            false
        )

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        binding.backClickView.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
