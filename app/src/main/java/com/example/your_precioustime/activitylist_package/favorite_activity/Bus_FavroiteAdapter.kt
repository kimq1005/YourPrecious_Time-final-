package com.example.your_precioustime.activitylist_package.favorite_activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.SecondActivity.DB.OnBusFavroiteListDeleteInterFace
import com.example.your_precioustime.SecondActivity.DB.OnDeleteInterFace
import com.example.your_precioustime.activitylist_package.favorite_activity.busfavroite_deepinfo.Bus_FavroiteDeepInfo_Activity
import com.example.your_precioustime.databinding.BusFavoritelistItemBinding
import com.example.your_precioustime.roompackage.bus_room.BusFavoriteEntity
import kotlinx.android.synthetic.main.subwayfavoritelist_item.view.*


//실제로쓸 아답터 ViewModel , DataBinding
class Bus_FavroiteAdapter(var onBusFavroiteListDeleteInterFace: OnBusFavroiteListDeleteInterFace) : RecyclerView.Adapter<Bus_FavroiteAdapter.BusFavoriteViewHolder>() {
    private var busfavoriteEntity = emptyList<BusFavoriteEntity>()


    inner class BusFavoriteViewHolder(val binding: BusFavoritelistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(busFavoriteEntity: BusFavoriteEntity) {
            binding.FavoriteNameTextView.text = busFavoriteEntity.stationName
            binding.FavoriteNodeIDTextView.text = busFavoriteEntity.stationnodenode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusFavoriteViewHolder {
        val view =
            BusFavoritelistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BusFavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusFavoriteViewHolder, position: Int) {
        holder.bind(busfavoriteEntity[position])

        val busfavoriteModelList = busfavoriteEntity[position]


        val favoriteStationName = busfavoriteModelList.stationName
        val favoritenodenum = busfavoriteModelList.stationNodeNumber
        val citycode = busfavoriteModelList.citycode

        holder.itemView.setOnClickListener {
            citycodeSaveClass.citycodeSaveClass.Savecitycode("favroitebuscitycode",citycode.toString())
            val intent = Intent(holder.itemView.context, Bus_FavroiteDeepInfo_Activity::class.java).apply {
                putExtra("citycode",citycode)
                putExtra("favoriteStationName", favoriteStationName)
                putExtra("favoritenodenum", favoritenodenum)
            }

            holder.itemView.context.startActivity(intent)
        }


        holder.itemView.stardeletebtn.setOnClickListener {
            onBusFavroiteListDeleteInterFace.onDeleteBusFavoriteList(busfavoriteModelList)
            Myobject.myobject.deletestation(holder.itemView)
        }
    }

    override fun getItemCount(): Int {
        return busfavoriteEntity.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitlist(list: List<BusFavoriteEntity>) {
        busfavoriteEntity = list
        notifyDataSetChanged()
    }
}