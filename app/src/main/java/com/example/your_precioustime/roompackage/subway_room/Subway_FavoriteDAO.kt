package com.example.your_precioustime.roompackage.subway_room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.your_precioustime.SecondActivity.DB.SubwayNameEntity


@Dao
interface Subway_FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun subwayInsert(subwayFavoriteEntity: SubwayFavoriteEntity)

    @Query("SELECT * FROM subwayFavoriteEntity")
    fun subwayGetAll() : LiveData<List<SubwayFavoriteEntity>>

    @Delete
    fun subwayDelete(subwayFavoriteEntity: SubwayFavoriteEntity)
}