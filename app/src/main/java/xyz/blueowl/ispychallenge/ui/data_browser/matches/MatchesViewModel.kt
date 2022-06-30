package xyz.blueowl.ispychallenge.ui.data_browser.matches

import xyz.blueowl.ispychallenge.data.models.Match
import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.ImageTextChevronItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.AdapterItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.BaseDataBrowserViewModel
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState

class MatchesViewModel(
    filterOption: FilterOption,
    dataRepository: DataRepository
): BaseDataBrowserViewModel() {

    init {
        val adapterItems = mutableListOf<AdapterItem>()
        when (filterOption) {
            is FilterOption.Challenge -> {
                dataRepository.getAllChallenges().filter { it.id == filterOption.challengeId }.forEach { challenge ->
                    challenge.matches.forEach { match ->
                        adapterItems.add(match.toImageTextChevron())
                    }
                }
            }
            is FilterOption.User -> {
                dataRepository.getMatchesByUserId(filterOption.userId).forEach { match ->
                    adapterItems.add(match.toImageTextChevron())
                }
            }
        }

        _adapterItems.tryEmit(adapterItems)
    }

    private fun onMatchClick(matchId: String) {
        _navigationFlow.tryEmit(DataBrowserNavState.MatchNavState(matchId))
    }

    private fun Match.toImageTextChevron(): ImageTextChevronItem {
        return ImageTextChevronItem(
            header = "Match",
            label = "(${this.latitude}, ${this.longitude}",
            imageUrl = this.photoImageName
        ) {
            onMatchClick(this.id)
        }
    }

    sealed class FilterOption {

        class Challenge(val challengeId: String): FilterOption()
        class User(val userId: String): FilterOption()
    }
}