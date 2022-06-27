package xyz.blueowl.ispychallenge.ui.data_browser.user

import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.ui.data_browser.shared.BaseDataBrowserViewModel
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.AttributesHeaderItem
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.HeaderLabelListItem
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.RelationshipHeaderItem
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.TextChevronItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.AdapterItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.HeaderItem

class UserViewModel(
    userId: String,
    dataRepository: DataRepository
): BaseDataBrowserViewModel() {

    init {
        val userAdapterItems = mutableListOf<AdapterItem>()

        userAdapterItems.add(HeaderItem(AttributesHeaderItem()))
        dataRepository.getUserByUserId(userId)?.let { user ->
            userAdapterItems.add(HeaderLabelListItem(user.username, "username"))
            userAdapterItems.add(HeaderLabelListItem(user.email, "email"))
            userAdapterItems.add(HeaderLabelListItem(user.avatarLargeUrl.toString(), "avatarLargeHref"))
            userAdapterItems.add(HeaderLabelListItem(user.avatarMediumUrl.toString(), "avatarMediumHref"))
            userAdapterItems.add(HeaderLabelListItem(user.avatarThumbnailUrl.toString(), "avatarThumbnailHref"))
        }

        userAdapterItems.add(HeaderItem(RelationshipHeaderItem()))
        userAdapterItems.add(TextChevronItem("Challenges") { onNavigateTo(
            DataBrowserNavState.ChallengesNavState(
                userId
            )
        ) })
        userAdapterItems.add(TextChevronItem("Matches") { onNavigateTo(
            DataBrowserNavState.MatchesNavState(
                userId,
                challengeId = null
            )
        ) })
        userAdapterItems.add(TextChevronItem("Ratings") { onNavigateTo(
            DataBrowserNavState.RatingsNavState(
                userId,
                challengeId = null
            )
        ) })

        _adapterItems.tryEmit(userAdapterItems)
    }

    private fun onNavigateTo(navState: DataBrowserNavState) {
        _navigationFlow.tryEmit(navState)
    }
}
