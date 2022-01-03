package com.example.your_precioustime.SecondActivity.Busfragment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.Model.StationItem
import com.example.your_precioustime.SecondActivity.FavoriteFragment.DeepStationInfoActivity
import com.example.your_precioustime.databinding.BusStationSearchitemLayoutBinding

class Bus_Station_Search_Adapter:RecyclerView.Adapter<Bus_Station_Search_Adapter.MyViewHolder>() {
    private var stationItem:List<StationItem>? = null

    inner class MyViewHolder(val binding: BusStationSearchitemLayoutBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(stationItem: StationItem){
            binding.StationNameTextView.text = stationItem.nodenm
            binding.StationNodeIDTextView.text = stationItem.nodeno

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = BusStationSearchitemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        stationItem?.get(position)?.let { holder.bind(it) }

        val stationName = stationItem?.get(position)?.nodenm.toString()
        val stationnodenode = stationItem?.get(position)?.nodeno.toString()
        val stationNodeNumber = stationItem?.get(position)?.nodeid.toString()


        holder.itemView.setOnClickListener{

            val intent = Intent(holder.itemView.context, DeepStationInfoActivity::class.java)
            intent.putExtra("stationName" , stationName)
            intent.putExtra("stationnodenode", stationnodenode)
            intent.putExtra("stationNodeNumber", stationNodeNumber)
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return stationItem?.size!!
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list:List<StationItem>){
        stationItem= list
        notifyDataSetChanged()
    }
}


