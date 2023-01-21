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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.savedcards.R
import com.example.savedcards.databinding.FragmentCardDetailsBinding
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
        binding.backPreviewView.visibility = View.GONE

        SpicyAnimation().fadeToDown(binding.frontPreviewView, 30F, 800)
        Handler().postDelayed({
            binding.backPreviewView.visibility = View.VISIBLE
        }, 800)

        binding.frontPreviewView.setOnClickListener {
            flipFrontCard(this.requireContext(), binding.backPreviewView, binding.frontPreviewView)
        }

        binding.backPreviewView.setOnClickListener {
            flipFrontCard(this.requireContext(), binding.frontPreviewView, binding.backPreviewView)
        }

        binding.backClickView.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    @SuppressLint("ResourceType")
    fun flipFrontCard(context: Context, frontView: ConstraintLayout, backView: ConstraintLayout) {
        try {
            frontView.visibility = View.VISIBLE
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
                backView.visibility = View.GONE
            }
            flipOutAnimatorSet.doOnEnd {
                frontView.visibility = View.VISIBLE
            }

        } catch (e: java.lang.Exception) {
            Log.d("ERRORaNI", "Error Animation")
        }
    }

}
