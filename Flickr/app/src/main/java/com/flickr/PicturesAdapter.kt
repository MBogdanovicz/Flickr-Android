package com.flickr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.flickr.com.flickr.model.Photo
import com.squareup.picasso.Picasso

class PicturesAdapter(var photos: ArrayList<Photo>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val photo = photos[position]

        val viewHolder: ViewHolder
        var view = convertView

        if (view == null) {

            view = LayoutInflater.from(parent?.context).inflate(R.layout.gridview_item, parent,false)
            viewHolder = ViewHolder(view)

            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val path = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_s.jpg"
        Picasso.get().load(path).into(viewHolder.photo)

        return view!!
    }

    override fun getItem(position: Int): Photo {
        return photos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return photos.size
    }

    fun addPhotos(photos: ArrayList<Photo>) {
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }
}

private class ViewHolder(view:View) {

    var photo: ImageView = view.findViewById(R.id.photo)
}