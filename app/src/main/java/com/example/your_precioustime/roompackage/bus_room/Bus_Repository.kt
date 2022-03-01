package com.example.your_precioustime.roompackage.bus_room

import android.app.Application
import androidx.lifecycle.LiveData

class Bus_Repository(application: Application) {

    private val busFavroiteDao: Bus_FavroiteDAO
    private val busFavoriteEntity: LiveData<List<BusFavoriteEntity>>


    //초기화
    init {
        val busfavoritedb = Bus_FavoriteDataBase.getInstance(application)!!

        busFavroiteDao = busfavoritedb.busfavroiteDao()
        busFavoriteEntity = busfavoritedb.busfavroiteDao().busFavoriteGetAll()
    }

    fun busgetAll(): LiveData<List<BusFavoriteEntity>> {
        return busFavroiteDao.busFavoriteGetAll()
    }

    fun busInsert(busFavoriteEntity: BusFavoriteEntity) {
        busFavroiteDao.busFavoriteInsert(busFavoriteEntity)
    }

    fun busDelete(busFavoriteEntity: BusFavoriteEntity) {
        busFavroiteDao.busFavoriteDelete(busFavoriteEntity)
    }

}