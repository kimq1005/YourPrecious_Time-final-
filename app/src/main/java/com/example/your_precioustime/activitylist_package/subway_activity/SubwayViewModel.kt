package com.example.your_precioustime.activitylist_package.subway_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.your_precioustime.mo_del.SubwayItem

class SubwayViewModel:ViewModel() {

    private val _subwayItem = MutableLiveData<List<SubwayItem>>()

    val subwayItem : LiveData<List<SubwayItem>>
        get() = _subwayItem


    // Subway_Activity 데이터
    fun setSubwayItem(list : List<SubwayItem>){
        _subwayItem.value = list
    }
}