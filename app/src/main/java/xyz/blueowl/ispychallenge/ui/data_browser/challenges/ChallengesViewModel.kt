package xyz.blueowl.ispychallenge.ui.data_browser.challenges

import xyz.blueowl.ispychallenge.data.models.Challenge
import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.ImageTextChevronItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.AdapterItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.BaseDataBrowserViewModel
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState

class ChallengesViewModel(
    userId: String,
    dataRepository: DataRepository
): BaseDataBrowserViewModel() {

    init {
        val adapterItems = mutableListOf<AdapterItem>()
        dataRepository.getUserByUserId(userId)?.let { user ->
            user.challenges.forEach { challenge ->
                adapterItems.add(challenge.toImageTextChevronItem())
            }
        }

        _adapterItems.tryEmit(adapterItems)
    }

    private fun onChallengeClick(challengeId: String) {
        _navigationFlow.tryEmit(DataBrowserNavState.ChallengeNavState(challengeId))
    }

    private fun Challenge.toImageTextChevronItem(): ImageTextChevronItem {
        return ImageTextChevronItem(
            header = this.hint,
            label = "(${this.latitude}, ${this.longitude})",
            imageUrl = this.photoImageName
        ) {
            onChallengeClick(this.id)
        }
    }
}
