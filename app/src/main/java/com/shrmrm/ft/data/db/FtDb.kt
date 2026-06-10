package com.shrmrm.ft.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shrmrm.ft.data.domain.Expense
import com.shrmrm.ft.data.domain.Task
import com.shrmrm.ft.data.domain.TaskLog

@Database(entities = [Task::class, TaskLog::class, Expense::class], version = 1)
@TypeConverters(FtConverters::class)
abstract class FtDb : RoomDatabase() {
    abstract fun ftDao(): FtDao

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
