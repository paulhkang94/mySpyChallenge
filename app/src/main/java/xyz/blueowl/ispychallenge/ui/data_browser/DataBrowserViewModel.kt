package xyz.blueowl.ispychallenge.ui.data_browser

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.UserItem

class DataBrowserViewModel(
    dataRepository: DataRepository
) : ViewModel() {

    private val _userList = MutableSharedFlow<List<UserItem>>(replay = 1)

    val userList: Flow<List<UserItem>>
        get() = _userList

    init {
        val userItems = dataRepository.allUsers.map { user ->
            UserItem(
                user.username,
                user.email,
                user.avatarThumbnailUrl.toString()
            ) { onUserClick(user.id) }
        }

        _userList.tryEmit(userItems)
    }

    private val onUserClick: (userId: String) -> Unit = {

    }
}
