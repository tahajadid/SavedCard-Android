package tahadeta.example.savedcards.ui.cardDetails

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.example.spicyanimation.SpicyAnimation
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.data.Cards
import tahadeta.example.savedcards.databinding.FragmentCardDetailsBinding
import tahadeta.example.savedcards.util.*
import tahadeta.example.savedcards.util.Constants.FROM_EDIT
import tahadeta.example.savedcards.util.Constants.MASTERCARD_TYPE
import tahadeta.example.savedcards.util.comeFrom
import tahadeta.example.savedcards.util.currentCardSelected
import tahadeta.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager
import tahadeta.example.savedcards.util.mySessionCards
import tahadeta.example.savedcards.util.numberCardUtil.ScratchCardUtil

class CardDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCardDetailsBinding
    private var deleteIsHide = true
    private var shareRibIsHide = true

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
            if (!currentCardSelected!!.rib.equals("")) binding.shareView.visibility = View.VISIBLE
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

        if (currentCardSelected!!.cardType == MASTERCARD_TYPE) binding.mastercard.setImageResource(R.drawable.mastercard_logo)
        else binding.mastercard.setImageResource(R.drawable.visa_logo)

        listenToView()
    }

    private fun showDeletAndArchive() {
        // binding.archiveCard.visibility = View.VISIBLE
        binding.deleteCard.visibility = View.VISIBLE
    }

    private fun hideDeletAndArchive() {
        // binding.archiveCard.visibility = View.GONE
        binding.deleteCard.visibility = View.GONE
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
            if (shareRibIsHide) showShareRib()
            else hideShareRib()
            shareRibIsHide = !shareRibIsHide
        }

        binding.deleteCard.setOnClickListener {
            deleteCard()
        }

        binding.deleteCl.setOnClickListener {
            if (deleteIsHide) showDeletAndArchive()
            else hideDeletAndArchive()
            deleteIsHide = !deleteIsHide
        }

        binding.editCl.setOnClickListener {
            comeFrom = FROM_EDIT
            findNavController().navigate(R.id.addInfoCardFragment)
        }

        binding.shareRib.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "RIB Number")
                var shareMessage = "\n My Rib number: ${currentCardSelected!!.rib} \n"
                shareMessage =
                    """
                $shareMessage
                    """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                // e.toString();
            }
        }
    }

    private fun showShareRib() {
        binding.shareRib.visibility = View.VISIBLE
    }

    private fun hideShareRib() {
        binding.shareRib.visibility = View.GONE
    }

    private fun deleteCard() {
        val mySessionCards = ModelPreferencesManager.get<Cards>(Constants.MY_CARDS)

        var indexOfSelectedCard = 0
        var i = 0

        mySessionCards!!.listOfCards.forEach {
            if (it.number == currentCardSelected?.number) {
                indexOfSelectedCard = i
            }
            i++
        }

        mySessionCards!!.listOfCards.removeAt(indexOfSelectedCard)
        ModelPreferencesManager.put(mySessionCards, Constants.MY_CARDS)

        currentCardSelected = null

        findNavController().navigate(R.id.homeFragment)
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
