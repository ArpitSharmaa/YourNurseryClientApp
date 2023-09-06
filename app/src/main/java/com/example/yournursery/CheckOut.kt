package com.example.yournursery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth


class CheckOut : Fragment() {
    lateinit var auth :FirebaseAuth
private var email :String? = null
lateinit var viewmodel:ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = ViewModelProvider(this).get(ViewModel::class.java)
        viewmodel.getcartlist()
        arguments?.let {
            email = it.getString("email")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_out, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val test = view.findViewById<TextView>(R.id.email)
        test.text = email
        val homebutton = view.findViewById<ImageButton>(R.id.imageButton4)
        homebutton.setOnClickListener {
            findNavController().navigate(R.id.action_checkOut_to_categoryFragment)
        }
        val signout = view.findViewById<Button>(R.id.button2)
        signout.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_checkOut_to_blankFragment)
        }
        val orderelist: MutableList<ordercart> = mutableListOf()
        val buy = view.findViewById<Button>(R.id.button3)
        viewmodel.getcartlist()
        val fullname = view.findViewById<TextView>(R.id.fullname)
        val mobile = view.findViewById<TextView>(R.id.mobile)
        val adress = view.findViewById<TextView>(R.id.address)
        val state = view.findViewById<TextView>(R.id.state)
        val city = view.findViewById<TextView>(R.id.city)
        val postal = view.findViewById<TextView>(R.id.postal)
        buy.setOnClickListener {


            if (fullname.text.isBlank() || mobile.text.isBlank() || adress.text.isBlank() || state.text.isBlank() || city.text.isBlank() || postal.text.isBlank()) {
                Toast.makeText(context, "Please Fill All The Required Fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val z = viewmodel.cartlistlive
                z.observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        for (a in it) {

                            orderelist.add(
                                (ordercart(
                                    a.productname,
                                    a.numberofproduct,
                                    a.productdescription,
                                    a.image,
                                    a.ownerid,
                                    email!!,
                                    fullname.text.toString(),
                                    mobile.text.toString(),
                                    adress.text.toString(),
                                    state = state.text.toString(),
                                    city.text.toString(),
                                    postal.text.toString()
                                ))
                            )

                        }
                    }

                })

                viewmodel.neworder(orderelist)


            }

        }
    }

}