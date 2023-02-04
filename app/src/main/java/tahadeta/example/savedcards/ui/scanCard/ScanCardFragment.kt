package tahadeta.example.savedcards.ui.scanCard

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import tahadeta.example.savedcards.MainActivity
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentScanCardBinding
import tahadeta.example.savedcards.util.*
import tahadeta.example.savedcards.util.Constants.FROM_SCAN
import tahadeta.example.savedcards.util.scanDateCard
import tahadeta.example.savedcards.util.scanHelper.ScanHelper
import tahadeta.example.savedcards.util.scanNameCard
import tahadeta.example.savedcards.util.scanNumberCard
import java.util.regex.Pattern
import kotlin.properties.Delegates

class ScanCardFragment : Fragment() {

    private lateinit var binding: FragmentScanCardBinding

    private var mCameraSource by Delegates.notNull<CameraSource>()
    private var textRecognizer by Delegates.notNull<TextRecognizer>()

    private lateinit var surfaceCameraPreview: SurfaceView
    private val permissionRequestCamera = 200
    private var flagName: Boolean = false
    private var count = 0
    private var indexDate = 0

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
            R.layout.fragment_scan_card,
            container,
            false
        )

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        surfaceCameraPreview = binding.surfaceCameraPreview
        startCameraSource()

        binding.backView.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.capture.setOnClickListener {
            // binding.viewBg.visibility = View.VISIBLE
            binding.extractLabel.visibility = View.VISIBLE
            // anim.playAnimation()

            // val intent = Intent(this, ScanBack::class.java)
            textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
                override fun release() {
                    println("TODO")
                }
                override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
                    if (count == 0) {
                        count++
                        val items = detections.detectedItems
                        if (items.size() <= 0) {
                            return
                        }
                        process(items)
                    }
                }
            })
        }
    }

    private fun process(items: SparseArray<TextBlock>?) {
        val arrayList = ScanHelper.removeUnuseful(items)
        binding.tvResult.post {
            this.flagName = true
            for (i in 0 until arrayList.size) {
                val item = arrayList[i]
                Log.d("TestDebug", "Value (process) : $item")
                val flagMatchNumber = Pattern.matches("[0-9].*", item)
                processNumber(item, i, flagMatchNumber)
                val flagMatchD = Pattern.matches("[0-9]+/[0-9]+", item)
                processD(item, i, flagMatchD, arrayList)
            }
            comeFrom = FROM_SCAN
            findNavController().navigate(R.id.addInfoCardFragment)
        }
    }

    private fun processD(item: String, i: Int, flagMatchD: Boolean, arrayList: ArrayList<String>) {
        if (flagMatchD && item.length > 1) {
            scanDateCard = item
            scanNameCard = arrayList[i + 1]
            Log.d("TestDebug", "£££ -> Value Name (Name) : " + arrayList[indexDate + 1])
            Log.d("TestDebug", "£££ -> Value Date (processD) : $item")
        }
    }

    private fun processNumber(item: String, i: Int, flagMatchNumber: Boolean) {
        if (flagMatchNumber && item.length > 16 && i < 10) {
            scanNumberCard = item.replace('L', '1').replace('O', '0')
        }
    }

    private fun startCameraSource() {
        //  Create text Recognizer
        textRecognizer = TextRecognizer.Builder(MainActivity.activityInstance).build()

        if (!textRecognizer.isOperational) {
            Log.d("TestDebug", "Value isOperational")
            return
        }

        //  Init camera source to use high resolution and auto focus
        mCameraSource = CameraSource.Builder(MainActivity.activityInstance.applicationContext, textRecognizer)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(1280, 1024)
            .setAutoFocusEnabled(true)
            .setRequestedFps(2.0f)
            .build()

        surfaceCameraPreview.holder.addCallback(object : SurfaceHolder.Callback {

            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (isCameraPermissionGranted()) {
                        mCameraSource.start(surfaceCameraPreview.holder)
                    } else {
                        requestForPermission()
                    }
                } catch (e: Exception) {
                    // toast("Error:  ${e.message}")
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                // DO("Not yet implemented")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                mCameraSource.stop()
            }
        })
    }

    fun isCameraPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(MainActivity.activityInstance, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
    }

    private fun requestForPermission() {
        ActivityCompat.requestPermissions(MainActivity.activityInstance, arrayOf(Manifest.permission.CAMERA), permissionRequestCamera)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != permissionRequestCamera) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (isCameraPermissionGranted()) {
                mCameraSource.start(surfaceCameraPreview.holder)
            } else {
                // toast("Permission need to grant")
                findNavController().navigateUp()
            }
        }
    }
}
