package tahadeta.example.savedcards.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentSettingBinding
import tahadeta.example.savedcards.util.Constants.FINGERPRINT_ACTIVATED
import tahadeta.example.savedcards.util.biometricManagerUtil.BiometricManagerUtil
import tahadeta.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding

    companion object {
        lateinit var settingInstance: SettingFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        setStateSwitch()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting,
            container,
            false
        )

        settingInstance = this

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        binding.customSwitch.setOnClickListener {
            setStateSwitch()
            BiometricManagerUtil.showPropBiometricSetting(tahadeta.example.savedcards.MainActivity.activityInstance)
        }

        binding.passwordCl.setOnClickListener {
            findNavController().navigate(R.id.changePinFragment)
        }

        binding.addView.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.bottomLeftView.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        // Check if Device have a fingerprint option
        if (!BiometricManagerUtil.hasBiometricAuthenticator(requireContext())) {
            hideFingerPrintSection()
        }
    }

    private fun hideFingerPrintSection() {
        binding.fingerprintCl.visibility = View.GONE
    }

    fun setStateSwitch() {
        binding.customSwitch.isChecked = !(
            ModelPreferencesManager.get<Boolean>(FINGERPRINT_ACTIVATED) == null ||
                ModelPreferencesManager.get<Boolean>(FINGERPRINT_ACTIVATED) == false
            )
    }
}
