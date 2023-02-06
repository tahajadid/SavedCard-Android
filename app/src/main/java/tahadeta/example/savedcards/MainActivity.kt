package tahadeta.example.savedcards

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.FirebaseApp
import tahadeta.example.savedcards.ui.scanCard.ScanCardFragment
import tahadeta.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var navController: NavController
        lateinit var activityInstance: MainActivity
        var currentDestinationId: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize ModelPreferencesManager
        ModelPreferencesManager.with(this.application)

        setContentView(R.layout.activity_main)

        activityInstance = this

        // initialize FirebaseApp
        FirebaseApp.initializeApp(this.application)

        navController =
            Navigation.findNavController(this, R.id.nav_host_fragment)

        /**
         * Get destination id when is changed
         * onCreate
         */
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentDestinationId = destination.id
        }
    }

    /**
     * Clear focus of all EditText views
     * dispatchTouchEvent
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val imm =
                        getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    public fun isCameraPermissionGranted(): Boolean {
        val TAG = "Storage_Permission"
        if (this!!.checkSelfPermission(Manifest.permission.CAMERA)
            === PackageManager.PERMISSION_GRANTED
        ) {
            Log.v(TAG, "Permission is granted")
            navController.navigate(R.id.scanCardFragment)
            return true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                100
            )
            Toast.makeText(applicationContext, "Permission is revoked", Toast.LENGTH_SHORT)
            return false
        }
    }

    // override function on Request Permissions Result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        val TAG = "Storage_Permission"

        when (requestCode) {
            100 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    navController.navigate(R.id.scanCardFragment)
                }else Toast.makeText(applicationContext, "Permission is revoked", Toast.LENGTH_SHORT)
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
