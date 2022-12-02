package xyz.blueowl.ispychallenge.ui.near_me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import xyz.blueowl.ispychallenge.databinding.FragmentChallengeImageBinding
import xyz.blueowl.ispychallenge.extensions.requireISpyApplication
import xyz.blueowl.ispychallenge.ui.data_browser.shared.GenericSingleViewModelFactory
import xyz.blueowl.ispychallenge.ui.safeCollectFlow

class ChallengeImageFragment : Fragment() {

    private var _binding: FragmentChallengeImageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val challengeId = arguments?.let {
            val params = ChallengeImageFragmentArgs.fromBundle(it)
            params.challengeId
        } ?: throw IllegalArgumentException("Missing challenge id")

        val factory = GenericSingleViewModelFactory (ChallengeImageViewModel::class.java) {
            ChallengeImageViewModel(challengeId, requireISpyApplication().dataRepository)
        }
        val viewModel = ViewModelProvider(this, factory)[ChallengeImageViewModel::class.java]
        _binding = FragmentChallengeImageBinding.inflate(inflater, container, false)

        safeCollectFlow(viewModel.imageItem) { imageUrl ->
            Picasso.get()
                .load(imageUrl)
                .fit()
                .centerInside()
                .into(binding.image)
        }

        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}