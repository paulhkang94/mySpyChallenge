package xyz.blueowl.ispychallenge.ui.data_browser.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GenericSingleViewModelFactory<E: ViewModel>(
    private val clazz: Class<E>,
    private val createViewModel: () -> E
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(clazz)) {
            return createViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
