package com.example.yournursery

import android.os.Bundle
import android.os.Handler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CategoryFragment : Fragment() {
    lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val string :List<String> = listOf("Summer Plant","Winter Plant","Desert Plant","Indoor Plants")
        val summer = view.findViewById<ConstraintLayout>(R.id.suumers)
        val winters = view.findViewById<ConstraintLayout>(R.id.winters)
        val desert = view.findViewById<ConstraintLayout>(R.id.Desert)
        val allplants = view.findViewById<ConstraintLayout>(R.id.allplants)
        val indoor = view.findViewById<ConstraintLayout>(R.id.indoor)
        val floatingbutton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingbutton.setOnClickListener{
            findNavController().navigate(R.id.action_categoryFragment_to_blankFragment)
        }
//        val image = view.findViewById<ImageView>(R.id.imageView9)
        val newlist = mutableListOf(R.drawable.image1,R.drawable.image2,R.drawable.image3)
//            image.setImageResource(R.drawable.image1)
        allplants.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("key","all")
            findNavController().navigate(R.id.action_categoryFragment_to_PRODUCTFRAGMENT,bundle)
        }
        summer.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("key",string[0])
            findNavController().navigate(R.id.action_categoryFragment_to_PRODUCTFRAGMENT,bundle)
        }
        winters.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("key",string[1])
            findNavController().navigate(R.id.action_categoryFragment_to_PRODUCTFRAGMENT,bundle)
        }
        desert.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("key",string[2])
            findNavController().navigate(R.id.action_categoryFragment_to_PRODUCTFRAGMENT,bundle)
        }
        indoor.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("key",string[3])
            findNavController().navigate(R.id.action_categoryFragment_to_PRODUCTFRAGMENT,bundle)
        }

        val viewpager = view.findViewById<ViewPager2>(R.id.viewpGER).apply {
            adapter = ViewpagerAdaptor(newlist as MutableList<Int>,this)

        }
        val handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                viewpager.beginFakeDrag()
                viewpager.fakeDragBy(-10f)
                viewpager.endFakeDrag()
                handler.postDelayed(this,2000)
            }

        },2000)



//            var img: Int = 0
//            val handler = Handler()
//        while (img < newlist.size){
//            handler.postDelayed({image.setImageResource(newlist.get(img))},1000)
//            if (2>img){
//                img = 0
//            }else{
//                img++
//            }
//        }
//            handler.postDelayed(object : Runnable{
//                override fun run() {
//                   handler.postDelayed({
//                       image.setImageResource(R.drawable.image2)
//                   },1000)
//                    handler.postDelayed({
//                        image.setImageResource(R.drawable.image3)
//                    },1000)
//                    handler.postDelayed({
//                        image.setImageResource(R.drawable.image1)
//                    },1000)
//
//
//
//
//                    handler.postDelayed(this,1000)
//                }
//
//
//            }, 1000)





    }

}