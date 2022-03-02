package com.example.your_precioustime.roompackage.subway_room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Subway_FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Subway_Repository(application)

    class Factory(val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return Subway_FavoriteViewModel(application) as T
        }

    }

    fun subwaygetAll() : LiveData<List<SubwayFavoriteEntity>>{
        return repository.subwaygetAll()
    }


    fun subwayInsert(subwayName:String){
        val subwayname = SubwayFavoriteEntity(null,subwayName)

        CoroutineScope(Dispatchers.IO).launch {
            repository.subwayInsert(subwayname)
        }
    }

    fun subwayDelete(subwayFavoriteEntity: SubwayFavoriteEntity){
        CoroutineScope(Dispatchers.IO).launch {
            repository.subwayDelete(subwayFavoriteEntity)
        }
    }


}