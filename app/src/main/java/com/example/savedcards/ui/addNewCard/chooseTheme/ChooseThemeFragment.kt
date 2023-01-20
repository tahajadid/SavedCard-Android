package com.example.savedcards.ui.addNewCard.chooseTheme

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.savedcards.R
import com.example.savedcards.data.Cards
import com.example.savedcards.ui.addNewCard.chooseTheme.adapter.CarouselCardAdapter
import com.example.savedcards.util.Constants.MY_CARDS
import com.example.savedcards.util.currentCard
import com.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager
import com.example.savedcards.util.mySessionCards
import kotlin.math.abs

class ChooseThemeFragment : Fragment() {

    lateinit var viewPager: ViewPager2
    lateinit var nextClickView: View
    lateinit var backClickView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_card, container, false)

        // init view
        viewPager = root.findViewById(R.id.view_pager)
        nextClickView = root.findViewById(R.id.next_click_view)
        backClickView = root.findViewById(R.id.back_click_view)

        initComponents()
        return root
    }

    private fun initComponents() {
        nextClickView.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)

            val myCards = ModelPreferencesManager.get<Cards>(MY_CARDS)
            if (myCards == null) {
                // First init
                mySessionCards = Cards(arrayListOf())
            } else mySessionCards = myCards

            mySessionCards!!.listOfCards.add(currentCard!!)
            ModelPreferencesManager
                .put<Cards>(mySessionCards as Cards, MY_CARDS)
        }

        backClickView.setOnClickListener {
            findNavController().navigateUp()
        }

        initViewPager()
    }

    private fun initViewPager() {
        viewPager.apply {
            clipChildren = false // No clipping the left and right items
            clipToPadding = false // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3 // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }

        val demoData = arrayListOf(
            "1",
            "2",
            "3",
            "4",
            "5"
        )

        viewPager.adapter = CarouselCardAdapter(demoData)

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        viewPager.setPageTransformer(compositePageTransformer)

        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentCard?.themeId = position.toString()
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }
}
