package com.example.expensetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    val name: String,
    val amount: Long,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)