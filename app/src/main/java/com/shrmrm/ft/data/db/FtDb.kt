package com.shrmrm.ft.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class FtDb : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    abstract fun taskDao(): TaskDao

    companion object {
        private var INSTANCE: FtDb? = null

        fun getInstance(context: Context): FtDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance =
                        Room
                            .databaseBuilder(
                                context = context.applicationContext,
                                klass = FtDb::class.java,
                                name = "ftDatabase",
                            ).fallbackToDestructiveMigration(false)
                            .build()
                }
                return instance
            }
        }
    }
}
