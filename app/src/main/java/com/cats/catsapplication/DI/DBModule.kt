package com.cats.catsapplication.DI

import android.arch.persistence.room.Room
import android.content.Context
import com.cats.catsapplication.core.data.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DBModule (private val context: Context)  {

    companion object {
        private const val DATABASE_NAME = "data_base"
    }

    @Provides
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}