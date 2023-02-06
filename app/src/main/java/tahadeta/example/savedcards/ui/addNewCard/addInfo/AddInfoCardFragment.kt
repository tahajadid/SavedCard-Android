package tahadeta.example.savedcards.ui.addNewCard.addInfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tahadeta.example.savedcards.MainActivity
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.data.CardInfo
import tahadeta.example.savedcards.databinding.FragmentAddInfoCardBinding
import tahadeta.example.savedcards.util.*
import tahadeta.example.savedcards.util.Constants.FROM_EDIT
import tahadeta.example.savedcards.util.Constants.FROM_HOME
import tahadeta.example.savedcards.util.Constants.FROM_SCAN
import tahadeta.example.savedcards.util.Constants.MASTERCARD_TYPE
import tahadeta.example.savedcards.util.Constants.VISACARD_TYPE
import tahadeta.example.savedcards.util.currentCard
import tahadeta.example.savedcards.util.numberCardUtil.ScratchCardUtil
import tahadeta.example.savedcards.util.scanNumberCard

class AddInfoCardFragment : Fragment() {

    private lateinit var binding: FragmentAddInfoCardBinding

    var errorName = false
    var errorNumber = false
    var errorCvv = false
    var errorMonth = false
    var errorYear = false
    var errorRib = false

    var cardSelected = MASTERCARD_TYPE

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
        ScratchCardUtil.setSeparatorFormatNumber(binding.cardNumberEditText)

        when (comeFrom) {
            FROM_SCAN -> fillFromScannedData()
            FROM_EDIT -> fillTheExistingData()
        }

        binding.nextClickView.setOnClickListener {
            checkInfo()
        }

        binding.backClickView.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.checkBox.setOnClickListener { view ->
            if (binding.checkBox.isChecked) binding.ribContainer.visibility = View.VISIBLE
            else binding.ribContainer.visibility = View.GONE
        }

        binding.scanImage.setOnClickListener {
            MainActivity.activityInstance.isCameraPermissionGranted()
        }

