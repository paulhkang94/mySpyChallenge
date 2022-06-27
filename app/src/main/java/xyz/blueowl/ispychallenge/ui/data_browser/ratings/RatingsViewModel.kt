package xyz.blueowl.ispychallenge.ui.data_browser.ratings

import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.ui.data_browser.shared.BaseDataBrowserViewModel
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.HeaderLabelListItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.AdapterItem

class RatingsViewModel(
    filterOption: FilterOption,
    dataRepository: DataRepository
): BaseDataBrowserViewModel() {

    init {
        val adapterItems = mutableListOf<AdapterItem>()
        when (filterOption) {
            is FilterOption.Challenge -> {
                dataRepository.getAllChallenges().find { it.id == filterOption.challengeId}?.ratings?.let { ratings ->
                    dataRepository.getAssociatedUsersByRatings(ratings)
                }?.forEach { ratingAndAssociatedUser ->
                    adapterItems.add(HeaderLabelListItem(ratingAndAssociatedUser.rating.stars.toString(), ratingAndAssociatedUser.user.username))
                }
            }
            is FilterOption.User -> {
                dataRepository.getUserByUserId(filterOption.userId)?.let { user ->
                    dataRepository.getRatingsByUserId(user.id).forEach { rating ->
                        adapterItems.add(HeaderLabelListItem(rating.stars.toString(), user.username))
                    }
                }
            }
        }

        _adapterItems.tryEmit(adapterItems)
    }

    sealed class FilterOption {

        class Challenge(val challengeId: String): FilterOption()
        class User(val userId: String): FilterOption()
    }
}
