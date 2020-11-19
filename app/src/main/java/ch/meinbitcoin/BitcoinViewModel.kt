package ch.meinbitcoin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException

class BitcoinViewModel constructor(app: Application) : AndroidViewModel(app) {

    private val TAG = "BitcoinViewModel"

    private val repository: BitcoinRepository = BitcoinRepository()

    private val _bitcoinList = MutableLiveData<List<Bitcoin>>()

    val bitcoinList: LiveData<List<Bitcoin>>
        get() = _bitcoinList

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError


    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        getBitcoinExchangeRatesFromService()
    }

    fun getBitcoinExchangeRatesFromService() {
        viewModelScope.launch {
            try {
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

                repository.getBitcoinExchangeRates()
                _bitcoinList.postValue(repository.bitcoinList)

                Log.d(TAG, "NETWORK OK")
            } catch (networkError: IOException) {
                Log.d(TAG, "IOException NO NETWORK")

                // TODO hier könnte man auf ein offline caching (room db) zurückgreifen, falls keine internetverbindung besteht
                // TODO aus zeitgründen nicht umgesetzt...
                _bitcoinList.postValue(null)
            }

            // If data is empty show a error message
            if (repository.bitcoinList.isNullOrEmpty()) {
                Log.d(TAG, "Exchange rate is empty. Show a error message in the UI.")
                _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

}