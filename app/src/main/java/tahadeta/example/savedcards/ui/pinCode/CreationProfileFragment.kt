package tahadeta.example.savedcards.ui.pinCode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentCreationProfileBinding
import tahadeta.example.savedcards.util.Constants
import tahadeta.example.savedcards.util.addedProfile
import tahadeta.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager

class CreationProfileFragment : Fragment() {

    private lateinit var binding: FragmentCreationProfileBinding
    var errorName = false

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
            checkInfo()
        }

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.nameEditText.addTextChangedListener {
            if (errorName) initViewName()
        }
    }

    private fun checkInfo() {
        if (binding.nameEditText.text.isNullOrEmpty()) showErrorName()
        if (!errorName) {
            addedProfile.name = binding.nameEditText.text.toString()
            ModelPreferencesManager.put(true, Constants.ONBOARDING)
            findNavController().navigate(R.id.pinCodeFragment)
        }
    }

    private fun showErrorName() {
        binding.nameContainer.setBackgroundResource(R.drawable.background_text_fields_error)
        errorName = true
        binding.errorName.visibility = View.VISIBLE
    }

    private fun initViewName() {
        binding.nameContainer.setBackgroundResource(R.drawable.background_text_fields)
        errorName = false
        binding.errorName.visibility = View.GONE
    }
}
