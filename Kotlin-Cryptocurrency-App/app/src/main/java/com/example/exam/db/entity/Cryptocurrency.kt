package com.example.exam.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_currency")
data class Cryptocurrency(
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "current_price") val currentPrice: String,
    @ColumnInfo(name = "market_cap") val marketCap: String,
    @ColumnInfo(name = "high_24h") val high24h: String,
    @ColumnInfo(name = "price_change_percentage_24h") val priceChangePercentage24h: String,
    @ColumnInfo(name = "market_cap_change_percentage_24h") val marketCapChangePercentage24h: String,
    @ColumnInfo(name = "low_24h") val low24h: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}