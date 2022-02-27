package com.example.your_precioustime.activitylist_package.favorite_activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.activitylist_package.favorite_activity.subwayfavroite_deepinfo.Subway_FravoriteDeepInfo_Activity
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.SecondActivity.DB.OnSubwayListDeleteInterFace
import com.example.your_precioustime.SecondActivity.DB.SubwayNameEntity
import com.example.your_precioustime.databinding.SubwayfavoritelistItemBinding


class SubwayFavorite_Adpater(var onSubwayListDeleteInterFace: OnSubwayListDeleteInterFace) :
    RecyclerView.Adapter<SubwayFavorite_Adpater.SubwayFVHolder>() {
    lateinit var subwaynameEntity: List<SubwayNameEntity>

    inner class SubwayFVHolder(val binding: SubwayfavoritelistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subwaynameEntity: SubwayNameEntity) {
            binding.SubwayFavoriteNameTextView.text = subwaynameEntity.subwayName
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
        val subway_delete_List = subwaynameEntity[position]
        val subwayname = subwaynameEntity[position].subwayName

        holder.bind(subwaynameEntity[position])
        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, Subway_FravoriteDeepInfo_Activity::class.java)
            intent.putExtra("subwayname", subwayname)
            holder.itemView.context.startActivity(intent)

        }

        holder.binding.stardeletebtn.setOnClickListener {
            onSubwayListDeleteInterFace.onDeleteSubwayList(subway_delete_List)
            Myobject.myobject.deletestation(holder.itemView)
        }

    }

    override fun getItemCount(): Int {
        return subwaynameEntity.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<SubwayNameEntity>) {
        subwaynameEntity = list
        notifyDataSetChanged()
    }
}