package com.ysn.examplesquarecamera.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ysn.examplesquarecamera.R
import java.io.File

class AdapterPhotos (private var photos: MutableList<File>) : RecyclerView.Adapter<AdapterPhotos.ViewHolderPhoto>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPhoto {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false)
        return ViewHolderPhoto(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderPhoto, position: Int) {
        val photo = photos[position]
        holder.bindData(photo = photo)
    }

    override fun getItemCount(): Int = photos.size

    fun refresh(photos: MutableList<File>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    inner class ViewHolderPhoto(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView = itemView.findViewById<ImageView>(R.id.image_view_photo_item_photo)

        fun bindData(photo: File) {
            Glide.with(itemView)
                    .load(photo)
                    .into(imageView)
        }

    }

}