package com.cats.catsapplication.features.favorites.presentation.adapter

import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v4.widget.ImageViewCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cats.catsapplication.R
import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.core.utils.loadImage

class FavoritesAdapter(private var cats: List<Cat>,
                       private val onDeleteClick: (Cat) -> Unit,
                       private val onDownloadClick: (Cat) -> Unit) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    fun updateItems(newCats: List<Cat>) {
        val diffUtilsCallback = FavoritesDiffUtilsCallback(cats, newCats)
        val diffResult = DiffUtil.calculateDiff(diffUtilsCallback)

        cats = newCats

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)
        return ViewHolder(view, onDeleteClick, onDownloadClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cats[position])
    }

    override fun getItemCount(): Int = cats.size

    class ViewHolder(view: View,
                     private val onDeleteClick: (Cat) -> Unit,
                     private val onDownloadClick: (Cat) -> Unit) : RecyclerView.ViewHolder(view) {
        private val imageView: AppCompatImageView = view.findViewById(R.id.image_view)
        private val favoriteView: AppCompatImageView = view.findViewById(R.id.favorite_button)
        private val downloadView: AppCompatImageView = view.findViewById(R.id.download_button)

        fun bind(cat: Cat) {
            imageView.loadImage(cat.url)

            val context = imageView.context
            ImageViewCompat.setImageTintList(favoriteView, ColorStateList.valueOf( ContextCompat.getColor(context, R.color.colorAccent)))

            favoriteView.setOnClickListener { onDeleteClick.invoke(cat) }
            downloadView.setOnClickListener { onDownloadClick.invoke(cat) }
        }
    }
}
