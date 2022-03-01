package com.example.your_precioustime.roompackage.bus_room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "busFavoriteEntity")
data class BusFavoriteEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Long?,
    var citycode:String?,
    var stationnodenode:String,
    var stationName:String,
    var stationNodeNumber:String
)