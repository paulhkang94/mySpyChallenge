package xyz.blueowl.ispychallenge.ui.data_browser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.blueowl.ispychallenge.ISpyApplication
import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.databinding.FragmentNotificationsBinding
import xyz.blueowl.ispychallenge.ui.safeCollectFlow
import xyz.blueowl.ispychallenge.ui.shared.UniversalListAdapter

class DataBrowserFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val dataBrowserAdapter = UniversalListAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = DataBrowserViewModelFactory((requireActivity().application as ISpyApplication).dataRepository)
        val dataBrowserViewModel = ViewModelProvider(this, factory)[DataBrowserViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerViewDataBrowser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dataBrowserAdapter
        }

        safeCollectFlow(dataBrowserViewModel.userList) {
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