package com.example.your_precioustime.activitylist_package.favorite_activity

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.App
import com.example.your_precioustime.DB.BusFavroiteDataBase
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.SecondActivity.DB.OnDeleteInterFace
import com.example.your_precioustime.SecondActivity.DB.OnSubwayListDeleteInterFace
import com.example.your_precioustime.SecondActivity.DB.SubwayDataBase
import com.example.your_precioustime.SecondActivity.DB.SubwayNameEntity
import com.example.your_precioustime.SecondActivity.DB.SubwayDB.TestFavoriteModel
import com.example.your_precioustime.Util
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.databinding.ActivityFavroiteBinding

@SuppressLint("StaticFieldLeak")
class FavroiteActivity : AppCompatActivity(), OnDeleteInterFace, OnSubwayListDeleteInterFace {

    private var favroiteBinding: ActivityFavroiteBinding? = null
    private val binding get() = favroiteBinding!!

    lateinit var busFavoriteDB: BusFavroiteDataBase
    lateinit var busfavoriteEntity: List<TestFavoriteModel>

    lateinit var favroiteAdapterBus: BusFavorite_Adapter

    lateinit var subwayDataBase: SubwayDataBase
    lateinit var subwayNameEntity: List<SubwayNameEntity>
    lateinit var subwayfavoriteAdpater: SubwayFavorite_Adpater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favroiteBinding = ActivityFavroiteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        busFavoriteDB = BusFavroiteDataBase.getinstance(App.instance)!!
        subwayDataBase = SubwayDataBase.getinstance(App.instance)!!

        binding.backwowbtn.setOnClickListener {
            onBackPressed()
            finish()
        }


        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )

        getAll()
        subwaygetAll()

    }

    private fun subwaygetAll() {
        val getTask = (object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                subwayNameEntity = subwayDataBase.subwayNameDAO().subwayGetAll()
                Log.d(TAG, "지하철 DB 확인 : $subwayNameEntity ")
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                subwaysetRecyclerView()

            }
        }).execute()
    }

    private fun subwaysetRecyclerView() = with(binding) {

        subwayfavoriteAdpater = SubwayFavorite_Adpater(this@FavroiteActivity)

        SubwayFvRecylerView.apply {
            adapter = subwayfavoriteAdpater
            layoutManager = LinearLayoutManager(context)
            subwayfavoriteAdpater.submitList(subwayNameEntity)

        }


    }


    private fun getAll() {
        val getAllTask = (@SuppressLint("StaticFieldLeak")
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                busfavoriteEntity = busFavoriteDB.busFavoriteDAO().busFavoriteGetAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)

                Log.d(Util.TAG, "버스 태그 확인: $busfavoriteEntity")
                setBusFavroiteRecyclerView()

            }
        }).execute()
    }

    private fun setBusFavroiteRecyclerView() = with(binding) {

        favroiteAdapterBus = BusFavorite_Adapter(this@FavroiteActivity)

        FavoriteRecyclerView.apply {
            adapter = favroiteAdapterBus
            layoutManager = LinearLayoutManager(context)
            favroiteAdapterBus.submitList(busfavoriteEntity)

        }

    }


    private fun onSubwayDelete(subwayNameEntity: SubwayNameEntity) {
        val deleteTask = (object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                subwayDataBase.subwayNameDAO().subwayDelete(subwayNameEntity)
            }


            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                subwaygetAll()
            }

        }).execute()
    }


    private fun ondeleteList(testFavoriteModel: TestFavoriteModel) {

        val deleteTask = (
                object : AsyncTask<Unit, Unit, Unit>() {
                    override fun doInBackground(vararg params: Unit?) {
                        busFavoriteDB.busFavoriteDAO().busFavoriteDelete(testFavoriteModel)
                    }

                    override fun onPostExecute(result: Unit?) {
                        super.onPostExecute(result)
                        getAll()
                    }
                }).execute()
    }


    override fun onDeleteFavroitelist(testFavoriteModel: TestFavoriteModel) {
        ondeleteList(testFavoriteModel)
    }

    override fun onDeleteSubwayList(subwayNameEntity: SubwayNameEntity) {
        onSubwayDelete(subwayNameEntity)
    }


}