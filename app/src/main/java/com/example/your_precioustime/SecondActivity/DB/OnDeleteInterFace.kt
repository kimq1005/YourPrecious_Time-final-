package com.example.your_precioustime.SecondActivity.DB

import com.example.your_precioustime.SecondActivity.DB.SubwayDB.TestFavoriteModel
import com.example.your_precioustime.roompackage.bus_room.BusFavoriteEntity
import com.example.your_precioustime.roompackage.subway_room.SubwayFavoriteEntity

interface OnDeleteInterFace {
    fun onDeleteFavroitelist(testFavoriteModel: TestFavoriteModel)

}

interface OnBusFavroiteListDeleteInterFace{
    fun onDeleteBusFavoriteList(busFavoriteEntity: BusFavoriteEntity)
}

interface OnSubwayFavoriteListDeleteInterFace{
    fun onDeleteSubwayFavoriteList(subwayFavoriteEntity: SubwayFavoriteEntity)
}