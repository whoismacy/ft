package com.shrmrm.ft.data.db

import androidx.room.Dao
import androidx.room.Insert
import java.util.Date
import java.util.UUID

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(
        id: UUID = UUID.randomUUID(),
        name: String,
        amount: Int,
        date: Date = Date(),
    )
}
