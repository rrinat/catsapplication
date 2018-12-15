package com.cats.catsapplication.features.cats.presentation.adapter

import android.support.v7.util.DiffUtil
import com.cats.catsapplication.features.cats.presentation.model.CatModel

class CatsDiffUtilsCallback (private val oldItems: List<CatModel>, private val newItems: List<CatModel>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].isFavorite == newItems[newItemPosition].isFavorite
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return newItems[newItemPosition].isFavorite
    }
}