package com.example.myfinances.data

import androidx.room.*


@Dao
interface transactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: transaction)

    @Delete()
    fun deleteTransaction(transaction: transaction)

    @Query("SELECT * FROM transactions")
    suspend fun getTransactions()
}
