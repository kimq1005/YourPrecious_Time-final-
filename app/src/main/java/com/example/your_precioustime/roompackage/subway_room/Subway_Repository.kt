package com.example.your_precioustime.roompackage.subway_room

import android.app.Application
import androidx.lifecycle.LiveData

class Subway_Repository(application: Application) {
    private val subwayFavoritedao : Subway_FavoriteDAO
    private val subwayFavoriteEntity : LiveData<List<SubwayFavoriteEntity>>

    //초기화
    init {
        val subwayfavoritedb = Subway_FavoriteDataBase.getInstance(application)!!

        subwayFavoritedao = subwayfavoritedb.subwayfavroiteDao()
        subwayFavoriteEntity = subwayfavoritedb.subwayfavroiteDao().subwayGetAll()
    }

    fun subwaygetAll(): LiveData<List<SubwayFavoriteEntity>> {
        return subwayFavoritedao.subwayGetAll()
    }

    fun subwayInsert(subwayFavoriteEntity: SubwayFavoriteEntity){
        subwayFavoritedao.subwayInsert(subwayFavoriteEntity)
    }

    fun subwayDelete(subwayFavoriteEntity: SubwayFavoriteEntity){
        subwayFavoritedao.subwayDelete(subwayFavoriteEntity)
    }
}