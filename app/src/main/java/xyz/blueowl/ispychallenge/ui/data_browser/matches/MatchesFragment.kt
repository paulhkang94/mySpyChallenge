package xyz.blueowl.ispychallenge.ui.data_browser.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.blueowl.ispychallenge.ISpyApplication
import xyz.blueowl.ispychallenge.databinding.FragmentDataBrowserBinding
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState
import xyz.blueowl.ispychallenge.ui.data_browser.shared.GenericSingleViewModelFactory
import xyz.blueowl.ispychallenge.ui.data_browser.shared.UniversalListAdapter
import xyz.blueowl.ispychallenge.ui.safeCollectFlow

class MatchesFragment: Fragment() {

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
            val params = MatchesFragmentArgs.fromBundle(it)
            params.userId
        }

        val challengeId = arguments?.let {
            val params = MatchesFragmentArgs.fromBundle(it)
            params.challengeId
        }

        val filterOption = when {
            userId != null -> MatchesViewModel.FilterOption.User(userId)
            challengeId != null -> MatchesViewModel.FilterOption.Challenge(challengeId)
            else -> throw IllegalArgumentException("Missing user ir od challenge id")
        }

        val matchesAdapter = UniversalListAdapter()

        val factory = GenericSingleViewModelFactory (MatchesViewModel::class.java) {
            MatchesViewModel(filterOption, (requireActivity().application as ISpyApplication).dataRepository)
        }
        val viewModel = ViewModelProvider(this, factory)[MatchesViewModel::class.java]
        _binding = FragmentDataBrowserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerViewDataBrowser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = matchesAdapter
        }

        safeCollectFlow(viewModel.navigationFlow) { navState ->
            val action = when (navState) {
                is DataBrowserNavState.MatchNavState -> {
                    MatchesFragmentDirections.actionNavigationMatchesToNavigationMatch(navState.matchId)
                }
                else -> throw IllegalArgumentException("Cannot support nav state: $navState")
            }

            findNavController().navigate(action)
        }

        safeCollectFlow(viewModel.adapterItems) {
            matchesAdapter.submitList(it)
        }

        return root
    }
}