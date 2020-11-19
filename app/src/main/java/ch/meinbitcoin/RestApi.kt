package ch.meinbitcoin

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RestApi {

    @GET("/ticker")
    suspend fun getRateList(): Response<List<Bitcoin>>

    companion object {
        operator fun invoke(): RestApi {

            return Retrofit.Builder()
                .baseUrl("https://blockchain.info")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RestApi::class.java)

        }
    }

}