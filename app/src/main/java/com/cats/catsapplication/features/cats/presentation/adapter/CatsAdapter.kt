package com.cats.catsapplication.features.cats.presentation.adapter

import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v4.widget.ImageViewCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cats.catsapplication.R
import com.cats.catsapplication.core.utils.loadImage
import com.cats.catsapplication.features.cats.presentation.model.CatModel


class CatsAdapter(private val cats: MutableList<CatModel>) : RecyclerView.Adapter<CatsAdapter.ViewHolder>() {

    fun updateItems(newCats: List<CatModel>) {
        cats.clear()
        cats.addAll(newCats)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_cats, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cats[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = cats.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: AppCompatImageView = view.findViewById(R.id.image_view)
        private val favoriteView: AppCompatImageView = view.findViewById(R.id.favorite_button)

        fun bind(cat: CatModel) {
            val context = imageView.context

            imageView.setImageDrawable(null)
            imageView.loadImage(cat.url)

            val favoriteColor = if (cat.isFavorite) {
                ContextCompat.getColor(context, R.color.colorAccent)
            } else {
                ContextCompat.getColor(context, R.color.colorGrey)
            }

            ImageViewCompat.setImageTintList(favoriteView, ColorStateList.valueOf(favoriteColor))
        }
    }
}
