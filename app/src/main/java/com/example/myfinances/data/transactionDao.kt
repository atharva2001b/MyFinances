package com.example.myfinances.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface transactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: transaction)

    @Delete()
    fun deleteTransaction(transaction: transaction)

    @Query("SELECT * FROM transactions")
    fun getTransactions(): List<transaction>
}
