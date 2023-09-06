package com.example.yournursery

import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.delay
import java.net.ConnectException
import java.util.*


class ProductsFragment : Fragment() {
        private var string:String? = null
  private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            if (it != null) {
                string = it.getString("key")
            }
        }
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
            if (string == "all"){
                viewModel.getallproductsfromrepo()
            }else{
                viewModel.getaparamproductsfromrepo(string!!)
            }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var livedata = viewModel.allproductlive
        when(string){
            "all" -> { livedata = viewModel.allproductlive}
            else -> { livedata = viewModel.paramproductlive}
        }

        val progressBar =  view.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE
        val search = view.findViewById<SearchView>(R.id.searchView)
        val recyclerView = view.findViewById<RecyclerView>(R.id.productslist).apply {
            layoutManager= GridLayoutManager(context,2)}
        val swipeRefreshLayout= view.findViewById<SwipeRefreshLayout>(R.id.swipe)
        val imageButton = view.findViewById<ImageButton>(R.id.imageButton)
        imageButton.setOnClickListener {
            viewModel.getcartlist()
            findNavController().navigate(R.id.action_PRODUCTFRAGMENT_to_blankFragment)
        }
        swipeRefreshLayout.setOnRefreshListener {
            onDestroy()
            onCreate(savedInstanceState)

        }

        fun startLoading() {
            val timer = Timer()
            var progress = 0

            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    progress += 10
                    progressBar.progress = progress
                    if (progress >= 100) {
                        timer.cancel()
                        startLoading() // Restart loading
                    }
                }
            }, 1000, 1000)
        }
        startLoading()
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val list = livedata.value?.filter {
                    p0?.let { it1 -> it.productname?.contains(it1, ignoreCase = true) } ?: false
                }
                viewModel.getcartlist()
                recyclerView.adapter= list?.let { ProductListAdaptor(it,context!!,imageButton,viewModel){
                    val bundle = Bundle()
                    bundle.putInt("key",it)
                    bundle.putString("frag",string)
                    findNavController().navigate(R.id.action_PRODUCTFRAGMENT_to_productdetailsfrag,bundle)
                } }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
              val list = livedata.value?.filter {
                  p0?.let { it1 -> it.productname?.contains(it1,ignoreCase = true) } ?: false
              }
                viewModel.getcartlist()
                recyclerView.adapter= list?.let { ProductListAdaptor(it,context!!,imageButton,viewModel){
                    val bundle = Bundle()
                    bundle.putInt("key",it)
                    bundle.putString("frag",string)
                    findNavController().navigate(R.id.action_PRODUCTFRAGMENT_to_productdetailsfrag,bundle)
                } }
                return false
            }

        })
//        livedata.observe(viewLifecycleOwner, Observer {
//            if (livedata.value == null){
//                swipeRefreshLayout.isRefreshing = false
//                progressBar.visibility = View.VISIBLE
//                val handle = Handler()
//                handle.postDelayed({
//                    progressBar.visibility = View.INVISIBLE
//                    Toast.makeText(context, "Server is down", Toast.LENGTH_SHORT).show()
//                }, 5000)
//            }
//        })

        livedata.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                swipeRefreshLayout.isRefreshing = false
                progressBar.visibility = View.VISIBLE
                val handle = Handler()
                handle.postDelayed({
                    progressBar.visibility = View.INVISIBLE
                   try{ Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()}catch (_:java.lang.Exception){

                   }
                }, 5000)

            }else{
                swipeRefreshLayout.isRefreshing = false
                if (it.isEmpty()){
                    progressBar.visibility = View.VISIBLE
                    val handle  = Handler()
                    handle.postDelayed({
                        try {
                            progressBar.visibility = View.INVISIBLE
                            Toast.makeText(requireContext(), "No Certain Type Products Available", Toast.LENGTH_SHORT).show()
                        }catch (_:java.lang.Exception){
                        }

                    }, 5000)
                }else{
                    viewModel.getcartlist()
                    recyclerView.adapter = ProductListAdaptor(it,requireContext(),imageButton,viewModel){
                        val bundle = Bundle()
                        bundle.putInt("key",it)
                        bundle.putString("frag",string)
                        findNavController().navigate(R.id.action_PRODUCTFRAGMENT_to_productdetailsfrag,bundle)
                    }


                }
            }
        })
    }


}