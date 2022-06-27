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

class MatchFragment: Fragment() {

    private var _binding: FragmentDataBrowserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val matchId = arguments?.let {
            val params = MatchFragmentArgs.fromBundle(it)
            params.matchId
        } ?: throw IllegalArgumentException("Missing match id")

        val matchAdapter = UniversalListAdapter()

        val factory = GenericSingleViewModelFactory(MatchViewModel::class.java) {
            MatchViewModel(matchId, (requireActivity().application as ISpyApplication).dataRepository)
        }
        val viewModel = ViewModelProvider(this, factory)[MatchViewModel::class.java]
        _binding = FragmentDataBrowserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerViewDataBrowser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = matchAdapter
        }

        safeCollectFlow(viewModel.navigationFlow) { navState ->
            val action = when (navState) {
                is DataBrowserNavState.ChallengeNavState -> {
                    MatchFragmentDirections.actionNavigationMatchToNavigationChallenge(navState.challengeId)
                }
                is DataBrowserNavState.UserNavState -> {
                    MatchFragmentDirections.actionNavigationMatchToNavigationUser(navState.userId)
                }
                else -> throw IllegalArgumentException("Cannot support nav state: $navState")
            }

            findNavController().navigate(action)
        }

        safeCollectFlow(viewModel.adapterItems) {
            matchAdapter.submitList(it)
        }

        return root
    }
}