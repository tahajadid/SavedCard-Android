package tahadeta.example.savedcards.ui.home.appInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentAppInfoBinding

class AppInfoFragment : Fragment() {

    private lateinit var binding: FragmentAppInfoBinding

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
            R.layout.fragment_app_info,
            container,
            false
        )

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        binding.backClickView.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
