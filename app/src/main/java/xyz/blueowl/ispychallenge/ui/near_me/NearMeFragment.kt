package xyz.blueowl.ispychallenge.ui.near_me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.blueowl.ispychallenge.databinding.FragmentNearMeBinding
import xyz.blueowl.ispychallenge.extensions.requireISpyApplication
import xyz.blueowl.ispychallenge.ui.data_browser.challenges.ChallengesFragmentDirections
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState
import xyz.blueowl.ispychallenge.ui.data_browser.shared.GenericSingleViewModelFactory
import xyz.blueowl.ispychallenge.ui.data_browser.shared.UniversalListAdapter
import xyz.blueowl.ispychallenge.ui.safeCollectFlow

class NearMeFragment : Fragment() {
    companion object {
        private const val TEST_LATITUDE = 37.7904462
        private const val TEST_LONGITUDE = -122.4011537
    }

    private var _binding: FragmentNearMeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val nearMeAdapter = UniversalListAdapter()
        val factory = GenericSingleViewModelFactory (NearMeViewModel::class.java) {
            NearMeViewModel(
                originLatitude = TEST_LATITUDE,
                originLongitude = TEST_LONGITUDE,
                dataRepository = requireISpyApplication().dataRepository
            )
        }
        val viewModel = ViewModelProvider(this, factory)[NearMeViewModel::class.java]

        _binding = FragmentNearMeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerViewNearMe.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = nearMeAdapter
        }

        safeCollectFlow(viewModel.navigationFlow) { navState ->
            val action = when (navState) {
                is DataBrowserNavState.ChallengeImageState -> {
                    NearMeFragmentDirections.actionNavigationNearMeToChallengeImage(navState.challengeId)
                }
                else -> throw IllegalArgumentException("Cannot support nav state $navState")
            }

            findNavController().navigate(action)
        }

        safeCollectFlow(viewModel.adapterItems) {
            nearMeAdapter.submitList(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/*
1. Challenge List
- GIVEN the user is on the near me tab
- WHEN a list of challenges are displayed to the user
- THEN they are sorted by the distance of the object to the device

2. Challenge Content
- GIVEN the user is displayed information about a challenge
- THEN the following information is displayed for each challenge
- Total number of wins
    - matches with isVerified = true
- Average rating
    - sum of rating.value / ratings.size
- Distance of the object to the device
    - get distance between device lat/long and challenge lat/long
- Full hint
- Creator of the challenge
    - get User given userID, return username or "unknown"
- sort by minimum distance

2b. Challenge cell layout
- Need to create dynamically resizable layout for challenge cells
- card view
    - 12dp corner radius
    - top/bottom padding 8dp
    - start/end padding 16dp
    - 12dp inner padding inside cell
    - 8dp padding between horizontal text levels (header / middle / footer)


3. Challenge Selection
- GIVEN the user is displayed a list of challenges
- WHEN the user selects a challenge
- THEN the user is taken to a screen to display the full image of the object

- Create new screen/fragment/layout
 - imageview with 16dp start/end padding
 - centerInside
 */