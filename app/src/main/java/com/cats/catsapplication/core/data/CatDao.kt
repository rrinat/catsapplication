package com.cats.catsapplication.core.data

import android.arch.persistence.room.*
import com.cats.catsapplication.core.data.enitity.CatEntity
import com.cats.catsapplication.core.data.enitity.TABLE_NAME_CAT
import io.reactivex.Flowable

@Dao
interface CatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCats(vararg cats: CatEntity)

    @Delete
    fun deleteCats(vararg cats: CatEntity)

    @Query("SELECT * FROM $TABLE_NAME_CAT")
    fun loadAllCats(): Flowable<List<CatEntity>>
}