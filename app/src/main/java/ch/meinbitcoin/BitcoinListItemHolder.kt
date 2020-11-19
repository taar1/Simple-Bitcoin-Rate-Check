package ch.meinbitcoin

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bitcoin_list_item.view.*

class BitcoinListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var view: View

    fun BitcoinListItemHolder(view: View) {
        this.view = view
        view.labelBuy.text = "Buy"
        view.labelSell.text = "Sell"
    }

    fun setBitcoinListItem(bitcoin: Bitcoin) {

        view.titleCurrency.text = "CURRENCY..."

        view.valueBuy.text = bitcoin.buy.toString()
        view.valueSell.text = bitcoin.sell.toString()

    }

}