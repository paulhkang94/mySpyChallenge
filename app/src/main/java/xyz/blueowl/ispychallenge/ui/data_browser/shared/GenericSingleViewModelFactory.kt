package xyz.blueowl.ispychallenge.ui.data_browser.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Simple generic view model factory to provide ViewModels needed in the DataBrowser UI package.
 *
 * It is not necessary this is to be used in the Tech Challenge, but is a good example of how to provide
 * ViewModels that required Dependency Injection.
 */
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
