package com.example.your_precioustime.roompackage.subway_room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subwayFavoriteEntity")
data class SubwayFavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long?,
    var subwayName:String
)