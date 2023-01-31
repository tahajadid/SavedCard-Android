package tahadeta.example.savedcards.ui.home

import android.content.Context
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.databinding.FragmentHomeBinding
import tahadeta.example.savedcards.ui.home.adapters.ListCardAdapter
import tahadeta.example.savedcards.ui.home.adapters.ListShortcutAdapter
import tahadeta.example.savedcards.util.Constants.LIST_OF_SHORTCUTS
import tahadeta.example.savedcards.util.Constants.MY_CARDS
import tahadeta.example.savedcards.util.modelPreferencesManager.ModelPreferencesManager
import tahadeta.example.savedcards.util.mySessionCards

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var listCardAdapter: ListCardAdapter
    private lateinit var listShortcutAdapter: ListShortcutAdapter

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
            R.layout.fragment_home,
            container,
            false
        )

        val window: Window = requireActivity().window
        if (window != null) {
            window.setStatusBarColor(ContextCompat.getColor(requireActivity(), R.color.cyan_800))
            window.navigationBarColor = ContextCompat.getColor(requireActivity(), R.color.black)
        }

        initComponent()

        return binding.root
    }

    private fun initComponent() {
        // checkPinCreated()
        initListCards()
        initListShortcuts()
        shakeAdd()

        binding.addView.setOnClickListener {
            findNavController().navigate(R.id.addInfoCardFragment)
        }

        binding.emptyListView.setOnClickListener {
            findNavController().navigate(R.id.addInfoCardFragment)
        }

        binding.bottomLeftView.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        binding.bottomRightView.setOnClickListener {
            findNavController().navigate(R.id.settingFragment)
        }

        binding.createPinCl.setOnClickListener {
            findNavController().navigate(R.id.pinCodeFragment)
        }
    }

    private fun initListShortcuts() {
        listShortcutAdapter = ListShortcutAdapter(context, LIST_OF_SHORTCUTS)
        binding.shortcutList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listShortcutAdapter
        }
    }

    private fun initListCards() {
        binding.emptyListView.visibility = View.VISIBLE
        val myCards = ModelPreferencesManager.get<tahadeta.example.savedcards.data.Cards>(MY_CARDS)
        if (myCards != null) {
            mySessionCards = myCards

            // Init adapter list
            // listCardAdapter = ListCardAdapter(context, Constants.LIST_OF_CARDS)

            if (mySessionCards!!.listOfCards!!.size == 0) {
                showEmptyList()
            } else {
                hideEmptyList()
                listCardAdapter = ListCardAdapter(context, mySessionCards?.listOfCards as ArrayList<tahadeta.example.savedcards.data.CardInfo>)

                binding.cardList.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = listCardAdapter
                }
            }
        }
    }

    private fun vibratePhone() {
        val vibrator = tahadeta.example.savedcards.MainActivity.activityInstance.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) { // Vibrator availability checking
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE)) // New vibrate method for API Level 26 or higher
            } else {
                vibrator.vibrate(300) // Vibrate method for below API Level 26
            }
        }
    }

    private fun shakeAdd() {
        binding.addView.animate().scaleX(0.9f).scaleY(0.9f).setDuration(400).start()
        Handler().postDelayed({
            binding.addView.animate().scaleX(1f).scaleY(1f).setDuration(400).start()
        }, 500)

        binding.addBackView.animate().scaleX(0.9f).scaleY(0.9f).setDuration(600).start()
        Handler().postDelayed({
            binding.addBackView.animate().scaleX(1f).scaleY(1f).setDuration(600).start()
        }, 500)

        Handler().postDelayed({
            shakeAdd()
        }, 1100)
    }

    private fun showEmptyList() {
        binding.cardList.visibility = View.GONE
        binding.topLeftView.visibility = View.GONE
        binding.topRightView.visibility = View.GONE
        binding.emptyListView.visibility = View.VISIBLE
    }

    private fun hideEmptyList() {
        binding.cardList.visibility = View.VISIBLE
        binding.topLeftView.visibility = View.VISIBLE
        binding.topRightView.visibility = View.VISIBLE
        binding.emptyListView.visibility = View.INVISIBLE
    }
}
