package com.example.your_precioustime.activitylist_package.favorite_activity.busfavroite_deepinfo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.mo_del.Item
import com.example.your_precioustime.ObjectManager.citycodeCallObject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.databinding.BusFavoritelistItemBinding
import com.example.your_precioustime.databinding.BusfavoriteItemBinding
import com.example.your_precioustime.databinding.BusitemLayoutBinding

@SuppressLint("StaticFieldLeak")
class Bus_DeepFavoriteAdapter : RecyclerView.Adapter<Bus_DeepFavoriteAdapter.myViewHolder>() {

    lateinit var item : List<Item>

    inner class myViewHolder(var binding: BusfavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")
            val cityname = citycodeCallObject.citycodeCallObject.returncitynamecode(citycode)
            binding.BusCityname.text = cityname

            binding.item = item

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = BusfavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int {
       return item.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitlist(list: List<Item>){
        item = list
        notifyDataSetChanged()
    }


}