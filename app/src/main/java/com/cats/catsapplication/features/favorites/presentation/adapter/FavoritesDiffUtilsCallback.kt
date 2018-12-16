package com.cats.catsapplication.features.favorites.presentation.adapter

import android.support.v7.util.DiffUtil
import com.cats.catsapplication.core.domain.Cat

class FavoritesDiffUtilsCallback (private val oldItems: List<Cat>, private val newItems: List<Cat>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}