package tahadeta.example.savedcards.ui.pinCode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentCreationProfileBinding
import tahadeta.example.savedcards.util.addedProfile

class CreationProfileFragment : Fragment() {

    private lateinit var binding: FragmentCreationProfileBinding

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
            R.layout.fragment_creation_profile,
            container,
            false
        )

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        binding.nextBtn.setOnClickListener {
            addedProfile.name = binding.nameEditText.text.toString()
            findNavController().navigate(R.id.pinCodeFragment)
        }

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
