package xyz.blueowl.ispychallenge.ui.near_me

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NearMeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Near Me Fragment"
    }
    val text: LiveData<String> = _text
}