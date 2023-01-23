package com.example.savedcards.ui.cardDetails

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.savedcards.R
import com.example.savedcards.databinding.FragmentCardDetailsBinding
import com.example.savedcards.util.Constants
import com.example.savedcards.util.currentCardSelected
import com.example.savedcards.util.numberCardUtil.ScratchCardUtil
import com.example.spicyanimation.SpicyAnimation

class CardDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCardDetailsBinding

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
            R.layout.fragment_card_details,
            container,
            false
        )

        initComponent()
        return binding.root
    }

    private fun initComponent() {
        binding.backPreviewView.visibility = View.INVISIBLE
        binding.shareView.visibility = View.INVISIBLE

        SpicyAnimation().fadeToDown(binding.frontPreviewView, 30F, 800)
        SpicyAnimation().fadeToDown(binding.cardTitle, 30F, 800)

        Handler().postDelayed({
            binding.backPreviewView.visibility = View.VISIBLE
            binding.shareView.visibility = View.VISIBLE
        }, 800)

        Handler().postDelayed({
            SpicyAnimation().fadeToDown(binding.shareView, 30F, 200)
        }, 600)

        binding.cardTitle.text = currentCardSelected?.title ?: ""
        binding.cardNumber.text = ScratchCardUtil.addSeparatorToNumber(currentCardSelected?.number.toString())
        binding.cardName.text = currentCardSelected?.fullName ?: ""
        binding.cardDate.text = currentCardSelected!!.expirationMonth.toString() +
            "/" +
            currentCardSelected!!.expirationYear.toString().drop(2)

        binding.cvvCard.text = currentCardSelected?.cvv ?: ""

        when (currentCardSelected!!.themeId!!.toInt()) {
            0 -> binding.frontPreviewView.setBackgroundResource(R.drawable.card_bg_one)
            1 -> binding.frontPreviewView.setBackgroundResource(R.drawable.card_bg_two)
            2 -> binding.frontPreviewView.setBackgroundResource(R.drawable.card_bg_three)
            3 -> binding.frontPreviewView.setBackgroundResource(R.drawable.card_bg_four)
            4 -> binding.frontPreviewView.setBackgroundResource(R.drawable.card_bg_five)
            else -> binding.frontPreviewView.setBackgroundResource(R.drawable.card_bg_six)
        }

        if(currentCardSelected!!.cardType == Constants.MASTERCARD_TYPE) binding.mastercard.setImageResource(R.drawable.mastercard_logo)
        else binding.mastercard.setImageResource(R.drawable.visa_logo)

        listenToView()
    }

    private fun listenToView() {
        binding.frontPreviewView.setOnClickListener {
            flipFrontCard(this.requireContext(), binding.backPreviewView, binding.frontPreviewView)
        }

        binding.backPreviewView.setOnClickListener {
            flipFrontCard(this.requireContext(), binding.frontPreviewView, binding.backPreviewView)
        }

        binding.backClickView.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.shareView.setOnClickListener {
        }
    }

    @SuppressLint("ResourceType")
    fun flipFrontCard(context: Context, frontView: ConstraintLayout, backView: ConstraintLayout) {
        try {
            frontView.visibility = View.VISIBLE
            frontView.isEnabled = false
            backView.isEnabled = false
            val scale = context.resources.displayMetrics.density
            val cameraDist = 8000 * scale
            frontView.cameraDistance = cameraDist
            backView.cameraDistance = cameraDist
            val flipOutAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.anim.flip_out
                ) as AnimatorSet
            flipOutAnimatorSet.setTarget(backView)
            val flipInAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.anim.flip_in
                ) as AnimatorSet
            flipInAnimatorSet.setTarget(frontView)
            flipOutAnimatorSet.start()
            flipInAnimatorSet.start()
            flipInAnimatorSet.doOnEnd {
                backView.visibility = View.INVISIBLE
                frontView.isEnabled = true
                backView.isEnabled = true
            }
            flipOutAnimatorSet.doOnEnd {
                frontView.visibility = View.VISIBLE
                frontView.isEnabled = true
                backView.isEnabled = true
            }
        } catch (e: java.lang.Exception) {
            Log.d("ERRORaNI", "Error Animation")
        }
    }
}
