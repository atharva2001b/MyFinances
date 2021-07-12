package com.example.myfinances.data

import android.content.Context
import android.os.strictmode.InstanceCountViolation
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [transaction::class], version = 1, exportSchema = false)
abstract class transactionDatabase: RoomDatabase() {

    abstract fun dao() : transactionDao

    companion object{

        @Volatile
        private var INSTANCE: transactionDatabase? = null

        fun getDatabase(context: Context) : transactionDatabase{

            synchronized(this){
                if(INSTANCE == null){
                    val instance = Room.databaseBuilder(context, transactionDatabase::class.java, "database").build()
                    INSTANCE = instance
                    return instance
                }else{
                    return INSTANCE as transactionDatabase
                }
            }
        }
    }
}