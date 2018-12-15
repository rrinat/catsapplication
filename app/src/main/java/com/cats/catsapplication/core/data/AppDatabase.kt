package com.cats.catsapplication.core.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.cats.catsapplication.core.data.enitity.CatEntity


@Database(entities = [CatEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}
