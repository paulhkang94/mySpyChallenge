package xyz.blueowl.ispychallenge.ui.data_browser.shared

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

open class BaseDataBrowserViewModel: ViewModel() {

    protected val _navigationFlow = MutableSharedFlow<DataBrowserNavState>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    protected val _adapterItems = MutableSharedFlow<List<AdapterItem>>(replay = 1)

    val navigationFlow: Flow<DataBrowserNavState>
        get() = _navigationFlow
    val adapterItems: Flow<List<AdapterItem>>
        get() = _adapterItems
}