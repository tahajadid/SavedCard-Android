package tahadeta.example.savedcards.ui.scanCard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentScanCardBinding

class ScanCardFragment : Fragment() {

    private lateinit var binding: FragmentScanCardBinding

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

        return binding.root
    }
}
