package com.example.your_precioustime.activitylist_package.bus_activity

import android.annotation.SuppressLint
import android.content.Intent
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.mo_del.StationItem
import com.example.your_precioustime.databinding.BusStationSearchitemLayoutBinding

class Bus_Station_Search_Adapter : RecyclerView.Adapter<Bus_Station_Search_Adapter.MyViewHolder>() {
    private var stationItem: List<StationItem>? = null

    inner class MyViewHolder(val binding: BusStationSearchitemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //dataBinding
        fun bind(stationItem: StationItem) {
            binding.stationitem = stationItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = BusStationSearchitemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        stationItem?.get(position)?.let { holder.bind(it) }

        val stationName = stationItem?.get(position)?.nodenm.toString()
        val stationnodenode = stationItem?.get(position)?.nodeno.toString()
        val stationNodeNumber = stationItem?.get(position)?.nodeid.toString()

//        holder.binding.StationNameTextView.setOnClickListener {
//            Toast.makeText(holder.itemView.context,"test",Toast.LENGTH_SHORT).show()
//        }

        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, Bus_StationInfo_Activity::class.java)
            intent.putExtra("stationName", stationName)
            intent.putExtra("stationnodenode", stationnodenode)
            intent.putExtra("stationNodeNumber", stationNodeNumber)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return stationItem?.size!!
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<StationItem>) {
        stationItem = list
        notifyDataSetChanged()
    }
}


