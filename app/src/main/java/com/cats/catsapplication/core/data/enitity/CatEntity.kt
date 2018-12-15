package com.cats.catsapplication.core.data.enitity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

const val TABLE_NAME_CAT = "cats"

@Entity(tableName = TABLE_NAME_CAT)
data class CatEntity(@PrimaryKey val id: String, val url: String)