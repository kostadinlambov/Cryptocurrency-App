package com.example.exam.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.exam.R
import com.example.exam.databinding.FragmentSecondBinding
import com.example.exam.viewmodel.CryptoViewModel

class SecondFragment : Fragment() {
    lateinit var binding: FragmentSecondBinding
    lateinit var mCryptoViewModel: CryptoViewModel

    private val args: SecondFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        mCryptoViewModel = ViewModelProvider(this)[CryptoViewModel::class.java]

        populateCurrencyDetailsModel()

        return binding.root
    }

    private fun populateCurrencyDetailsModel() {
        binding.name = args.name.replaceFirstChar{it.uppercaseChar()}
        binding.symbol = args.symbol.uppercase()
        binding.price = args.price
        binding.marketCap = args.marketCap
        binding.high24h = args.high24h
        binding.low24h = args.low24h
        binding.priceChange24h = args.priceChange24h
        binding.marketCapChangePercentage24h = args.marketCapChangePercentage24h

        updateFieldColors(args.priceChange24h, args.marketCapChangePercentage24h)

        Glide
            .with(binding.root.context)
            .load(args.logo)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.ivLogo)

        setLikeBtn()

    }

    private fun updateFieldColors(priceChange24h: String, marketCapChangePercentage24h: String) {
        val priceChange24hDouble = priceChange24h.toDouble()
        if(priceChange24hDouble >= 0){
            binding.tvPriceChange24h.setTextColor(Color.GREEN)
        }else{
            binding.tvPriceChange24h.setTextColor(Color.RED)
        }

        val marketCapChangePercentage24hDouble = marketCapChangePercentage24h.toDouble()
        if(marketCapChangePercentage24hDouble >= 0){
            binding.tvMarketCapChange24h.setTextColor(Color.GREEN)
        }else{
            binding.tvMarketCapChange24h.setTextColor(Color.RED)
        }
    }

    private fun setLikeBtn() {
        var favorite = args.isFavorite
        setLikeButtonImage(favorite)

        binding.btnLike.setOnClickListener {view ->
            favorite = !favorite

            setLikeButtonImage(favorite)

            // Update favorite button
            val name = args.name
            mCryptoViewModel.updateFavourites(favorite,name)
        }
    }

    private fun setLikeButtonImage(favorite: Boolean){
        if(favorite){
            binding.btnLike.setImageResource(android.R.drawable.star_big_on)
            binding.tvMarketCapChange24h.setTextColor(Color.GREEN)
        }else{
            binding.btnLike.setImageResource(android.R.drawable.star_big_off)
        }
    }

}