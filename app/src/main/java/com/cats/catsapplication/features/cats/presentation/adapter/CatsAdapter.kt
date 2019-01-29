package com.cats.catsapplication.features.cats.presentation.adapter

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
import com.cats.catsapplication.core.utils.loadImage
import com.cats.catsapplication.features.cats.presentation.model.CatModel


class CatsAdapter(private val onFavoriteClick: (CatModel) -> Unit,
                  private val onDownloadClick: (CatModel) -> Unit) : RecyclerView.Adapter<CatsAdapter.ViewHolder>() {

    private val cats: MutableList<CatModel> = arrayListOf()

    fun updateItems(newCats: List<CatModel>) {
        val diffUtilsCallback = CatsDiffUtilsCallback(cats, newCats)
        val diffResult = DiffUtil.calculateDiff(diffUtilsCallback)

        cats.clear()
        cats.addAll(newCats)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)
        return ViewHolder(view, onFavoriteClick, onDownloadClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cats[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.updateFavorite(cats[position])
        }
    }

    override fun getItemCount(): Int = cats.size

    class ViewHolder(view: View,
                     private val onAddFavoriteClick: (CatModel) -> Unit,
                     private val onDownloadClick: (CatModel) -> Unit) : RecyclerView.ViewHolder(view) {
        private val imageView: AppCompatImageView = view.findViewById(R.id.image_view)
        private val favoriteView: AppCompatImageView = view.findViewById(R.id.favorite_button)
        private val downloadView: AppCompatImageView = view.findViewById(R.id.download_button)

        fun bind(cat: CatModel) {
            imageView.loadImage(cat.url)

            updateFavorite(cat)

            downloadView.setOnClickListener { onDownloadClick.invoke(cat) }
        }

        fun updateFavorite(cat: CatModel) {
            val context = imageView.context

            val favoriteColor = if (cat.isFavorite) {
                ContextCompat.getColor(context, R.color.colorAccent)
            } else {
                ContextCompat.getColor(context, R.color.colorGrey)
            }

            ImageViewCompat.setImageTintList(favoriteView, ColorStateList.valueOf(favoriteColor))

            favoriteView.setOnClickListener { onAddFavoriteClick.invoke(cat) }
        }
    }
}
