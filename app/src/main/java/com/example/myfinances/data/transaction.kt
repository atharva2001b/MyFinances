package com.example.myfinances.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class transaction (
    @PrimaryKey val id: String,
    val type: String,
    val name: String,
    val value: String
        )