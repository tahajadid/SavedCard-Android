package com.example.savedcards.ui.login

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.savedcards.MainActivity
import com.example.savedcards.R
import com.example.savedcards.databinding.FragmentLoginBinding
import com.example.savedcards.ui.setting.SettingFragment
import com.example.savedcards.util.Constants
import com.example.savedcards.util.biometricManagerUtil.BiometricManagerUtil
import com.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    var countDigit = 0
    var listDigit = mutableListOf<Int>()
    var isEnabledkeyboard = true

    // code to verify the correct behavior
    var secretCode = ""

    companion object {
        lateinit var loginInstance: LoginFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        loginInstance = this

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        initDigitView()
        ListenToButton()

        // Check if Device have a fingerprint option
        if (!BiometricManagerUtil.hasBiometricAuthenticator(requireContext())) {
            hideFingerPrintSection()
        }

        secretCode = ModelPreferencesManager.get<String>(Constants.APP_PIN_CODE).toString()

        binding.fingerprintIv.setOnClickListener {
            BiometricManagerUtil.showPropBiometric(MainActivity.activityInstance, false)
        }
    }

    private fun hideFingerPrintSection() {
        binding.fingerprintIv.visibility = View.GONE
        binding.fingerprintIv.visibility = View.GONE
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
            if (secretCode == getDigitActual()) {
                animateLoader()
            } else {
                behaviorWrongPassword()
            }
        }
    }

    private fun animateLoader() {
        binding.animateBg.visibility = View.VISIBLE
        binding.animationLoading.visibility = View.VISIBLE

        Handler().postDelayed({
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
    // like re-creating a new UI
    private fun initDigitView() {
        // we set the counter to initial value
        initDigitAndCounter()

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
        binding.animateBg.visibility = View.VISIBLE
        binding.animationLoading.visibility = View.VISIBLE

        Handler().postDelayed({
            binding.animateBg.visibility = View.GONE
            binding.animationLoading.visibility = View.GONE

            binding.mainCl.isEnabled = false
            fillWrongPassword()

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
        }, 600) // 3000 is the delayed time in milliseconds.
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

    fun navigateToHome() {
        MainActivity.activityInstance.findNavController(R.id.nav_host_fragment)
            .popBackStack(
                R.id.loginFragment,
                true
            )
        findNavController().navigate(R.id.homeFragment)
    }
}
