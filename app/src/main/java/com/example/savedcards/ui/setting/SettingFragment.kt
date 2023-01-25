package com.example.savedcards.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.savedcards.MainActivity
import com.example.savedcards.R
import com.example.savedcards.databinding.FragmentSettingBinding
import com.example.savedcards.util.FINGERPRINT_ACTIVATED
import com.example.savedcards.util.biometricManagerUtil.BiometricManagerUtil
import com.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding

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
            R.layout.fragment_setting,
            container,
            false
        )

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        binding.customSwitch.setOnClickListener {
            setStateSwitch()
            BiometricManagerUtil.showPropBiometricSetting(MainActivity.activityInstance)

            // Log Analytics
        }
    }

    fun setStateSwitch() {
        binding.customSwitch.isChecked = !(
            ModelPreferencesManager.get<Boolean>(FINGERPRINT_ACTIVATED) == null ||
                ModelPreferencesManager.get<Boolean>(FINGERPRINT_ACTIVATED) == false
            )
    }
}
