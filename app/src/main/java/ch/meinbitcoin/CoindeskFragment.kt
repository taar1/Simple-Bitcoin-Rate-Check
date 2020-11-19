package ch.meinbitcoin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ch.meinbitcoin.databinding.CoindeskFragmentBinding


class CoindeskFragment : Fragment(R.layout.coindesk_fragment) {

    companion object {
        fun newInstance() = CoindeskFragment()
    }

    private lateinit var viewModel: CoindeskViewModel

    private lateinit var viewBinding: CoindeskFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = CoindeskFragmentBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.bitcoinTitle.text = "1 Bitcoin ="

        viewModel = ViewModelProvider(this).get(CoindeskViewModel::class.java)


        // Wir beobachten die bitcoin raten und updaten dann das UI sobald sich die Daten ändern.
        viewModel.coindesk.observe(viewLifecycleOwner, { btc ->
            btc?.apply {
                viewBinding.euroValue.text = btc.bpi.EUR.rate
                viewBinding.usdValue.text = btc.bpi.USD.rate
                viewBinding.gbpValue.text = btc.bpi.GBP.rate

                viewBinding.lastUpdatedLabel.text = btc.time.updateduk
            }
        })

        // Beobachten ob ein Netzwerkproblem besteht
        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        viewBinding.refreshButton.setOnClickListener {
            viewModel.getCoindeskDataFromApi()
        }
    }


    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(context, "Wir haben grade ein Netzwerkfehler...", Toast.LENGTH_LONG)
                .show()

            viewModel.onNetworkErrorShown()
        }
    }
}