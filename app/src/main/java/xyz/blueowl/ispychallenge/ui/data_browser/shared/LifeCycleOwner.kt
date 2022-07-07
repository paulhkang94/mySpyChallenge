package xyz.blueowl.ispychallenge.ui

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Executes the flow and performs action when the Activity life cycle event is at least started
 * This is the safest way to use flows in the main thread because we are not leaking anything and be aware of the lifecycle events at the same time
 * Execution is similar to observing a `LiveData` but eliminates the need to use it
 *
 * This function is used only in the DataBrowser UI package as it's similar to the current state of
 * how the Android team uses Flows in the MVVM pattern.
 *
 * @param flow - flow that needs to be executed
 * @param action - execution block
 */
fun <T> AppCompatActivity.safeCollectFlow(flow: Flow<T>, action: suspend (T) -> Unit) =
    lifecycleScope.launchWhenStarted {
        flow.launch(this, action)
    }

/**
 * Executes the flow and performs action when the Fragment life cycle event is at least started.
 * This is the safest way to use flows in the main thread because we are not leaking anything and be aware of the lifecycle events at the same time
 * Execution is similar to observing a `LiveData` but eliminates the need to use it
 *
 * This function is used only in the DataBrowser UI package as it's similar to the current state of
 * how the Android team uses Flows in the MVVM pattern.
 *
 * @param flow - flow that needs to be executed
 * @param action - execution block
 */
fun <T> Fragment.safeCollectFlow(flow: Flow<T>, action: suspend (T) -> Unit) =
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        flow.launch(this, action)
    }

/**
 * This function launches the flow in the provided scope and logs any errors to the corresponding logger
 *
 * This function is used only in the DataBrowser UI package as it's similar to the current state of
 * how the Android team uses Flows in the MVVM pattern.
 *
 * @param launcherScope - Scope that the flowed  gets launched in
 * @param action - block of action that needs to be performed in the given scope
 * @return job - [Job] of coroutine context
 */
fun <T> Flow<T>.launch(launcherScope: CoroutineScope, action: suspend (T) -> Unit): Job =
    this.onEach(action)
        .catch { Log.e("ERROR", it.message ?: "") }
        .launchIn(launcherScope)