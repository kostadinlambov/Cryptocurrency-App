package com.example.exam.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.exam.db.entity.Cryptocurrency

@Dao
interface CryptocurrencyDao {
    @Query("SELECT * FROM crypto_currency")
    fun getCryptoList(): LiveData<List<Cryptocurrency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCurrencies(currencyList: List<Cryptocurrency>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: Cryptocurrency)

    @Update
    suspend fun updateCryptocurrency(cryptocurrency: Cryptocurrency)

    @Query("SELECT * FROM crypto_currency where crypto_currency.name=:name")
    suspend fun getCryptocurrencyByName(name: String): Cryptocurrency

    @Transaction
    suspend fun insertOrUpdate(cryptocurrency: Cryptocurrency) {
        val cryptocurrencyByName = getCryptocurrencyByName(cryptocurrency.name)
        if (cryptocurrencyByName == null) {
            insertCurrency(cryptocurrency)
        } else {
            val copy = cryptocurrency.copy(isFavorite = cryptocurrencyByName.isFavorite)
            updateCryptocurrency(copy)
        }
    }

    @Query("UPDATE CRYPTO_CURRENCY SET is_favorite=:favorite WHERE name=:name")
    suspend fun updateFavourites(favorite: Boolean, name: String)
}