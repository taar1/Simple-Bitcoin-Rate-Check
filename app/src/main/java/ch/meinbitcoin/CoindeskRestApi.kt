package ch.meinbitcoin

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CoindeskRestApi {

    @GET("currentprice.json")
    suspend fun getCoindeskObject(): Response<Coindesk>

    companion object {
        operator fun invoke(): CoindeskRestApi {

            return Retrofit.Builder()
                .baseUrl("https://api.coindesk.com/v1/bpi/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoindeskRestApi::class.java)

        }
    }

}