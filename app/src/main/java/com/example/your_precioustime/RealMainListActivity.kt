package com.example.your_precioustime

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.your_precioustime.activitylist_package.notice_activity.NoticeActivity
import com.example.your_precioustime.ObjectManager.citycodeCallObject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.activitylist_package.bus_activity.Bus_Activity
import com.example.your_precioustime.activitylist_package.favorite_activity.FavroiteActivity
import com.example.your_precioustime.activitylist_package.subway_activity.Subway_Activity
import com.example.your_precioustime.databinding.ActivityRealMainListBinding
import com.google.android.material.snackbar.Snackbar

class RealMainListActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var realMainListBinding: ActivityRealMainListBinding? = null
    private val binding get() = realMainListBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realMainListBinding = ActivityRealMainListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.mySpinner
        spinner.onItemSelectedListener = this
        setSpinner(spinner)

        binding.busView.setOnClickListener {
            val mycitycode: String =
                citycodeCallObject.citycodeCallObject.citycode(binding.citynameTextView.text.toString())
            citycodeSaveClass.citycodeSaveClass.Savecitycode("citycode", mycitycode)

            val intent = Intent(this, Bus_Activity::class.java)
            startActivity(intent)
        }

        binding.SubwayView.setOnClickListener {
            val intent = Intent(this, Subway_Activity::class.java)
            startActivity(intent)
        }

        binding.FavroiteView.setOnClickListener {
            val intent = Intent(this, FavroiteActivity::class.java)
            startActivity(intent)
        }


        binding.noticeView.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)
        }

        binding.helpView.setOnClickListener {
            val snackbar = Snackbar.make(binding.helpView, "준비중입니다.", Snackbar.LENGTH_LONG)
            snackbar.show()
        }

        binding.emailInquiriesLinearlayout.setOnClickListener {
            shareEmail()
        }

    }


    //이메일 공유함수
    @SuppressLint("QueryPermissionsNeeded")
    private fun shareEmail() {
        val shareintent = Intent().apply {
            action = Intent.ACTION_SENDTO
            type = "text/plain"
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("tpwnd103502@naver.com"))

        }

        startActivity(Intent.createChooser(shareintent, null))



    }


    //지역선택 스피너 함수
    private fun setSpinner(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            this,
            R.array.cityname,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner.adapter = adapter

        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        binding.citynameTextView.text = binding.mySpinner.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}