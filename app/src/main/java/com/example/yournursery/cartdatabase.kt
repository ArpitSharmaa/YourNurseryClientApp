package com.example.yournursery

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [cart::class], version = 2)
abstract class cartdatabase:RoomDatabase() {
    abstract fun getcartdao():cartdao

    companion object{
        class migration12  :Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE cart ADD COLUMN ownerid TEXT NOT NULL")
            }

        }
        @Volatile
        var instance : cartdatabase? = null
        fun getdatabaseofcart(context: Context):cartdatabase{
            if (instance == null){
                synchronized(this){
                    instance = Room.databaseBuilder(context.applicationContext, cartdatabase::class.java,"cartdatabase").addMigrations(migration12()).build()
                }
            }
            return instance!!
        }
    }
}