package com.example.yournursery

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import retrofit2.Response
import java.net.ConnectException

class Repository(val context: Context) {

    //api repo
    val reposervice = yournurseryapi.service
    suspend fun getallproductsfromapi(): List<Productresponse>? {
        var return1:List<Productresponse>? = null
        try {
            return1 =  reposervice.getproducts().body()
//            Log.e("jeloo", "getallproductsfromapi: $return1", )
        }catch (ex :Exception){
//            Log.e("hi", "getallproductsfromapi: ${ex}", )
            return1 = null
        }


        return return1
    }

    suspend fun getparamproducts(string: String):List<Productresponse>?{
        var return2:List<Productresponse>? = null
        try {
            return2 =  reposervice.getbypath(string).body()
        }catch (ex :Exception){
//            Log.e("hi", "getallproductsfromapi: ${ex}", )
            return2 = null
        }


        return return2
    }

    suspend fun neworders(ordercart: List<ordercart>): response<String>? {
       return reposervice.neworder(ordercart).body()
    }



    //cartdatabase repo
    val cartdata = cartdatabase.getdatabaseofcart(context)
    val cartdao = cartdata.getcartdao()
    suspend fun insert(cart: cart){
        cartdao.inserttocart(cart)
    }
    suspend fun detetecartitem(cart: cart){
        cartdao.deleteitem(cart)
    }
    suspend fun getcartdatalist(): List<cart>{
        return cartdao.getcart()
    }
    suspend fun updateitemincart(cart: cart){
        cartdao.updateitem(cart)
    }
}