package com.example.your_precioustime.activitylist_package.bus_activity

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.App
import com.example.your_precioustime.DB.BUSStationNameDataBase
import com.example.your_precioustime.mo_del.Item
import com.example.your_precioustime.ObjectManager.citycodeCallObject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.SecondActivity.DB.BUSStationNameEntity
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.databinding.BusitemLayoutBinding


@SuppressLint("StaticFieldLeak")
class BusStationInfo_Adpater : RecyclerView.Adapter<BusStationInfo_Adpater.MyViewHolder>() {

    private var item: List<Item>? = null


    class MyViewHolder(val binding: BusitemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        //data binding
        fun bind(item: Item) {
            val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")
            val cityname = citycodeCallObject.citycodeCallObject.returncitynamecode(citycode)
            binding.BusCityname.text = cityname

            binding.item = item

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = BusitemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        item?.get(position)?.let {
            holder.bind(it)
        }


    }


    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Item>) {
        item = list
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return item?.size!!
    }


}