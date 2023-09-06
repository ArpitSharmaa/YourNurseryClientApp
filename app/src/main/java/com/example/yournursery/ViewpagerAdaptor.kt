package com.example.yournursery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class ViewpagerAdaptor(var images : MutableList<Int>,val viewPager2: ViewPager2):
    RecyclerView.Adapter<ViewpagerAdaptor.viewholder>() {
    inner class viewholder(view: View):RecyclerView.ViewHolder(view) {
            val imageview = view.findViewById<ImageView>(R.id.imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.viewpagerlayout,parent,false)
        return viewholder(layout)
    }

    override fun getItemCount(): Int {
     return images.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.imageview.setImageResource(images.get(position))
        if (position == images.size-1){
            viewPager2.post(runnable())
        }
    }

    private fun runnable()= Runnable{
        images.addAll(images)
        notifyDataSetChanged()

    }
}