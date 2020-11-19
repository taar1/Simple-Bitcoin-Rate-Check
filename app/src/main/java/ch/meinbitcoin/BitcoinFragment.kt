package ch.meinbitcoin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.meinbitcoin.databinding.BitcoinFragmentBinding


class BitcoinFragment : Fragment(R.layout.bitcoin_fragment) {

    companion object {
        fun newInstance() = BitcoinFragment()
    }

    private lateinit var viewModel: BitcoinViewModel

    private lateinit var viewBinding: BitcoinFragmentBinding

    private lateinit var recyclerListViewAdapter: RecyclerListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = BitcoinFragmentBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.bitcoinTitle.text = "1 Bitcoin ="

        viewModel = ViewModelProvider(this).get(BitcoinViewModel::class.java)

        recyclerListViewAdapter = RecyclerListViewAdapter()

        viewBinding.recyclerView.apply {
            adapter = recyclerListViewAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            itemAnimator?.removeDuration = 50
            setHasFixedSize(true)
        }

        // Wir beobachten die bitcoin Liste und updaten dann das UI sobald sich die Daten ändern.
        viewModel.bitcoinList.observe(viewLifecycleOwner, { btc ->
            btc?.apply {
                recyclerListViewAdapter.setListOfBitcoins(btc)
            }
        })

        // Beobachten ob ein Netzwerkproblem besteht
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner, { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })

        viewModel.isNetworkErrorShown.observe(
            viewLifecycleOwner,
            { isNetworkErrorShown ->
                if (!isNetworkErrorShown) {
                    viewBinding.recyclerView.visibility = View.VISIBLE
                }
            })

        viewBinding.swipeRefresh.setOnRefreshListener {
            toggleRefreshingAnimation(true)
            viewModel.getBitcoinExchangeRatesFromService()
            toggleRefreshingAnimation(false)
        }
    }

    fun toggleRefreshingAnimation(isRefreshing: Boolean) {
        viewBinding.swipeRefresh.isRefreshing = isRefreshing
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(context, "Wir haben grade ein Netzwerkfehler...", Toast.LENGTH_LONG)
                .show()

            viewBinding.recyclerView.visibility = View.GONE
            viewModel.onNetworkErrorShown()
        }
    }
}