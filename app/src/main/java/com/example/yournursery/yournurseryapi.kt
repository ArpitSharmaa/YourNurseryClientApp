package com.example.yournursery


import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

val base_url = "http://192.168.29.199:8080"

interface yournurseryapi {
    @GET("/products")
    suspend fun getproducts():Response<List<Productresponse>>
    @GET("/productsbyparam/{param}")
    suspend fun getbypath(@Path("param") param: String):Response<List<Productresponse>>

    @POST("/neworder")
    suspend fun neworder( @Body neworder :List<ordercart>):Response<response<String>>
    companion object{
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create(gson)).build()
        val service : yournurseryapi = retrofit.create(yournurseryapi::class.java)
    }
}