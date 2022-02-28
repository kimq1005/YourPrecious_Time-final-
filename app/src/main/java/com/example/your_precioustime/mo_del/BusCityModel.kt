package com.example.your_precioustime.mo_del

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

data class BusCityModel(
    val hi : String
)


@Xml(name = "response")
data class City(
    @Element
    val header: CityHeader,

    @Element
    val body : CityBody?
)

@Xml(name = "header")
data class CityHeader(
    @PropertyElement
    val resultCode:Int,

    @PropertyElement
    val resultMsg:String
)

@Xml(name="body")
data class CityBody(
    @Element
    val items: Cityitems?
)

@Xml
data class Cityitems(
    @Element(name="item")
    val item : List<CityItem>?
)

@Xml
data class CityItem(
    @PropertyElement(name="citycode") var citycode:Int?,
//    @PropertyElement(name="cityname") var cityname:String?
){
    constructor() : this(null)
}









