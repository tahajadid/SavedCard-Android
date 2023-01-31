package tahadeta.example.savedcards.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentFirstOnboardingBinding

class FirstOnboardingFragment : Fragment() {

    private lateinit var binding: FragmentFirstOnboardingBinding

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
            R.layout.fragment_first_onboarding,
            container,
            false
        )

        val window: Window = requireActivity().window
        if (window != null) {
            window.setStatusBarColor(ContextCompat.getColor(requireActivity(), R.color.cyan_800))
            window.navigationBarColor = ContextCompat.getColor(requireActivity(), R.color.cyan_800)
        }

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.secondOnboardingFragment)
        }
    }
}
