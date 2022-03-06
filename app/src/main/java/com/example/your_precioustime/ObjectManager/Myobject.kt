package com.example.your_precioustime.ObjectManager

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.view.View
import com.example.your_precioustime.activitylist_package.bus_activity.Bus_Activity
import com.example.your_precioustime.activitylist_package.favorite_activity.FavroiteActivity
import com.example.your_precioustime.activitylist_package.subway_activity.Subway_Activity
import com.google.android.material.snackbar.Snackbar

class Myobject {

    companion object {
        val myobject = Myobject()
    }


    //토글버튼 함수
    fun ToggleSet(
        context: Context,
        floatbtn: View,
        fvfloatBtn: View,
        subwayfloatbtn: View,
        busfloatbtn: View
    ) {
        var myBolean = false
        floatbtn.setOnClickListener {
            if (myBolean == false) {
                ObjectAnimator.ofFloat(fvfloatBtn, "translationY", -200f).apply { start() }
                ObjectAnimator.ofFloat(subwayfloatbtn, "translationY", -380f).apply { start() }
                ObjectAnimator.ofFloat(busfloatbtn, "translationY", -560f).apply { start() }
                myBolean = true

            } else {
                ObjectAnimator.ofFloat(fvfloatBtn, "translationY", -0f).apply { start() }
                ObjectAnimator.ofFloat(subwayfloatbtn, "translationY", 0f).apply { start() }
                ObjectAnimator.ofFloat(busfloatbtn, "translationY", 0f).apply { start() }
                myBolean = false
            }
        }

        fvfloatBtn.setOnClickListener {
            val intent = Intent(context, FavroiteActivity::class.java)
            context.startActivity(intent)
        }

        subwayfloatbtn.setOnClickListener {
            val intent = Intent(context, Subway_Activity::class.java)
            context.startActivity(intent)
        }

        busfloatbtn.setOnClickListener {
            val intent = Intent(context, Bus_Activity::class.java)
            context.startActivity(intent)
        }


    }

    //지하철 남은역 반환 함수
    fun changeSubwayText(mytext: String): String {
        if (mytext.contains("[")) {
            val one = mytext.replace("[", "")
            val two = one.replace("]", "")
            val three = two.replace("번째", "")
            val four = three.replace("역", " ")
            val subString = four.substring(0..3)
            return subString


        }
        return mytext
    }

    //지하철역 방면 반환 함수
    fun changeSubwayResultText(mytext: String): String {
        if (mytext.contains("-")) {
            val one = mytext.replace(" ", "")
            val two = one.split("-")
            val three = two[1].replace("방면", " 방면")
            return three
        }
        else{
            return "Error"
        }


    }

    fun FavroiteSnackBar(view: View) {
        val snackbar = Snackbar.make(view, "즐겨찾기에 등록되었습니다!", Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    fun alreadyFavroiteSnackBar(view: View) {
        val snackbar = Snackbar.make(view, "즐겨찾기에 등록된 정류장(역)입니다!", Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    fun retrystation(view: View) {
        val snackbar = Snackbar.make(view, "정류장(역)이름을 재입력 해주세요", Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    fun deletestation(view:View){
        val snackbar = Snackbar.make(view, "즐겨찾기에서 삭제 되었습니다!",Snackbar.LENGTH_LONG)
        snackbar.show()
    }



}
