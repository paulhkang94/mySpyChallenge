package xyz.blueowl.ispychallenge.ui.data_browser

import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.ImageTextChevronItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.BaseDataBrowserViewModel
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState

class DataBrowserViewModel(
    dataRepository: DataRepository
) : BaseDataBrowserViewModel() {

    init {
        val userItems = dataRepository.allUsers.map { user ->
            ImageTextChevronItem(
                user.username,
                user.email,
                user.avatarLargeUrl.toString()
            ) { onUserClick(user.id) }
        }

        _adapterItems.tryEmit(userItems)
    }

    private val onUserClick: (userId: String) -> Unit = {
        _navigationFlow.tryEmit(DataBrowserNavState.UserNavState(it))
    }
}
