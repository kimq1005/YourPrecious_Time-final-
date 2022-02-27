package com.example.your_precioustime.activitylist_package.notice_activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.Model.NoticeModel.NoticeItem
import com.example.your_precioustime.databinding.NoticelayoutItemBinding

class NoticeAdapter : RecyclerView.Adapter<NoticeAdapter.myViewHolder>() {

    lateinit var noticeItem: List<NoticeItem>

    inner class myViewHolder(val binding: NoticelayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noticeItem: NoticeItem) {
            binding.noticeTitleTextView.text = noticeItem.noticeName
            binding.noticeTimeTextView.text = noticeItem.noticeTime
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = NoticelayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
       holder.bind(noticeItem[position])
    }

    override fun getItemCount(): Int {
        return noticeItem.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list:List<NoticeItem>){
        noticeItem = list
        notifyDataSetChanged()
    }
}