        listenToView()
    }

    private fun fillTheExistingData() {
        binding.cardNumberEditText.setText(ScratchCardUtil.addSeparatorToNumber(currentCardSelected?.number.toString()))
        binding.nameEditText.setText(currentCardSelected?.fullName ?: "")
        binding.monthEditText.setText(currentCardSelected!!.expirationMonth.toString())
        binding.yearEditText.setText(currentCardSelected!!.expirationYear.toString())
        binding.cvvEditText.setText(currentCardSelected?.cvv ?: "")

        if (currentCardSelected!!.cardType.equals(MASTERCARD_TYPE)) {
            cardSelected = MASTERCARD_TYPE
            binding.masterSelection.setBackgroundResource(R.drawable.type_card_bg_selected)
            binding.visaSelection.setBackgroundResource(R.drawable.type_card_bg_unselected)
        } else {
            cardSelected = VISACARD_TYPE
            binding.visaSelection.setBackgroundResource(R.drawable.type_card_bg_selected)
            binding.masterSelection.setBackgroundResource(R.drawable.type_card_bg_unselected)
        }

        if (!currentCardSelected!!.rib.equals("")) {
            binding.checkBox.isChecked = true
            binding.ribContainer.visibility = View.VISIBLE
            binding.ribEditText.setText(currentCardSelected!!.rib.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fillFromScannedData() {
        if (!scanNumberCard.equals("")) {
            binding.cardNumberEditText.setText(
                ScratchCardUtil.addSeparatorToNumber(scanNumberCard.replace("\\s".toRegex(), ""))
            )
        }

        if (!scanNameCard.equals("")) {
            binding.nameEditText.setText(scanNameCard)
        }

        if (!scanDateCard.equals("")) {
            binding.monthEditText.setText(scanDateCard.subSequence(0, 2))
            binding.yearEditText.setText("20" + scanDateCard.subSequence(3, 5))
        }
        comeFrom = FROM_HOME
    }

    private fun checkInfo() {
        if (binding.nameEditText.text.isNullOrEmpty()) showErrorName()
        if (binding.cardNumberEditText.text.isNullOrEmpty()) showErrorNumber()
        if (binding.cvvEditText.text.isNullOrEmpty()) showErrorCvv()
        if (binding.monthEditText.text.isNullOrEmpty()) showErrorMonth()
        if (binding.yearEditText.text.isNullOrEmpty()) showErrorYear()
        if (binding.ribEditText.text.isNullOrEmpty() && binding.checkBox.isChecked) showErrorRib()
        if (!errorCvv && !errorYear && !errorMonth && !errorRib && !errorName && !errorNumber) {
            fillCardInfo()
            findNavController().navigate(R.id.addCardFragment)
        }
    }

    private fun listenToView() {
        binding.visaSelection.setOnClickListener {
            cardSelected = VISACARD_TYPE
            binding.visaSelection.setBackgroundResource(R.drawable.type_card_bg_selected)
            binding.masterSelection.setBackgroundResource(R.drawable.type_card_bg_unselected)
        }
        binding.masterSelection.setOnClickListener {
            cardSelected = MASTERCARD_TYPE
            binding.masterSelection.setBackgroundResource(R.drawable.type_card_bg_selected)
            binding.visaSelection.setBackgroundResource(R.drawable.type_card_bg_unselected)
        }

        binding.nameEditText.addTextChangedListener {
            if (errorName) initViewName()
        }

        binding.cardNumberEditText.addTextChangedListener {
            if (errorNumber) initViewNumber()
        }

        binding.ribEditText.addTextChangedListener {
            if (errorRib) initViewRib()
        }

        binding.monthEditText.addTextChangedListener {
            if (errorMonth) initViewMonth()
        }
        binding.yearEditText.addTextChangedListener {
            if (errorYear) initViewYear()
        }
        binding.cvvEditText.addTextChangedListener {
            if (errorCvv) initViewCvv()
        }
    }

    private fun fillCardInfo() {
        currentCard = CardInfo(
            "",
            binding.cardNumberEditText.text.toString().replace("\\s".toRegex(), ""),
            binding.nameEditText.text.toString(),
            binding.monthEditText.text.toString(),
            binding.yearEditText.text.toString(),
            binding.cvvEditText.text.toString(),
            binding.ribEditText.text.toString(),
            "",
            cardSelected
        )
    }

    /**
     * errorName
     */
    private fun initViewName() {
        binding.nameContainer.setBackgroundResource(R.drawable.background_text_fields)
        errorName = false
        binding.errorName.visibility = View.GONE
    }

    private fun showErrorName() {
        binding.nameContainer.setBackgroundResource(R.drawable.background_text_fields_error)
        errorName = true
        binding.errorName.visibility = View.VISIBLE
    }

    /**
     * errorNumber
     */
    private fun initViewNumber() {
        binding.cardNumberContainer.setBackgroundResource(R.drawable.background_text_fields)
        errorNumber = false
        binding.errorCardNumber.visibility = View.GONE
    }

    private fun showErrorNumber() {
        binding.cardNumberContainer.setBackgroundResource(R.drawable.background_text_fields_error)
        errorNumber = true
        binding.errorCardNumber.visibility = View.VISIBLE
    }

    /**
     * errorNumber
     */
    private fun initViewRib() {
        binding.ribContainer.setBackgroundResource(R.drawable.background_text_fields)
        errorRib = false
        binding.errorRib.visibility = View.GONE
    }

    private fun showErrorRib() {
        binding.ribContainer.setBackgroundResource(R.drawable.background_text_fields_error)
        errorRib = true
        binding.errorRib.visibility = View.VISIBLE
    }

    /**
     * errorCvv
     */
    private fun initViewCvv() {
        binding.cvvContainer.setBackgroundResource(R.drawable.background_text_fields)
        errorCvv = false
    }

    private fun showErrorCvv() {
        binding.cvvContainer.setBackgroundResource(R.drawable.background_text_fields_error)
        errorCvv = true
    }

    /**
     * errorMonth
     */
    private fun initViewMonth() {
        binding.monthContainer.setBackgroundResource(R.drawable.background_text_fields)
        errorMonth = false
    }

    private fun showErrorMonth() {
        binding.monthContainer.setBackgroundResource(R.drawable.background_text_fields_error)
        errorMonth = true
    }

    /**
     * errorYear
     */
    private fun initViewYear() {
        binding.yearContainer.setBackgroundResource(R.drawable.background_text_fields)
        errorYear = false
    }

    private fun showErrorYear() {
        binding.yearContainer.setBackgroundResource(R.drawable.background_text_fields_error)
        errorYear = true
    }
}
