package com.example.yournursery

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductListAdaptor(
    var productlist : List<Productresponse>,
    val context: Context, val imageButton: ImageButton,
    val viewModel: ViewModel,
    val Listener:(int:Int)->Unit): RecyclerView.Adapter<ProductListAdaptor.Productlistviewholder>() {
    var text = viewModel.cartlistlive.value
    inner class Productlistviewholder(view:View):RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.productimage)
        val name = view.findViewById<TextView>(R.id.productname)
        val type = view.findViewById<TextView>(R.id.producttype)
        val price = view.findViewById<TextView>(R.id.productprice)
        val button = view.findViewById<Button>(R.id.button)

        init {
            Log.e("hi", "hi: ${productlist}", )
            itemView.setOnClickListener {
                Listener.invoke(adapterPosition)
            }
            button.setOnClickListener {

                if (text != null) {
                    if (text!!.filter { it.productname == productlist.get(adapterPosition).productname }.isEmpty() ){
                        imageButton.setImageResource(R.drawable.baseline_shopping_cart_24)
                        viewModel.insertitemtocart(cart(id = 0,name.text.toString(),
                            1,type.text.toString(),productlist.get(adapterPosition).imageurl!!,productlist.get(adapterPosition).ownerid))
                        Toast.makeText(context, "Product added to cart visit cart for further process", Toast.LENGTH_SHORT).show()
                        viewModel.getcartlist()
                        runnable()

                        text = viewModel.cartlistlive.value

                    }else{
                        val list = text!!.filter {  it.productname == name.text.toString() }
                        ++list.get(0).numberofproduct
                        viewModel.upadteiteminthecartviewmodel(list.get(0))
                        Toast.makeText(context, "Product updated", Toast.LENGTH_SHORT).show()
                        viewModel.getcartlist()
                        runnable()

                        text = viewModel.cartlistlive.value
                    }

                }else{
                    imageButton.setImageResource(R.drawable.baseline_shopping_cart_24)
                    viewModel.insertitemtocart(cart(id = 0,name.text.toString(),
                        1,type.text.toString(),productlist.get(adapterPosition).imageurl!!,productlist.get(adapterPosition).ownerid))
                    Toast.makeText(context, "Product added to cart visit cart for further process",
                        Toast.LENGTH_SHORT).show()
                    viewModel.getcartlist()
                    runnable()
//
//                    text = viewModel.cartlistlive.value
                }


            }
        }
    }

    private fun runnable() = Runnable{
        viewModel.getcartlist()
        text = viewModel.cartlistlive.value
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Productlistviewholder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.productsitemlayout,parent,false)
        return Productlistviewholder(layout)
    }

    override fun getItemCount(): Int {
       return productlist.size
    }

    override fun onBindViewHolder(holder: Productlistviewholder, position: Int) {
       with(holder){
           name.text = productlist[position].productname
           type.text= productlist[position].productdescription
           price.text= productlist[position].productprice.toString()
           Glide.with(context).load(productlist[position].imageurl).into(image)
       }
    }
}