package com.example.your_precioustime.SecondActivity.subwayfavorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.SecondActivity.DB.OnDeleteInterFace
import com.example.your_precioustime.SecondActivity.DB.OnSubwayListDeleteInterFace
import com.example.your_precioustime.SecondActivity.DB.SubwayDB.SubwayNameEntity
import com.example.your_precioustime.databinding.SubwayfavoritelistItemBinding

class SubwayFavorite_Adpater(var onSubwayListDeleteInterFace: OnSubwayListDeleteInterFace):RecyclerView.Adapter<SubwayFavorite_Adpater.SubwayFVHolder>() {
    lateinit var subwaynameEntity: List<SubwayNameEntity>

    inner class SubwayFVHolder(val binding: SubwayfavoritelistItemBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bind(subwaynameEntity: SubwayNameEntity){
            binding.SubwayFavoriteNameTextView.text = subwaynameEntity.subwayName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubwayFVHolder {
        val view = SubwayfavoritelistItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return SubwayFVHolder(view)
    }

    override fun onBindViewHolder(holder: SubwayFVHolder, position: Int) {
        val subway_delete_List = subwaynameEntity[position]

        holder.bind(subwaynameEntity[position])
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context,"$position 번째 아이템",Toast.LENGTH_SHORT).show()
        }

        holder.binding.stardeletebtn.setOnClickListener {
            onSubwayListDeleteInterFace.onDeleteSubwayList(subway_delete_List)
        }

    }

    override fun getItemCount(): Int {
        return subwaynameEntity.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list:List<SubwayNameEntity>){
        subwaynameEntity = list
        notifyDataSetChanged()
    }
}