package com.example.exam.repository

import com.example.exam.model.CryptocurrencyBindingModel
import com.example.exam.service.CryptocurrencyService
import retrofit2.Call

class CryptoRepository constructor(
    private val cryptocurrencyService: CryptocurrencyService,

) {
     fun getCryptocurrencies(): Call<List<CryptocurrencyBindingModel>> {
        return try {
            val cryptocurrencyList = cryptocurrencyService.getCryptoList()
            cryptocurrencyList
        } catch (e: Exception) {
            throw Exception("Something went wrong during fetching all currencies")
        }
    }
}