package xyz.blueowl.ispychallenge.ui.data_browser.challenges

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

class ChallengeFragment: Fragment() {

    private var _binding: FragmentDataBrowserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val challengeId = arguments?.let {
            val params = ChallengeFragmentArgs.fromBundle(it)
            params.challengeId
        } ?: throw IllegalArgumentException("Missing challenge id")

        val challengeAdapter = UniversalListAdapter()

        val factory = GenericSingleViewModelFactory (ChallengeViewModel::class.java) {
            ChallengeViewModel(challengeId, (requireActivity().application as ISpyApplication).dataRepository)
        }
        val viewModel = ViewModelProvider(this, factory)[ChallengeViewModel::class.java]
        _binding = FragmentDataBrowserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerViewDataBrowser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = challengeAdapter
        }

        safeCollectFlow(viewModel.navigationFlow) { navState ->
            val action = when (navState) {
                is DataBrowserNavState.MatchesNavState -> {
                    ChallengeFragmentDirections.actionNavigationChallengeToNavigationMatches(navState.userId, navState.challengeId)
                }
                is DataBrowserNavState.RatingsNavState -> {
                    ChallengeFragmentDirections.actionNavigationChallengeToNavigationRatings(navState.userId, navState.challengeId)
                }
                is DataBrowserNavState.UserNavState -> {
                    ChallengeFragmentDirections.actionNavigationChallengeToNavigationUser(navState.userId)
                }
                else -> throw IllegalArgumentException("Cannot support nav state $navState")
            }

            findNavController().navigate(action)
        }

        safeCollectFlow(viewModel.adapterItems) {
            challengeAdapter.submitList(it)
        }

        return root
    }
}