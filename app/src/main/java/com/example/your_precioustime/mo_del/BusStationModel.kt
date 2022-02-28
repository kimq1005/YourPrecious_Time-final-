package com.example.your_precioustime.mo_del

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml(name="response")
data class StationBus(
    @Element
    val header: StationHeader,
    @Element
    val body: StationBody,
)

@Xml(name = "header")
data class StationHeader(
    @PropertyElement
    val resultCode: Int,
    @PropertyElement
    val resultMsg: String,
)

@Xml(name = "body")
data class StationBody(
    @Element(name="items")
    val items: StationItems,

    @PropertyElement
    val numOfRows: Int,
    @PropertyElement
    val pageNo: Int,
    @PropertyElement
    val totalCount: Int,
)
//
@Xml
data class StationItems(
    @Element(name = "item")
    val item: List<StationItem>
)

//
@Xml
data class StationItem(
    @PropertyElement(name="nodeid") var nodeid : String?,
    @PropertyElement(name="nodenm") var nodenm : String?,
    @PropertyElement(name="nodeno") var nodeno : String?,
    @PropertyElement(name="gpslati") var gpslati : String?,
    @PropertyElement(name="gpslong") var gpslong : String?
//    @PropertyElement(name="arrtime") var arrtime : Int? // 남은 시간

){
    constructor() : this(null,null,null,null,null)
}

