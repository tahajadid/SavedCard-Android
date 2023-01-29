package tahadeta.example.savedcards.ui.addNewCard.chooseTheme

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.textfield.TextInputEditText
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.ui.addNewCard.chooseTheme.adapter.CarouselCardAdapter
import tahadeta.example.savedcards.util.Constants.MY_CARDS
import tahadeta.example.savedcards.util.currentCard
import tahadeta.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager
import tahadeta.example.savedcards.util.mySessionCards
import kotlin.math.abs

class ChooseThemeFragment : Fragment() {

    lateinit var viewPager: ViewPager2
    lateinit var nextClickView: View
    lateinit var backClickView: View
    lateinit var numberTv: TextView
    lateinit var nameTv: TextView
    lateinit var dateTv: TextView

    lateinit var cardTitle: TextInputEditText
    lateinit var bgTitle: ConstraintLayout
    lateinit var cardTitleError: TextView

    var errorFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
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
        numberTv = root.findViewById(R.id.card_number)
        nameTv = root.findViewById(R.id.card_name)
        dateTv = root.findViewById(R.id.card_date)

        cardTitle = root.findViewById(R.id.label_editText)
        bgTitle = root.findViewById(R.id.label_container)
        cardTitleError = root.findViewById(R.id.error_card_title)

        listenToView()
        return root
    }

    private fun listenToView() {
        fillInformation()
        nextClickView.setOnClickListener {
            if (cardTitle.text.isNullOrEmpty()) showError()
            else navigateToSuccess()
        }

        backClickView.setOnClickListener {
            findNavController().navigateUp()
        }

        cardTitle.addTextChangedListener {
            if (errorFlag) initView()
        }

        initViewPager()
    }

    private fun navigateToSuccess() {
        currentCard?.title = cardTitle.text.toString()

        val myCards = ModelPreferencesManager.get<tahadeta.example.savedcards.data.Cards>(MY_CARDS)
        if (myCards == null) {
            // First init
            mySessionCards = tahadeta.example.savedcards.data.Cards(arrayListOf())
        } else mySessionCards = myCards
        mySessionCards!!.listOfCards.add(currentCard!!)
        ModelPreferencesManager
            .put<tahadeta.example.savedcards.data.Cards>(mySessionCards as tahadeta.example.savedcards.data.Cards, MY_CARDS)
        findNavController().navigate(R.id.homeFragment)
    }

    @SuppressLint("SetTextI18n")
    private fun fillInformation() {
        numberTv.text = currentCard!!.number.toString()
        nameTv.text = currentCard!!.fullName.toString()
        dateTv.text = currentCard!!.expirationMonth.toString() +
            "/" +
            currentCard!!.expirationYear.toString().drop(2)
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
            "5",
            "6"
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

    private fun initView() {
        bgTitle.setBackgroundResource(R.drawable.background_text_fields)
        errorFlag = false
        cardTitleError.visibility = View.GONE
    }

    private fun showError() {
        errorFlag = true
        cardTitleError.visibility = View.VISIBLE
        bgTitle.setBackgroundResource(R.drawable.background_text_fields_error)
    }
}
