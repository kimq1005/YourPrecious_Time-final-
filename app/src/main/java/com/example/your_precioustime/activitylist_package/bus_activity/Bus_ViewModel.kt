package com.example.your_precioustime.activitylist_package.bus_activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.your_precioustime.ObjectManager.citycodeCallObject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.mo_del.Item
import com.example.your_precioustime.mo_del.ResultBusItem
import com.example.your_precioustime.mo_del.StationItem

class Bus_ViewModel : ViewModel() {

    // Bus_Activity의 버스검색 데이터
    private val _stationItem = MutableLiveData<List<StationItem>>()

    // Bus_StationInfo의 데이터
    private val _stationinfoItem = MutableLiveData<List<ResultBusItem>>()

//    private val _stationinfoItem = MutableLiveData<List<Item>>()


    val stationItem: LiveData<List<StationItem>>
        get() = _stationItem


//    val stationinfoItem : LiveData<List<Item>>
//        get() = _stationinfoItem

    val stationinfoItem: LiveData<List<ResultBusItem>>
        get() = _stationinfoItem


    // Bus_Activity의 버스검색 데이터
    fun setStationBusItem(list: List<StationItem>) {
        _stationItem.value = list
    }


    // Bus_StationInfo의 데이터
    fun setStationInfoItem(list: List<ResultBusItem>) {
        _stationinfoItem.value = list

    }


}