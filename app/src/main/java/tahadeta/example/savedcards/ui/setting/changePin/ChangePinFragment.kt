package tahadeta.example.savedcards.ui.setting.changePin

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
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentChangePinBinding
import tahadeta.example.savedcards.util.Constants.APP_PIN_CODE
import tahadeta.example.savedcards.util.Constants.FIRST_STEP
import tahadeta.example.savedcards.util.Constants.SECOND_STEP
import tahadeta.example.savedcards.util.animationUtil.AnimationUtil
import tahadeta.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager
import tahadeta.example.savedcards.util.stepPinCodeFragment

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
            R.layout.fragment_change_pin,
            container,
            false
        )

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        binding.backClickView.setOnClickListener {
            stepPinCodeFragment = FIRST_STEP
            findNavController().navigate(R.id.settingFragment)
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
            if (stepPinCodeFragment.equals(FIRST_STEP)) {
                secretCode = ModelPreferencesManager.get<String>(APP_PIN_CODE).toString()
                if (secretCode == getDigitActual()) {
                    animateLoader()
                } else {
                    behaviorWrongPassword()
                }
            } else {
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
    }

    private fun animateLoader() {
        binding.animateBg.visibility = View.VISIBLE
        binding.animationLoading.visibility = View.VISIBLE

        Handler().postDelayed({
            stepPinCodeFragment = SECOND_STEP
            findNavController().navigate(R.id.changePinFragment)
        }, 1000) // 3000 is the delayed time in milliseconds.
    }

    private fun registerPassword() {
        ModelPreferencesManager.put(getDigitActual(),APP_PIN_CODE)

        binding.animateBg.visibility = View.VISIBLE
        binding.animationLoading.visibility = View.VISIBLE

        Handler().postDelayed({
            stepPinCodeFragment = FIRST_STEP
            findNavController().navigate(R.id.homeFragment)
        }, 1000) // 3000 is the delayed time in milliseconds.
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
            0 -> {
                if (stepPinCodeFragment.equals(FIRST_STEP)) {
                    binding.digitOne.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.password_rounded_actual
                        )
                    }
                } else {
                    binding.digitOne.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.password_rounded
                        )
                    }
                }
            }

            1 -> {
                if (stepPinCodeFragment.equals(FIRST_STEP)) {
                    binding.digitTwo.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.password_rounded_actual
                        )
                    }
                } else {
                    binding.digitTwo.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.password_rounded
                        )
                    }
                }
            }
            2 -> {
                if (stepPinCodeFragment.equals(FIRST_STEP)) {
                    binding.digitThree.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.password_rounded_actual
                        )
                    }
                } else {
                    binding.digitThree.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.password_rounded
                        )
                    }
                }
            }
            3 -> {
                if (stepPinCodeFragment.equals(FIRST_STEP)) {
                    binding.digitFour.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.password_rounded_actual
                        )
                    }
                } else {
                    binding.digitFour.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.password_rounded
                        )
                    }
                }
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
        binding.mainCl.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.main_bg_pin
            )
        }
        binding.imageView4.setImageResource(R.drawable.actual_pin_icon)

        // we set the counter to initial value
        initDigitAndCounter()
        AnimationUtil.changeTextContent(binding.confirmerPasswordTv, "Enter Your Actual Pin Code")
        binding.confirmerPasswordTv.setTextColor(Color.parseColor("#e0f7fa"))

        binding.digitOne.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded_actual
            )
        }

        binding.digitTwo.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded_actual
            )
        }

        binding.digitThree.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded_actual
            )
        }

        binding.digitFour.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.password_rounded_actual
            )
        }
    }

    // init view for the second and the lalst step
    fun initDigitViewNewPin() {
        binding.mainCl.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.color.cyan_800
            )
        }

        binding.imageView4.setImageResource(R.drawable.new_pin_icon)
        // we set the counter to initial value
        initDigitAndCounter()
        if (isFirstStep) {
            AnimationUtil.changeTextContent(binding.confirmerPasswordTv, "Enter The New Pin Code")
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
