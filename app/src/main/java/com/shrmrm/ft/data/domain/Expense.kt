package com.shrmrm.ft.data.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "expenses",
)
data class Expense(
    @PrimaryKey
    @ColumnInfo("expense_id")
    val expenseId: UUID = UUID.randomUUID(),
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
