package com.example.yournursery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class viewpageradaptorfordetails(val images:List<String>,val context: Context):RecyclerView.Adapter<viewpageradaptorfordetails.viewholder>() {
    inner class viewholder(view: View): RecyclerView.ViewHolder(view) {
        val imageview = view.findViewById<ImageView>(R.id.imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.viewpage,parent,false)
        return viewholder(layout)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        Glide.with(context).load(images.get(position)).into(holder.imageview)

    }

}