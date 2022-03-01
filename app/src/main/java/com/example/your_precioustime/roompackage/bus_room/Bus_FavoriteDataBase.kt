package com.example.your_precioustime.roompackage.bus_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [BusFavoriteEntity::class], version = 1)
abstract class Bus_FavoriteDataBase:RoomDatabase() {
    abstract fun busfavroiteDao() : Bus_FavroiteDAO

    companion object{
        private var INSTANCE : Bus_FavoriteDataBase?=null

        fun getInstance(context: Context): Bus_FavoriteDataBase?{
            if(INSTANCE==null){
                synchronized(Bus_FavoriteDataBase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        Bus_FavoriteDataBase::class.java,
                        "bus_favorite.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE
        }
    }
}