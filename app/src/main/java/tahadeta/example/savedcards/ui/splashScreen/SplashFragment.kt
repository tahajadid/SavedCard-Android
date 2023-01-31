package tahadeta.example.savedcards.ui.splashScreen

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.spicyanimation.SpicyAnimation
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentSplashBinding
import tahadeta.example.savedcards.util.Constants.APP_PIN_CODE
import tahadeta.example.savedcards.util.Constants.ONBOARDING
import tahadeta.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

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
            R.layout.fragment_splash,
            container,
            false
        )

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        binding.imageView10.visibility = View.VISIBLE
        binding.enterInfo.visibility = View.VISIBLE

        val window: Window = requireActivity().window

        if (window != null) {
            window.setStatusBarColor(ContextCompat.getColor(requireActivity(), R.color.cyan_25))
            window.navigationBarColor = ContextCompat.getColor(requireActivity(), R.color.cyan_25)
        }

        SpicyAnimation().fadeToUp(binding.imageView10, 60F, 1600)
        SpicyAnimation().fadeToRight(binding.enterInfo, 60F, 1600)

        Handler().postDelayed({
            val getPinCreated = ModelPreferencesManager.get<String>(APP_PIN_CODE)
            if (getPinCreated == null) {
                val demo = ModelPreferencesManager.get<Boolean>(ONBOARDING)
                if (demo == null) {
                    findNavController().navigate(R.id.firstOnboardingFragment)
                } else {
                    findNavController().navigate(R.id.homeFragment)
                }
            } else {
                findNavController().navigate(R.id.loginFragment)
            }
        }, 2500)
    }
}
