package com.shrmrm.ft.data.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "expenses",
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("expense_id")
    val expenseId: Int = 0,
    @ColumnInfo("expense_name")
    val name: String,
    @ColumnInfo(name = "expense_amount")
    val amount: Int,
    @ColumnInfo(
        name = "expense_date",
        defaultValue = "CURRENT_TIMESTAMP",
    )
    val date: Date,
)
