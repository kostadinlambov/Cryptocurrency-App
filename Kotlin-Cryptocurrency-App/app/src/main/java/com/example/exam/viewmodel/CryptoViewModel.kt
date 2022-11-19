package com.example.exam.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.exam.db.AppDatabase
import com.example.exam.db.entity.Cryptocurrency
import com.example.exam.db.repository.CryptocurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoViewModel(application: Application) : AndroidViewModel(application) {

    val getAllCurrencies: LiveData<List<Cryptocurrency>>

    private val cryptocurrencyRepository: CryptocurrencyRepository

    init {
        val cryptoDao = AppDatabase.getDatabase(application).cryptocurrencyDao()
        cryptocurrencyRepository = CryptocurrencyRepository(cryptoDao)
        getAllCurrencies = cryptocurrencyRepository.getAllCurrencies
    }

    fun addCurrencies(currencyList: List<Cryptocurrency>) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptocurrencyRepository.addCurrencies(currencyList)
        }
    }

    fun insertOrUpdateCurrencies(countries: List<Cryptocurrency>) {
        countries.forEach{insertOrUpdate(it)}
    }

    private fun insertOrUpdate(country: Cryptocurrency) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptocurrencyRepository.insertOrUpdate(country)
        }
    }

    fun updateFavourites(favorite: Boolean, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptocurrencyRepository.updateFavourites(favorite, name)
        }
    }
}