package com.example.exam.db.repository

import androidx.lifecycle.LiveData
import com.example.exam.db.dao.CryptocurrencyDao
import com.example.exam.db.entity.Cryptocurrency

class CryptocurrencyRepository(private val cryptocurrencyDao: CryptocurrencyDao) {

    val getAllCurrencies: LiveData<List<Cryptocurrency>> = cryptocurrencyDao.getCryptoList()

    suspend fun addCurrencies(currencyList: List<Cryptocurrency>){
        cryptocurrencyDao.insertAllCurrencies(currencyList)
    }

    suspend fun insertOrUpdate(cryptocurrency: Cryptocurrency) {
        cryptocurrencyDao.insertOrUpdate(cryptocurrency)
    }

    suspend fun updateFavourites(favorite: Boolean, name: String) {
        cryptocurrencyDao.updateFavourites(favorite, name)

    }
}