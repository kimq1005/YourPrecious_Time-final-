package com.example.your_precioustime.activitylist_package.favorite_activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.activitylist_package.favorite_activity.subwayfavroite_deepinfo.Subway_FravoriteDeepInfo_Activity
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.SecondActivity.DB.OnSubwayFavoriteListDeleteInterFace
import com.example.your_precioustime.databinding.SubwayfavoritelistItemBinding
import com.example.your_precioustime.roompackage.subway_room.SubwayFavoriteEntity


//실제로 쓸 아답터
class Subway_FavoriteAdpater(var onSubwayListDeleteInterFace: OnSubwayFavoriteListDeleteInterFace) :
    RecyclerView.Adapter<Subway_FavoriteAdpater.SubwayFVHolder>() {
    private var subwayFavoriteEntity = emptyList<SubwayFavoriteEntity>()

    inner class SubwayFVHolder(val binding: SubwayfavoritelistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subwayFavoriteEntity: SubwayFavoriteEntity) {
            binding.SubwayFavoriteNameTextView.text = subwayFavoriteEntity.subwayName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubwayFVHolder {
        val view = SubwayfavoritelistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SubwayFVHolder(view)
    }

    override fun onBindViewHolder(holder: SubwayFVHolder, position: Int) {
        val subway_delete_List = subwayFavoriteEntity[position]
        val subwayname = subwayFavoriteEntity[position].subwayName

        holder.bind(subwayFavoriteEntity[position])
        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, Subway_FravoriteDeepInfo_Activity::class.java)
            intent.putExtra("subwayname", subwayname)
            holder.itemView.context.startActivity(intent)

        }

        holder.binding.stardeletebtn.setOnClickListener {
            onSubwayListDeleteInterFace.onDeleteSubwayFavoriteList(subway_delete_List)
            Myobject.myobject.deletestation(holder.itemView)
        }

    }

    override fun getItemCount(): Int {
        return subwayFavoriteEntity.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<SubwayFavoriteEntity>) {
        subwayFavoriteEntity = list
        notifyDataSetChanged()
    }
}