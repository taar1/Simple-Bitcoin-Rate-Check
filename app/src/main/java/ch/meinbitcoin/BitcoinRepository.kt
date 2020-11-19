package ch.meinbitcoin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BitcoinRepository : SafeApiRequest() {

    var bitcoinList: List<Bitcoin> = listOf()

    suspend fun getBitcoinExchangeRates() {
        withContext(Dispatchers.IO) {
            val bitcoinListFromApi: List<Bitcoin> = apiRequest { RestApi().getRateList() }

            bitcoinList = bitcoinListFromApi
        }
    }

}