package com.example.exam.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.exam.adapter.CryptocurrencyAdapter
import com.example.exam.databinding.FragmentFirstBinding
import com.example.exam.db.entity.Cryptocurrency
import com.example.exam.model.CryptocurrencyBindingModel
import com.example.exam.repository.CryptoRepository
import com.example.exam.service.CryptocurrencyService
import com.example.exam.util.NetworkUtil
import com.example.exam.viewmodel.CryptoViewModel
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FirstFragment : Fragment() {
    lateinit var binding: FragmentFirstBinding
    lateinit var mCryptoViewModel: CryptoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        mCryptoViewModel = ViewModelProvider(this)[CryptoViewModel::class.java]

        val hasInternet = context?.let { NetworkUtil.isConnected(it) } ?: false

        if (hasInternet) {
            insertCryptosToDatabase()
        }

        loadCountriesFromDatabase()

        return binding.root
    }

    private fun insertCryptosToDatabase() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/coins/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        val cryptocurrencyService = retrofit.create(CryptocurrencyService::class.java)
        val cryptocurrencyRepository = CryptoRepository(cryptocurrencyService)

        cryptocurrencyRepository.getCryptocurrencies()
            .enqueue(object : Callback<List<CryptocurrencyBindingModel>> {
                override fun onResponse(
                    call: Call<List<CryptocurrencyBindingModel>>,
                    response: Response<List<CryptocurrencyBindingModel>>
                ) {
                    val cryptos = response.body() ?: return

                    val mappedCurrenciesDataList = cryptos.map {
                        Cryptocurrency(
                            it.image,
                            it.name,
                            it.symbol,
                            it.current_price,
                            it.market_cap,
                            it.high_24h,
                            it.price_change_percentage_24h,
                            it.market_cap_change_percentage_24h,
                            it.low_24h,
                        false
                        )
                    }

                    mCryptoViewModel.insertOrUpdateCurrencies(mappedCurrenciesDataList)

//                    Snackbar.make(
//                        binding.root,
//                        "Successfully updated ${mappedCurrenciesDataList.size} currencies!",
//                        Snackbar.LENGTH_LONG
//                    ).show()
                }

                override fun onFailure(call: Call<List<CryptocurrencyBindingModel>>, t: Throwable) {
                    Snackbar.make(
                        binding.root,
                        "Failed to fetch crypto currencies",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            })
    }

    private fun loadCountriesFromDatabase() {
        mCryptoViewModel.getAllCurrencies.observe(viewLifecycleOwner, Observer { currencies ->
            val sortedCurrencies = currencies.sortedWith(compareBy({ !it.isFavorite }, { -it.marketCap.toDouble() }))

            val adapter = CryptocurrencyAdapter(sortedCurrencies)
            binding.cryptosList.adapter = adapter
            binding.progressBar.visibility = View.GONE
        })
    }
}