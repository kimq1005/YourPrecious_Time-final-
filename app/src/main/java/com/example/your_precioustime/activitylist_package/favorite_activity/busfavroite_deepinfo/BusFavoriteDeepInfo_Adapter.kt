package com.example.your_precioustime.activitylist_package.favorite_activity.busfavroite_deepinfo

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.ObjectManager.citycodeCallObject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.activitylist_package.favorite_activity.Bus_FavroiteAdapter
import com.example.your_precioustime.databinding.BusitemLayoutBinding
import com.example.your_precioustime.mo_del.Item


//실제로 쓸 아답터
class BusFavoriteDeepInfo_Adapter :
    RecyclerView.Adapter<Bus_FavroiteAdapter.BusFavoriteViewHolder>() {
    lateinit var item: List<Item>


    inner class BusFavoriteDeepInfoViewHolder(val binding: BusitemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            val mytime = item.arrtime!!
            val second = mytime / 60
            val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode(
                "favroitebuscitycode",
                "favroitebuscitycode"
            )
            val cityname = citycodeCallObject.citycodeCallObject.returncitynamecode(citycode)


            binding.BusNumber.text = item.routeno
            binding.waitBusNumber.text = item.arrprevstationcnt.toString()
            binding.waitTime.text = second.toString()
            binding.BusCityname.text = cityname

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Bus_FavroiteAdapter.BusFavoriteViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: Bus_FavroiteAdapter.BusFavoriteViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}