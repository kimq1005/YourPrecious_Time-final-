package com.example.your_precioustime.roompackage.bus_room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Bus_RoomViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Bus_Repository(application)

    //파라미터를 넘기위한 팩토리 객체
    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return Bus_RoomViewModel(application) as T
        }
    }

    fun businsert(
        citycode: String?,
        stationnodenode: String,
        stationName: String,
        stationNodeNumber: String
    ) {


        val stationInfo = BusFavoriteEntity(
            null,
            citycode, stationnodenode, stationName, stationNodeNumber
        )

        CoroutineScope(Dispatchers.IO).launch {
            repository.busInsert(stationInfo)
        }

    }

    fun busgetAll() : LiveData<List<BusFavoriteEntity>>{
        return repository.busgetAll()
    }

    fun busdelete(busFavoriteEntity: BusFavoriteEntity){

        CoroutineScope(Dispatchers.IO).launch{
            repository.busDelete(busFavoriteEntity)
        }

    }





}