package com.example.exam.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.exam.db.dao.CryptocurrencyDao
import com.example.exam.db.entity.Cryptocurrency

@Database(entities = [Cryptocurrency::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cryptocurrencyDao(): CryptocurrencyDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "crypto_db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}