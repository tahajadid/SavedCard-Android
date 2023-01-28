package com.example.savedcards.util.biometricManagerUtil

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.savedcards.MainActivity
import com.example.savedcards.ui.login.LoginFragment
import com.example.savedcards.ui.setting.SettingFragment
import com.example.savedcards.util.Constants.BIOMETRIC_CREDENTIAL
import com.example.savedcards.util.Constants.FINGERPRINT_ACTIVATED
import com.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager
import java.util.concurrent.Executor

object BiometricManagerUtil {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    /**
     * fun to display  biometric login
     * prams
     * LoginActivity
     *  Boolean form Login or Setting
     */

    fun showPropBiometric(mainActivity: MainActivity, fromSetting: Boolean) {
        executor = ContextCompat.getMainExecutor(MainActivity.activityInstance)

        biometricPrompt = BiometricPrompt(
            mainActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    if (fromSetting.equals(true)) {
                        ModelPreferencesManager.put(false, FINGERPRINT_ACTIVATED)
                    }
                }

                /**
                 * override  fun
                 * success auth
                 */
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    LoginFragment.loginInstance.navigateToHome()
                }

                /**
                 * override  fun
                 * Failed auth
                 */
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    if (fromSetting.equals(true)) {
                        ModelPreferencesManager.put(false, FINGERPRINT_ACTIVATED)
                    }
                }
            }
        )
        /**
         * Set infos to BiometricPrompt
         * Title
         * Subtitlle
         * NavigateButton
         *
         */
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("setTitle 1")
            .setSubtitle("setSubtitle 1")
            .setNegativeButtonText("setNegativeButtonText 1")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    /**
     * fun to display  biometric setting
     */
    fun showPropBiometricSetting(mainActivity: MainActivity) {
        executor = ContextCompat.getMainExecutor(mainActivity)

        biometricPrompt = BiometricPrompt(
            mainActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                /**
                 * override  fun
                 * Failed auth
                 */
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    ModelPreferencesManager.put(false, FINGERPRINT_ACTIVATED)
                }

                /**
                 * override  fun
                 * Success auth
                 */
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)

                    val actualvalue =
                        ModelPreferencesManager.get<Boolean>(FINGERPRINT_ACTIVATED)
                    if (actualvalue == null || actualvalue == false) {
                        ModelPreferencesManager.put(true, FINGERPRINT_ACTIVATED)
                    } else {
                        ModelPreferencesManager.put(false, FINGERPRINT_ACTIVATED)
                    }
                    SettingFragment.settingInstance.setStateSwitch()

                    // Log Analytics
                }

                /**
                 * override  fun
                 * Failed auth
                 */
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    ModelPreferencesManager.put(false, FINGERPRINT_ACTIVATED)
                    SettingFragment.settingInstance.setStateSwitch()
                }
            }
        )
        /**
         * Set infos to BiometricPrompt
         * Title
         * Subtitlle
         * NavigateButton
         *
         */
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Title")
            .setSubtitle("setSubtitle")
            .setNegativeButtonText("setNegativeButtonText")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    /**
     * fun to check biometric availability
     */
    fun hasBiometricAuthenticator(context: Context?): Boolean {
        val biometry =
            if (Build.VERSION.SDK_INT >= 30) BiometricManager.from(context!!)
                .canAuthenticate(BIOMETRIC_STRONG or BIOMETRIC_WEAK) else BiometricManager.from(
                context!!
            ).canAuthenticate()
        when (biometry) {
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE, BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED, BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> return false
            BiometricManager.BIOMETRIC_SUCCESS -> return true
        }
        return false
    }

    /**
     * fun to check biometric availability
     */
    fun isAvailable(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Fingerprint API only available on from Android 6.0 (M)
            val fingerprintManager =
                if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) context.getSystemService(
                    Context.FINGERPRINT_SERVICE
                ) as FingerprintManager else null
            if (fingerprintManager?.isHardwareDetected != true) {
                return false
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                return false
            }
            return true
        }
        return false
    }

    /**
     * fun to check isSaved biometrics
     */
    fun isSavedUserBiometrics(): Boolean? {
        val credential =
            ModelPreferencesManager.get<Boolean>(BIOMETRIC_CREDENTIAL)
        return credential != null && isAvailable(
            MainActivity.activityInstance.baseContext
        )
    }
}