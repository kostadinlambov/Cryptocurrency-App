package com.example.exam.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exam.R
import com.example.exam.databinding.CryptoItemBinding
import com.example.exam.db.entity.Cryptocurrency
import com.example.exam.fragment.FirstFragmentDirections

class CryptocurrencyAdapter(private val cryptocurrencies: List<Cryptocurrency>):
    RecyclerView.Adapter<CryptocurrencyAdapter.CryptocurrencyViewHolder>() {

    class CryptocurrencyViewHolder(val binding: CryptoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrencyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CryptoItemBinding.inflate(layoutInflater, parent, false)

        return CryptocurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptocurrencyViewHolder, position: Int) {
        val currentCurrency= cryptocurrencies[position]

        holder.binding.apply {
            name =  currentCurrency.name.replaceFirstChar{it.uppercaseChar()}
            symbol = currentCurrency.symbol.uppercase()
            price = "${currentCurrency.currentPrice} USD"

            // Set background color for every currency depending on the favorite property
            if(currentCurrency.isFavorite){
                holder.binding.root.setBackgroundColor(Color.parseColor("#92D6E6"))
            }else{
                holder.binding.root.setBackgroundColor(Color.parseColor("#ffffff"))
            }

            Glide
                .with(this.root.context)
                .load(currentCurrency.image)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivLogo)
        }

        holder.binding.root.setOnClickListener { view ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                currentCurrency.name,
                currentCurrency.image,
                currentCurrency.symbol,
                currentCurrency.currentPrice,
                currentCurrency.marketCap,
                currentCurrency.high24h,
                currentCurrency.low24h,
                currentCurrency.marketCapChangePercentage24h,
                currentCurrency.priceChangePercentage24h,
                currentCurrency.isFavorite,
            )
            view.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = cryptocurrencies.size
}