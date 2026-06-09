package com.shrmrm.ft.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.Date
import java.util.UUID

@Dao
interface ExpenseDao {
    @Query("INSERT INTO 'expenses' (expense_id, expense_name, expense_amount, expense_date) VALUES (:id, :name, :amount, :date); ")
    fun insertExpense(
        id: Int = 0,
        name: String,
        amount: Int,
        date: Date = Date(),
    )
}
