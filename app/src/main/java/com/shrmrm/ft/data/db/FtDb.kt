package com.shrmrm.ft.data.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shrmrm.ft.data.domain.Expense
import com.shrmrm.ft.data.domain.Task
import com.shrmrm.ft.data.domain.TaskLog
import com.shrmrm.ft.data.domain.User

@Database(
    entities = [Task::class, TaskLog::class, Expense::class, User::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 2, to = 3)],
)
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
                            ).build()
                }
                return instance
            }
        }
    }
}
