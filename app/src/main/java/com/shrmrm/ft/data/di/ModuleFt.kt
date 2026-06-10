package com.shrmrm.ft.data.di

import android.content.Context
import com.shrmrm.ft.data.db.FtDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleFt {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = FtDb.getInstance(context)

    @Provides
    fun provideFtDao(database: FtDb) = database.ftDao()
}
