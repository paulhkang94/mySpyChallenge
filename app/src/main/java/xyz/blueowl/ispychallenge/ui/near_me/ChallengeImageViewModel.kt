package xyz.blueowl.ispychallenge.ui.near_me

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.ui.data_browser.shared.BaseDataBrowserViewModel

class ChallengeImageViewModel(
    challengeId: String,
    dataRepository: DataRepository
) : BaseDataBrowserViewModel() {

    private val _imageItem = MutableSharedFlow<String>(1)

    val imageItem: SharedFlow<String> = _imageItem

    init {
        dataRepository.getAllChallenges().firstOrNull { it.id == challengeId }?.let {
            _imageItem.tryEmit(it.photoImageName)
        }
    }
}