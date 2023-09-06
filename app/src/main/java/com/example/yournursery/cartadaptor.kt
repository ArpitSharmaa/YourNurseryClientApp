package com.example.yournursery

import  android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.logging.Handler

class cartadaptor(var list: List<cart>?,val context: Context,val viewModel: ViewModel ):RecyclerView.Adapter<cartadaptor.viewholder>(){
    inner class viewholder(view:View):RecyclerView.ViewHolder(view) {
        val nameofproduct = view.findViewById<TextView>(R.id.productnameeee)
        val quantity = view.findViewById<TextView>(R.id.quantity)
        val deletebut = view.findViewById<ImageButton>(R.id.deletitem)
        val plusone = view.findViewById<ImageButton>(R.id.positiveitem)
        val minusone = view.findViewById<ImageButton>(R.id.negativeitem)
        val image = view.findViewById<ImageView>(R.id.imageView2)
        init {


            deletebut.setOnClickListener {
                list?.let { it1 -> viewModel.deleteitemfromcart(it1.get(adapterPosition)) }
                viewModel.getcartlist()
                runnable()
            }

        }

    }

    private fun runnable() = Runnable {
        viwcart()
            viewModel.getcartlist()
            list = viewModel.cartlistlive.value
            notifyDataSetChanged()



    }

    private fun viwcart() = Runnable{
        viewModel.getcartlist()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val layoutq = LayoutInflater.from(parent.context).inflate(R.layout.cartitemlayout,parent,false)
        return viewholder(layoutq)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        with(holder){
            nameofproduct.text = list?.get(position)?.productname ?: ""
            quantity.text = list?.get(position)?.numberofproduct.toString()
            Glide.with(context).load(list?.get(position)?.image).into(image)
            plusone.setOnClickListener {
                list?.get(position)?.numberofproduct = list?.get(position)?.numberofproduct!! + 1
                quantity.text = (list?.get(position)?.numberofproduct).toString()
                list?.let { it1 -> viewModel.upadteiteminthecartviewmodel(it1.get(position)) }
            }
            minusone.setOnClickListener {
                if (list?.get(position)!!.numberofproduct >1){
                --list!!.get(position).numberofproduct
                quantity.text = (list!!.get(position).numberofproduct).toString()
                viewModel.upadteiteminthecartviewmodel(list!!.get(position))
            }else{
                viewModel.deleteitemfromcart(list!!.get(position))
                    quantity.text = (list!!.get(position).numberofproduct-1).toString()
                    viewModel.getcartlist()
                    runnable()
                }
            }
        }

    }
}