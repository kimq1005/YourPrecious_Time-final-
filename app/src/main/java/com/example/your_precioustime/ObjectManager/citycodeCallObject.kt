package com.example.your_precioustime.ObjectManager

import android.content.Context
import android.content.SharedPreferences

class citycodeCallObject {

    companion object{
       val citycodeCallObject = citycodeCallObject()
    }

    //도시명(이름)을 입력시 도시코드 반환 함수
    fun citycode(cityname:String): String {
        when(cityname){
            "수원"->{
                return "31010"
            }

            "성남"->{
                return "31020"
            }

            "안양"->{
                return "31040"
            }

            "부천"->{
                return "31050"
            }

            "광명"->{
                return "31060"
            }

            "평택"->{
                return "31070"
            }

            "동두천"->{
                return "31080"
            }

            "안산"->{
                return "31090"
            }

            "고양"->{
                return "31100"
            }
            "과천"->{
                return "31110"
            }

            "구리"->{
                return "31120"
            }

            "남양주"->{
                return "31130"
            }

            "오산"->{
                return "31140"
            }

            "시흥"->{
                return "31150"
            }

            "군포"->{
                return "31160"
            }

            "의왕"->{
                return "31170"
            }

            "하남"->{
                return "31180"
            }

            "용인"->{
                return "31190"
            }

            "파주"->{
                return "31200"
            }

            "이천"->{
                return "31210"
            }

            "안성"->{
                return "31220"
            }

            "김포"->{
                return "31230"
            }

            "화성"->{
                return "31240"
            }


            "광주"->{
                return "31250"
            }

            "양주"->{
                return "31260"
            }

            "포천"->{
                return "31270"
            }

            "여주"->{
                return "31320"
            }

            "연천"->{
                return "31350"
            }

            "가평"->{
                return "31370"
            }

            "양평"->{
                return "31380"
            }


        }
        return "31010"
    }

    //도시코드 입력시 도시명(이름) 반환 함수
    fun returncitynamecode(cityname:String): String {
        when(cityname){
            "31010"->{
                return "수원"
            }

            "31020"->{
                return "성남"
            }

            "31040"->{
                return "안양"
            }

            "31050"->{
                return "부천"
            }

            "31060"->{
                return "광명"
            }

            "31070"->{
                return "평택"
            }

            "31080"->{
                return "동두천"
            }

            "31090"->{
                return "안산"
            }

            "31100"->{
                return "고양"
            }
            "31110"->{
                return "과천"
            }

            "31120"->{
                return "구리"
            }

            "31130"->{
                return "남양주"
            }

            "31140"->{
                return "오산"
            }

            "31150"->{
                return "시흥"
            }

            "31160"->{
                return "군포"
            }

            "31170"->{
                return "의왕"
            }

            "31180"->{
                return "하남"
            }

            "31190"->{
                return "용인"
            }

            "31200"->{
                return "파주"
            }

            "31210"->{
                return "이천"
            }

            "31220"->{
                return "안성"
            }

            "31230"->{
                return "김포"
            }

            "31240"->{
                return "화성"
            }


            "31250"->{
                return "광주"
            }

            "31260"->{
                return "양주"
            }

            "31270"->{
                return "포천"
            }

            "31320"->{
                return "여주"
            }

            "31350"->{
                return "연천"
            }

            "31370"->{
                return "가평"
            }

            "31380"->{
                return "양평"
            }


        }
        return "Error"
    }


}