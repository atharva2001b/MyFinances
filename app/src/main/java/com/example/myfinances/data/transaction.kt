package com.example.myfinances.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class transaction (
    @PrimaryKey val name: String,
    val value: String
        )