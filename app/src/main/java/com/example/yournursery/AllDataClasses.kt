package com.example.yournursery

data class response<T>(val string: T)

// Restrofit product response
data class Productresponse(
    val productname:String?,
    val productdescription:String?,
    val productprice:Long?,
    val imageurl:String?,
    val imageurl2:String?,
    val imageurl3:String?,
    val imageurl4:String?,
    val imageurl5:String?,
    val about: List<aboutproduct>,
    val ownerid:String
    )

data class aboutproduct(
    val details: String
)
data class ordercart(
    val productname: String,
    var numberofproduct : Long,
    val productdescription : String,
    val image:String,
    val ownerid:String,
    val email:String,
    val fullname :String,
    val mobile :String,
    val address : String,
    val state : String,
    val city : String,
    val postal : String
)