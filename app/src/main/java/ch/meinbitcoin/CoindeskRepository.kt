package ch.meinbitcoin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoindeskRepository : SafeApiRequest() {

    var coindesk: Coindesk? = null

    suspend fun getCoindeskExchangeRates() {
        withContext(Dispatchers.IO) {
            val coindeskFromApi: Coindesk = apiRequest { CoindeskRestApi().getCoindeskObject() }

            coindesk = coindeskFromApi
        }
    }


}