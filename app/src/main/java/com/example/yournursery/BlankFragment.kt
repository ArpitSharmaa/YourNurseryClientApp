package com.example.yournursery

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BlankFragment : Fragment() {
    lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.getcartlist()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buybutton = view.findViewById<Button>(R.id.buy)
        buybutton.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment_to_signup_loginfrag)
        }
        val recycle = view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(context)
        }
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar2)
        progressBar.visibility = View.INVISIBLE
        val livelist = viewModel.cartlistlive
        livelist.observe(viewLifecycleOwner, Observer {
            if (it==null){
                progressBar.visibility = View.VISIBLE
            }else{
                recycle.adapter = cartadaptor(it,requireContext(),viewModel)
            }
        })
    }


}