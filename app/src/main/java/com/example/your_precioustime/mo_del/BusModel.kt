package com.example.your_precioustime.mo_del

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

import java.io.Serializable

data class BusModel(
    val nodeid:String,
    val nodenm:String,
    val routeid:String,
    val routeno:String,
    val routetp:String,
    val arrprevstationcnt:String,
    val vehicletp:String,
    val arrtime:String
):Serializable


//@Xml(name="response")
//data class PillInfo(
//    @Element
//    val header: Header,
//
//    @Element
//    val body:Body
//)
//

//@Xml(name="header")
//data class Header(
//    @PropertyElement
//    val resultCode:Int,
//
//    @PropertyElement
//    val resultMsg:String
//)
//
//
//@Xml(name = "body")
//data class Body(
//    @PropertyElement
//    val item :Item
//)
//
//@Xml
//data class Item(
//    @PropertyElement(name="arrtime")
//    val arrtime: String,
//
//    @PropertyElement(name="nodenm")
//    val nodenm: String,
//
//)

//
//@XmlRootElement(name="response")
//@XmlAccessorType(XmlAccessType.FIELD)
//data class holy(
//    @field:XmlElementWrapper(name = "header")
//    @field:XmlElement(name = "resultCode")
////    val resultCode:String? = null,
//    val resultMsg:String? =null,
//
//        )
//
//
//@XmlRootElement(name="item")
//@XmlAccessorType(XmlAccessType.FIELD)
//data class item(
//    val arrtime:String?=null,
//    val nodenm:String?=null
//)
//
//
//@Root(name = "response" , strict = false)
//class XmlResponse{ @field:Element(name="header")
//var mHeader:XmlHeader? = null
//    get() = field
//    set(value)
//    { field = value
//    }
//
//}
//
//@Root(name = "header",strict = false)
//class XmlHeader{
//    @field:Element()
//    var resultCode:String? = null
//        get() = field
//        set(value) {
//            field = value
//        }
//    @field:Element()
//    var resultMsg:String? = null
//        get() = field
//        set(value) {
//            field = value
//        }
//}





@Xml(name="response")
data class Bus(
    @Element
    val header: Header,
    @Element
    val body: Body,
)

@Xml(name = "header")
data class Header(
    @PropertyElement
    val resultCode: Int,
    @PropertyElement
    val resultMsg: String,
)

@Xml(name = "body")
data class Body(
    @Element
    val items: Items,

    @PropertyElement
    val numOfRows: Int,
    @PropertyElement
    val pageNo: Int,
    @PropertyElement
    val totalCount: Int,
)
//
@Xml
data class Items(
    @Element(name = "item")
    val item: List<Item>
)
//
@Xml
data class Item(
    @PropertyElement(name="routeno") var routeno : String?, //노선 번호
    @PropertyElement(name="arrprevstationcnt") var arrprevstationcnt : Int?,    //남은 정류장  번호
    @PropertyElement(name="arrtime") var arrtime : Int? // 남은 시간

){
    constructor() : this(null,null,null)

    val mytime : String
        get(){
            val converttime = arrtime?.div(60)

            return converttime.toString()
        }
}





