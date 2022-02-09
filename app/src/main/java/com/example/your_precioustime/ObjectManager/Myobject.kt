package com.example.your_precioustime.ObjectManager

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.your_precioustime.ActivityListPackage.BusActivity.Bus_Activity
import com.example.your_precioustime.ActivityListPackage.FavoriteActivity.FavroiteActivity
import com.example.your_precioustime.ActivityListPackage.SubwayActivity.SubwayFragment
import com.example.your_precioustime.R
import com.google.android.material.snackbar.Snackbar

class Myobject {

    companion object {
        val myobject = Myobject()
    }


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
                ObjectAnimator.ofFloat(fvfloatBtn, "translationY", -150f).apply { start() }
                ObjectAnimator.ofFloat(subwayfloatbtn, "translationY", -300f).apply { start() }
                ObjectAnimator.ofFloat(busfloatbtn, "translationY", -450f).apply { start() }
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
            val intent = Intent(context, SubwayFragment::class.java)
            context.startActivity(intent)
        }

        busfloatbtn.setOnClickListener {
            val intent = Intent(context, Bus_Activity::class.java)
            context.startActivity(intent)
        }


    }


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


    fun changeSubwayResultText(mytext: String): String {
        if (mytext.contains("-")) {
            val one = mytext.replace(" ", "")
            val two = one.split("-")
            val three = two[1].replace("방면", " 방면")
            return three
        }


        return mytext
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


    fun refreshView(refreshview: View) {

    }


}
