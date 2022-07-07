package xyz.blueowl.ispychallenge.ui.data_browser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.databinding.FragmentDataBrowserBinding
import xyz.blueowl.ispychallenge.extensions.requireISpyApplication
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState
import xyz.blueowl.ispychallenge.ui.safeCollectFlow
import xyz.blueowl.ispychallenge.ui.data_browser.shared.UniversalListAdapter

class DataBrowserFragment : Fragment() {

    private var _binding: FragmentDataBrowserBinding? = null
    private val dataBrowserAdapter = UniversalListAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = DataBrowserViewModelFactory(requireISpyApplication().dataRepository)
        val dataBrowserViewModel = ViewModelProvider(this, factory)[DataBrowserViewModel::class.java]

        _binding = FragmentDataBrowserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerViewDataBrowser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dataBrowserAdapter
        }

        safeCollectFlow(dataBrowserViewModel.navigationFlow) { navState ->
            val action = when(navState) {
                is DataBrowserNavState.UserNavState -> {
                    DataBrowserFragmentDirections.actionNavigationDataBrowserToNavigationUser(navState.userId)
                }
                else -> throw IllegalArgumentException("Cannot support navState: ${navState::class.java}")
            }

            findNavController().navigate(action)
        }

        safeCollectFlow(dataBrowserViewModel.adapterItems) {
            dataBrowserAdapter.submitList(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private class DataBrowserViewModelFactory(
        private val dataRepository: DataRepository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DataBrowserViewModel::class.java)){
                return DataBrowserViewModel(dataRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}