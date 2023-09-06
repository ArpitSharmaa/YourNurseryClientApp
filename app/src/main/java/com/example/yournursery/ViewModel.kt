package com.example.yournursery

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.*
import kotlinx.coroutines.*

class ViewModel(application: Application):AndroidViewModel(Application()) {
    val repo = Repository(application)
    val application1 = application

    //api viewmodel
    var allproducts = MutableLiveData<List<Productresponse>?>()
    val allproductlive : LiveData<List<Productresponse>?>
    get() = allproducts
    var fetchedlist = null
    fun getallproductsfromrepo(){

            viewModelScope.launch{
                withContext(Dispatchers.Main){
                    val z = allproductscall()
//                    Log.e("hi", "getallproductsfromrepo: $z", )
                    allproducts.value = z


                }
            }



    }

    private suspend fun allproductscall(): List<Productresponse>? {
        val async = viewModelScope.async(Dispatchers.IO) {
                repo.getallproductsfromapi()

        }
        return async.await()
    }



    var paramproducts = MutableLiveData<List<Productresponse>?>()
    val paramproductlive : LiveData<List<Productresponse>?>
        get() = paramproducts
    fun getaparamproductsfromrepo(string: String){

        viewModelScope.launch{
            withContext(Dispatchers.Main){
                val z = paramproductscall(string)
                paramproducts.value = z


            }
        }



    }

    private suspend fun paramproductscall(string: String): List<Productresponse>? {
        val async = viewModelScope.async(Dispatchers.IO) {
            repo.getparamproducts(string)

        }
        return async.await()
    }

    fun neworder ( ordercart: List<ordercart>){
        var z:response<String>? = null
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                z =callorder(ordercart)
                Toast.makeText(application1.applicationContext, "success", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private suspend fun callorder(ordercart: List<ordercart>): response<String>? {
       val r = viewModelScope.async {
           repo.neworders(ordercart)
       }
        return r.await()
    }


    // cartroom viewmodel
    fun insertitemtocart(cart: cart){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repo.insert(cart)
            }
        }
    }
    fun deleteitemfromcart(cart: cart){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repo.detetecartitem(cart)
            }
        }
    }
    fun upadteiteminthecartviewmodel(cart: cart){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repo.updateitemincart(cart)
            }
        }
    }
    var cartlist = MutableLiveData<List<cart>>()
    val cartlistlive : LiveData<List<cart>>
    get() = cartlist
    fun getcartlist(){
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                val recieved = getallcartfromrepo()
                cartlist.value = recieved
            }
        }
    }

    private suspend fun getallcartfromrepo(): List<cart> {
        val list = viewModelScope.async(Dispatchers.IO){
            repo.getcartdatalist()
        }
        return list.await()
    }

}