package com.example.your_precioustime.roompackage.bus_room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.your_precioustime.SecondActivity.DB.SubwayDB.TestFavoriteModel


@Dao
interface Bus_FavroiteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun busFavoriteInsert(busFavoriteEntity: BusFavoriteEntity)

    @Query("SELECT * FROM BusFavoriteEntity")
    fun busFavoriteGetAll() : LiveData<List<BusFavoriteEntity>>

    @Delete
    fun busFavoriteDelete(BusFavoriteEntity: BusFavoriteEntity)
}