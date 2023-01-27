package com.example.savedcards.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.savedcards.MainActivity
import com.example.savedcards.R
import com.example.savedcards.databinding.FragmentSettingBinding
import com.example.savedcards.util.FINGERPRINT_ACTIVATED
import com.example.savedcards.util.biometricManagerUtil.BiometricManagerUtil
import com.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager

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
            BiometricManagerUtil.showPropBiometricSetting(MainActivity.activityInstance)
        }

        binding.backClickView.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun setStateSwitch() {
        binding.customSwitch.isChecked = !(
            ModelPreferencesManager.get<Boolean>(FINGERPRINT_ACTIVATED) == null ||
                ModelPreferencesManager.get<Boolean>(FINGERPRINT_ACTIVATED) == false
            )
    }
}
