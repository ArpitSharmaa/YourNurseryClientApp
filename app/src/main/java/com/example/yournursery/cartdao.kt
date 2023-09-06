package com.example.yournursery

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface cartdao {
    @Insert
    suspend fun inserttocart(cart: cart)

    @Query("SELECT * FROM cart")
    suspend fun getcart():List<cart>

    @Delete
    suspend fun deleteitem(cart: cart)

    @Update
    suspend fun updateitem(cart:cart)
}