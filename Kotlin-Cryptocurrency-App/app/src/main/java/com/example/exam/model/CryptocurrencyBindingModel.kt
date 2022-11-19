package com.example.exam.model

data class CryptocurrencyBindingModel(
    var image: String,
    var name: String,
    var symbol: String,
    var current_price: String,
    var market_cap: String,
    var high_24h: String,
    var price_change_percentage_24h: String,
    var market_cap_change_percentage_24h: String,
    var low_24h: String,
)

