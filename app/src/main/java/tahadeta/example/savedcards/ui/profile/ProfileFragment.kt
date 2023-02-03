package tahadeta.example.savedcards.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentProfileBinding
import tahadeta.example.savedcards.util.connectedProfile

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

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
            R.layout.fragment_profile,
            container,
            false
        )

        initComponents()

        return binding.root
    }

    private fun initComponents() {
        fillInformation()

        binding.backClickView.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.addProfile.setOnClickListener {
            Toast.makeText(
                tahadeta.example.savedcards.MainActivity.activityInstance,
                "Feature available on next release",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.addView.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.bottomRightView.setOnClickListener {
            findNavController().navigate(R.id.settingFragment)
        }
    }

    private fun fillInformation() {
        binding.profileName.text = connectedProfile.name
    }
}
