package ch.meinbitcoin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException

class CoindeskViewModel constructor(app: Application) : AndroidViewModel(app) {

    companion object {
        private const val TAG = "BitcoinViewModel"
    }

    private val repository: CoindeskRepository = CoindeskRepository()

    private val _coindesk: MutableLiveData<Coindesk> by lazy {
        MutableLiveData()
    }
    val coindesk: LiveData<Coindesk>
        get() = _coindesk

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError


    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        getCoindeskDataFromApi()
    }

    fun getCoindeskDataFromApi() {
        viewModelScope.launch {
            try {
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

                repository.getCoindeskExchangeRates()
                _coindesk.postValue(repository.coindesk)

                Log.d(TAG, "NETWORK OK")
            } catch (networkError: IOException) {
                Log.d(TAG, "IOException NO NETWORK")
                _coindesk.postValue(null)
                Log.d(TAG, "Exchange rate is empty. Show a error message in the UI.")
                _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

}