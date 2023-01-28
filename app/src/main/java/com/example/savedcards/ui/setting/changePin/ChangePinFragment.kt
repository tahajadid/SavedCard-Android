package com.example.savedcards.ui.setting.changePin

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.savedcards.R
import com.example.savedcards.databinding.FragmentChangePinBinding
import com.example.savedcards.util.Constants
import com.example.savedcards.util.Constants.FIRST_STEP
import com.example.savedcards.util.animationUtil.AnimationUtil
import com.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager
import com.example.savedcards.util.stepPinCodeFragment

class ChangePinFragment : Fragment() {

    private lateinit var binding: FragmentChangePinBinding

    var countDigit = 0
    var listDigit = mutableListOf<Int>()
    var isFirstStep = true
    var isEnabledkeyboard = true

    // code to verify the correct behavior
    var secretCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_pin_code,
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
        initDigitView()
        ListenToButton()
    }

    // function that listen to each click and add the digit which the user had clicked on
    private fun ListenToButton() {
        binding.number0.setOnClickListener {
            addDigitToPassword(0)
        }
        binding.number1.setOnClickListener {
            addDigitToPassword(1)
        }
        binding.number2.setOnClickListener {
            addDigitToPassword(2)
        }
        binding.number3.setOnClickListener {
            addDigitToPassword(3)
        }
        binding.number4.setOnClickListener {
            addDigitToPassword(4)
        }
        binding.number5.setOnClickListener {
            addDigitToPassword(5)
        }
        binding.number6.setOnClickListener {
            addDigitToPassword(6)
        }
        binding.number7.setOnClickListener {
            addDigitToPassword(7)
        }
        binding.number8.setOnClickListener {
            addDigitToPassword(8)
        }
        binding.number9.setOnClickListener {
            addDigitToPassword(9)
        }
        binding.numberDelete.setOnClickListener {
            deleteLastDigit()
        }
    }

    private fun deleteLastDigit() {
        removeDigitToPassword()
    }

    // function that add each digit to our list to combine 4 digit
    private fun addDigitToPassword(number: Int) {
        if (!isEnabledkeyboard) return
        countDigit++
        listDigit.add(number)
        if (countDigit != 4) {
            checkCountDigit()
        } else {
            // in case of tapping the digit number 4
            // we should verify the code
            checkCountDigit()
            if (isFirstStep) {
                secretCode = getDigitActual()
                isFirstStep = false
                isEnabledkeyboard = false
                Handler().postDelayed({
                    try {
                        initDigitView()
                        isEnabledkeyboard = true
                    } catch (e: java.lang.Exception) {
                        // Catch in case of changing UI and can't find the id of the UIView
                        Log.d("TestCatch", "Catched")
                    }
                }, 500)
            } else {
                if (secretCode == getDigitActual()) {
                    registerPassword()
                } else {
                    behaviorWrongPassword()
                }
            }
        }
    }

    private fun registerPassword() {
        ModelPreferencesManager.put<String>(getDigitActual(), Constants.APP_PIN_CODE)
        findNavController().navigate(R.id.homeFragment)
    }

    // function that remove the last digit entered
    private fun removeDigitToPassword() {
        if (countDigit > 0) {
            countDigit--
            listDigit.removeLast()
            checkDiscountDigit()
        }
    }

    // function that check the counter to fill the next purple circle
    private fun checkCountDigit() {
        when (countDigit) {
            1 -> binding.digitOne.background = context?.let {
                ContextCompat.getDrawable(
                    it,
                    R.drawable.password_rounded_fill
                )
            }

            2 ->
                binding.digitTwo.background = context?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.password_rounded_fill
                    )
                }

            3 ->
                binding.digitThree.background = context?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.password_rounded_fill
                    )
                }

            4 ->
                binding.digitFour.background = context?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.password_rounded_fill
                    )
                }
        }
    }

    // function that check the counter to fill the last purple circle
    private fun checkDiscountDigit() {
        when (countDigit) {
            0 -> binding.digitOne.background = context?.let {
                ContextCompat.getDrawable(
                    it,
                    R.drawable.password_rounded
                )
            }

            1 ->
                binding.digitTwo.background = context?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.password_rounded
                    )
                }

            2 ->
                binding.digitThree.background = context?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.password_rounded
                    )
                }

            3 ->
                binding.digitFour.background = context?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.password_rounded
                    )
                }
        }
    }

    // function that return the secret code from our list
    private fun getDigitActual(): String {
        var digit = ""
        return if (listDigit.isEmpty()) digit
        else {
            listDigit.forEach { it ->
                digit += it.toString()
            }
            digit
        }
    }

    // function that init the UI
    private fun initDigitView() {
        if (stepPinCodeFragment.equals(FIRST_STEP)) {
            initDigitViewOldPin()
        } else {
            initDigitViewNewPin()
        }
    }

    // init view for the first step
    fun initDigitViewOldPin() {
        // we set the counter to initial value
        initDigitAndCounter()
        if (isFirstStep) {
            AnimationUtil.changeTextContent(binding.confirmerPasswordTv, "Enter The Pin Code")
        } else {
            AnimationUtil.changeTextContent(binding.confirmerPasswordTv, "Enter The Same Pin Code")
        }
        binding.confirmerPasswordTv.setTextColor(Color.parseColor("#e0f7fa"))

        binding.digitOne.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded
            )
        }

        binding.digitTwo.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded
            )
        }

        binding.digitThree.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded
            )
        }

        binding.digitFour.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded
            )
        }
    }

    // init view for the second and the lalst step
    fun initDigitViewNewPin() {
        // we set the counter to initial value
        initDigitAndCounter()
        if (isFirstStep) {
            AnimationUtil.changeTextContent(binding.confirmerPasswordTv, "Enter The Pin Code")
        } else {
            AnimationUtil.changeTextContent(binding.confirmerPasswordTv, "Enter The Same Pin Code")
        }
        binding.confirmerPasswordTv.setTextColor(Color.parseColor("#e0f7fa"))

        binding.digitOne.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded
            )
        }

        binding.digitTwo.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded
            )
        }

        binding.digitThree.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded
            )
        }

        binding.digitFour.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded
            )
        }
    }

    // function init the counter and the list it used in every init function
    private fun initDigitAndCounter() {
        listDigit = mutableListOf<Int>()
        countDigit = 0
    }

    // function that do the UI behavior in wrong secret code case
    private fun behaviorWrongPassword() {
        binding.mainCl.isEnabled = false
        fillWrongPassword()
        AnimationUtil.changeTextContent(binding.confirmerPasswordTv, "Wrong")
        binding.confirmerPasswordTv.setTextColor(Color.parseColor("#EE815E"))

        // Make a delay then init the view and let the user
        // Enter again the secret code
        Handler().postDelayed({
            try {
                initDigitView()
                binding.mainCl.isEnabled = true
            } catch (e: java.lang.Exception) {
                // Catch in case of changing UI and can't find the id of the UIView
                Log.d("TestCatch", "Catched")
            }
        }, 2000)
    }

    // function that change the UI view to show the error in case of wrong secret code
    private fun fillWrongPassword() {
        binding.digitOne.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded_wrong
            )
        }

        binding.digitTwo.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded_wrong
            )
        }

        binding.digitThree.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded_wrong
            )
        }

        binding.digitFour.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded_wrong
            )
        }
    }
}
