package ch.meinbitcoin

import com.google.gson.annotations.SerializedName

data class Bitcoin(
    @SerializedName("15m") val recent: Float,
    @SerializedName("last") val last: Int,
    @SerializedName("buy") val buy: Int,
    @SerializedName("sell") val sell: Int,
    @SerializedName("symbol") val symbol: String
)