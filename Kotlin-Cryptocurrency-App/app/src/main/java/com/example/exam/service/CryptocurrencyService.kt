package com.example.exam.service

import com.example.exam.model.CryptocurrencyBindingModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptocurrencyService {

    @GET("markets?vs_currency=usd")
    fun getCryptoList(): Call<List<CryptocurrencyBindingModel>>

}