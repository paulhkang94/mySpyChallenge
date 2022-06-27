package xyz.blueowl.ispychallenge.ui.data_browser.ratings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.blueowl.ispychallenge.ISpyApplication
import xyz.blueowl.ispychallenge.databinding.FragmentDataBrowserBinding
import xyz.blueowl.ispychallenge.ui.safeCollectFlow
import xyz.blueowl.ispychallenge.ui.data_browser.shared.GenericSingleViewModelFactory
import xyz.blueowl.ispychallenge.ui.data_browser.shared.UniversalListAdapter

class RatingsFragment: Fragment() {

    private var _binding: FragmentDataBrowserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userId = arguments?.let {
            val params = RatingsFragmentArgs.fromBundle(it)
            params.userId
        }

        val challengeId = arguments?.let {
            val params = RatingsFragmentArgs.fromBundle(it)
            params.challengeId
        }

        val filterOption = when {
            userId != null -> { RatingsViewModel.FilterOption.User(userId) }
            challengeId != null -> { RatingsViewModel.FilterOption.Challenge(challengeId) }
            else -> { throw IllegalArgumentException("Missing user id or challenge id") }
        }

        val ratingsAdapter = UniversalListAdapter()

        val factory = GenericSingleViewModelFactory (RatingsViewModel::class.java) {
            RatingsViewModel(filterOption, (requireActivity().application as ISpyApplication).dataRepository)
        }
        val viewModel = ViewModelProvider(this, factory)[RatingsViewModel::class.java]
        _binding = FragmentDataBrowserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerViewDataBrowser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ratingsAdapter
        }

        safeCollectFlow(viewModel.adapterItems) {
            ratingsAdapter.submitList(it)
        }

        return root
    }
}