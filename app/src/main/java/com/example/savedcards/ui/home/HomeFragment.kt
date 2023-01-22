package com.example.savedcards.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savedcards.R
import com.example.savedcards.data.CardInfo
import com.example.savedcards.data.Cards
import com.example.savedcards.databinding.FragmentHomeBinding
import com.example.savedcards.ui.home.adapters.ListCardAdapter
import com.example.savedcards.ui.home.adapters.ListShortcutAdapter
import com.example.savedcards.util.Constants.LIST_OF_SHORTCUTS
import com.example.savedcards.util.Constants.MY_CARDS
import com.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager
import com.example.savedcards.util.mySessionCards

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var listCardAdapter: ListCardAdapter
    private lateinit var listShortcutAdapter: ListShortcutAdapter

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
            R.layout.fragment_home,
            container,
            false
        )

        initComponent()
        return binding.root
    }

    private fun initComponent() {
        initListCards()
        initListShortcuts()

        binding.addView.setOnClickListener {
            findNavController().navigate(R.id.addInfoCardFragment)
        }

        binding.emptyListView.setOnClickListener {
            findNavController().navigate(R.id.addInfoCardFragment)
        }
    }

    private fun initListShortcuts() {
        listShortcutAdapter = ListShortcutAdapter(context, LIST_OF_SHORTCUTS)

        binding.shortcutList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listShortcutAdapter
        }
    }

    private fun initListCards() {
        binding.emptyListView.visibility = View.VISIBLE
        val myCards = ModelPreferencesManager.get<Cards>(MY_CARDS)
        if (myCards != null) {
            mySessionCards = myCards

            // Init adapter list
            // listCardAdapter = ListCardAdapter(context, Constants.LIST_OF_CARDS)

            if (mySessionCards!!.listOfCards!!.size == 0) {
                showEmptyList()
            } else {
                hideEmptyList()
                listCardAdapter = ListCardAdapter(context, mySessionCards?.listOfCards as ArrayList<CardInfo>)

                binding.cardList.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = listCardAdapter
                }
            }
        }
    }

    private fun showEmptyList() {
        binding.cardList.visibility = View.GONE
        binding.topLeftView.visibility = View.GONE
        binding.topRightView.visibility = View.GONE
        binding.emptyListView.visibility = View.VISIBLE
    }

    private fun hideEmptyList() {
        binding.cardList.visibility = View.VISIBLE
        binding.topLeftView.visibility = View.VISIBLE
        binding.topRightView.visibility = View.VISIBLE
        binding.emptyListView.visibility = View.INVISIBLE
    }
}
