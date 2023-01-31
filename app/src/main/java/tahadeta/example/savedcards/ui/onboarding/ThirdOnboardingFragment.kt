package tahadeta.example.savedcards.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentPinCodeBinding
import tahadeta.example.savedcards.databinding.FragmentThirdOnboardingBinding

class ThirdOnboardingFragment : Fragment() {

    private lateinit var binding: FragmentThirdOnboardingBinding

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
            R.layout.fragment_third_onboarding,
            container,
            false
        )

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.pinCodeFragment)
        }
    }
}
