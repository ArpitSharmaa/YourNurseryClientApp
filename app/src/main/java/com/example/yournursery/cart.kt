package com.example.yournursery

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart")
data class cart(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val productname: String,
    var numberofproduct : Long,
    val productdescription : String,
    val image:String,
    val ownerid:String
)

