package com.example.your_precioustime.mo_del

import com.example.your_precioustime.ObjectManager.Myobject

data class SubwayItem(
    var subwayId:String?,
    val trainLineNm : String?,
    val bstatnNm:String?,
    val arvlMsg2 : String?,
//    val barvlDt:Int?
){
    val mytext : String
        get() {
            return  Myobject.myobject.changeSubwayText(arvlMsg2.toString())
        }

    val trainLineNmtext:String
        get() {
            return Myobject.myobject.changeSubwayResultText(trainLineNm.toString())
        }
}