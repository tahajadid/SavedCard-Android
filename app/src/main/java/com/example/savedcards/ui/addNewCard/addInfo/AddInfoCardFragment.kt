package com.example.savedcards.ui.addNewCard.addInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.savedcards.R
import com.example.savedcards.data.CardInfo
import com.example.savedcards.databinding.FragmentAddInfoCardBinding
import com.example.savedcards.util.currentCard

class AddInfoCardFragment : Fragment() {

    private lateinit var binding: FragmentAddInfoCardBinding

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
            R.layout.fragment_add_info_card,
            container,
            false
        )

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        binding.nextClickView.setOnClickListener {
            fillCardInfo()
            findNavController().navigate(R.id.addCardFragment)
        }

        binding.backClickView.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun fillCardInfo() {
        currentCard = CardInfo(
            binding.cardNameEditText.text.toString(),
            binding.nameEditText.text.toString(),
            binding.monthEditText.text.toString(),
            binding.yearEditText.text.toString(),
            binding.cvvEditText.text.toString(),
            binding.ribEditText.text.toString(),
            ""
        )
    }
}
