package com.example.your_precioustime.activitylist_package.subway_activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.mo_del.SubwayItem
import com.example.your_precioustime.R
import com.example.your_precioustime.databinding.SubwayItemBinding
import com.example.your_precioustime.ObjectManager.Myobject

@SuppressLint("StaticFieldLeak")
class SubwayAdapter : RecyclerView.Adapter<SubwayAdapter.MyViewHolder>() {

    lateinit var subwayItem: List<SubwayItem>



    inner class MyViewHolder(val binding: SubwayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subwayItem: SubwayItem) {
            binding.subwayItem = subwayItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = SubwayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subwayItem[position])
        when (subwayItem[position].subwayId) {

            "1" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line1_background)

            }

            "2" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line2_background)

            }

            "3" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line3_background)

            }

            "4" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line4_background)

            }

            "5" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line5_background)

            }

            "6" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line6_background)

            }

            "7" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line7_background)

            }

            "8" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line8_background)

            }

            "9" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line9_background)

            }


            "경" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line_gyunge_background)

            }

            "공" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line_gonghang_background)

            }

            "경춘" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line_gyungchun_background)

            }


            "수" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line_bundang_background)

            }


            "신" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line_sinbundang_background)

            }

            "자" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line_jagibusang_background)

            }

            "우" -> {
                holder.binding.subwayitemSubwayId.setBackgroundResource(R.drawable.line_ui_background)

            }
        }

    }


    override fun getItemCount(): Int {
        return subwayItem.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<SubwayItem>) {
        subwayItem = list
        notifyDataSetChanged()
    }


}