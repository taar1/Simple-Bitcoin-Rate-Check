package ch.meinbitcoin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerListViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var bitcoinList: List<Bitcoin> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.bitcoin_list_item, parent, false)
        return BitcoinListItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bitcoin: Bitcoin? = bitcoinList.get(position)

        val bitcoinListItemHolder: BitcoinListItemHolder = holder as BitcoinListItemHolder
        bitcoin?.let { bitcoinListItemHolder.setBitcoinListItem(it) }
    }

    fun setListOfBitcoins(bitcoins: List<Bitcoin>) {
        bitcoinList = bitcoins
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return bitcoinList.size
    }

}