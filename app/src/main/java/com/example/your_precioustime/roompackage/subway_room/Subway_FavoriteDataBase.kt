package com.example.your_precioustime.roompackage.subway_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SubwayFavoriteEntity::class] , version = 1)
abstract class Subway_FavoriteDataBase : RoomDatabase() {
    abstract fun subwayfavroiteDao() : Subway_FavoriteDAO

    companion object{
        private var INSTANCE : Subway_FavoriteDataBase?=null

        fun getInstance(context: Context):Subway_FavoriteDataBase?{
            if(INSTANCE==null){
                synchronized(Subway_FavoriteDataBase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    Subway_FavoriteDataBase::class.java,
                    "subway_favorite.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE
        }
    }
}