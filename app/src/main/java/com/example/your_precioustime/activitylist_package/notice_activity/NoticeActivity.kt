package com.example.your_precioustime.activitylist_package.notice_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.Model.NoticeModel.NoticeItem
import com.example.your_precioustime.databinding.ActivityNoticeBinding

class NoticeActivity : AppCompatActivity() {
    private var noticeActivityBinding: ActivityNoticeBinding?=null
    private val binding get() = noticeActivityBinding!!

    lateinit var noticeAdapter: NoticeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noticeActivityBinding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backwowbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        setRecylcerView()
    }


    private fun setRecylcerView(){

        noticeAdapter = NoticeAdapter()
        binding.noticeRecyclerView.apply {
            adapter = noticeAdapter
            layoutManager = LinearLayoutManager(context)
            noticeAdapter.submitList(
                listOf(
                NoticeItem(
                    "나는 전설이다가 출시되었습니다!","2022.00.00"
                )
            ))
        }

    }
}