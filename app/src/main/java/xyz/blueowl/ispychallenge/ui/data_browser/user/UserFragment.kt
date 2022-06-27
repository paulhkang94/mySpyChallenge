package xyz.blueowl.ispychallenge.ui.data_browser.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.blueowl.ispychallenge.ISpyApplication
import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.databinding.FragmentDataBrowserBinding
import xyz.blueowl.ispychallenge.ui.data_browser.DataBrowserFragmentDirections
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState
import xyz.blueowl.ispychallenge.ui.safeCollectFlow
import xyz.blueowl.ispychallenge.ui.data_browser.shared.UniversalListAdapter

class UserFragment: Fragment() {

    private var _binding: FragmentDataBrowserBinding? = null
    private val userAdapter = UniversalListAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userId = arguments?.let {
            val params = UserFragmentArgs.fromBundle(it)
            params.userId
        } ?: throw IllegalStateException("Missing user id")

        val factory = UserViewModelFactory(userId, (requireActivity().application as ISpyApplication).dataRepository)
        val userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        _binding = FragmentDataBrowserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerViewDataBrowser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }

        safeCollectFlow(userViewModel.navigationFlow) { navState ->
            val action = when(navState) {
                is DataBrowserNavState.ChallengesNavState -> {
                    UserFragmentDirections.actionNavigationUserToNavigationChallenges(navState.userId)
                }
                is DataBrowserNavState.MatchesNavState -> {
                    UserFragmentDirections.actionNavigationUserToNavigationMatches(navState.userId, navState.challengeId)
                }
                is DataBrowserNavState.RatingsNavState -> {
                    UserFragmentDirections.actionNavigationUserToNavigationRatings(navState.userId, navState.challengeId)
                }
                else -> throw IllegalArgumentException("Cannot support navState: ${navState::class.java}")
            }

            findNavController().navigate(action)
        }

        safeCollectFlow(userViewModel.adapterItems) {
            userAdapter.submitList(it)
        }

        return root
    }

    private class UserViewModelFactory(
        private val userId: String,
        private val dataRepository: DataRepository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)){
                return UserViewModel(userId,dataRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
