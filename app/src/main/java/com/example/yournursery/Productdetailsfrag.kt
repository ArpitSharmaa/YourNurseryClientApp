package com.example.yournursery

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Productdetailsfrag : Fragment() {
    private var param1: Int?= null
    private var param2: String?= null
    lateinit var  viewModel:ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        arguments?.let {
        param1 = it.getInt("key")
            param2= it.getString("frag")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_productdetailsfrag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var livedata = viewModel.allproductlive
        when(param2){
            "all" -> {
                viewModel.getallproductsfromrepo()
                livedata = viewModel.allproductlive
            }
            else -> {
            viewModel.getaparamproductsfromrepo(param2!!)
                livedata = viewModel.paramproductlive
            }

        }
        val add = view.findViewById<Button>(R.id.Addtocart)

        val viewPager2 = view.findViewById<ViewPager2>(R.id.viewpGER)
        val list :MutableList<String> = mutableListOf()
        val about = view.findViewById<TextView>(R.id.produstabut)
        val nameofproduct = view.findViewById<TextView>(R.id.productnaming)
        val price = view.findViewById<TextView>(R.id.Price)
        viewPager2.adapter = viewpageradaptorfordetails(list,requireContext())
        val typr = view.findViewById<TextView>(R.id.typeofplant)
        var ownerid :String? = null
        livedata.observe(viewLifecycleOwner, Observer {
            if (livedata.value !=null) {
                about.text = param1?.let { livedata.value!!.get(it).about.map {
                    it.details
                }.joinToString(separator = "\n")}
                nameofproduct.text = param1?.let { livedata.value!!.get(it).productname }
                price.text = param1?.let { livedata.value!!.get(it).productprice.toString() }
                typr.text= param1?.let { livedata.value!!.get(it).productdescription }
                ownerid= param1?.let { it1 -> livedata.value!!.get(it1).ownerid }
                add.setOnClickListener {
                    param1?.let { it1 ->
                        livedata.value!!.get(it1).imageurl?.let { it1 ->
                            cart(id = 0,nameofproduct.text.toString(),
                                1,typr.text.toString(), it1
                            ,ownerid!!)
                        }?.let { it2 -> viewModel.insertitemtocart(it2) }
                    }
                    Toast.makeText(context, "Product added to cart visit cart for further process", Toast.LENGTH_SHORT).show()
                }
                    livedata.value!!.get(param1!!).imageurl?.let { it1 -> list.add(it1) }
                livedata.value!!.get(param1!!).imageurl2?.let { it1 -> list.add(it1) }
                livedata.value!!.get(param1!!).imageurl3?.let { it1 -> list.add(it1) }
                livedata.value!!.get(param1!!).imageurl4?.let { it1 -> list.add(it1) }
                livedata.value!!.get(param1!!).imageurl5?.let { it1 -> list.add(it1) }
                viewPager2.adapter = viewpageradaptorfordetails(list,requireContext())


            }
        })
        val handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                viewPager2.beginFakeDrag()
                viewPager2.fakeDragBy(-4f)
                viewPager2.endFakeDrag()
                handler.postDelayed(this,2000)
            }

        },2000)
//        val buy = view.findViewById<Button>(R.id.buynow)
//        buy.setOnClickListener {
//            findNavController().navigate(R.id.action_productdetailsfrag_to_signup_loginfrag)
//        }
        val floatingcart = view.findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        floatingcart.setOnClickListener {
            findNavController().navigate(R.id.action_productdetailsfrag_to_blankFragment)
        }


    }


}