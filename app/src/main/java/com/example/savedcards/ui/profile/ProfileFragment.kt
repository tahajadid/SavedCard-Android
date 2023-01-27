package com.example.savedcards.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.savedcards.MainActivity
import com.example.savedcards.R
import com.example.savedcards.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

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
            R.layout.fragment_profile,
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

        binding.addProfile.setOnClickListener {
            Toast.makeText(
                MainActivity.activityInstance,
                "Feature available on next release 1.0",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
