package tahadeta.example.savedcards.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentSecondOnboardingBinding

class SecondOnboardingFragment : Fragment() {

    private lateinit var binding: FragmentSecondOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_second_onboarding,
            container,
            false
        )

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.thirdOnboardingFragment)
        }

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
