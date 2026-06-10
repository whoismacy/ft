package com.shrmrm.ft.data.db

import androidx.room.Dao
import androidx.room.Query
import com.shrmrm.ft.data.domain.Expense
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ExpenseDao {
    @Query("INSERT INTO 'expenses' (expense_id, expense_name, expense_amount, expense_date) VALUES (:id, :name, :amount, :date); ")
    suspend fun insertExpense(
        id: Int,
        name: String,
        amount: Int,
        date: Date = Date(),
    )

    @Query("SELECT * FROM `expenses`;")
    fun loadAllExpenses(): Flow<List<Expense>>
}
