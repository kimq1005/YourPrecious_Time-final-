package com.example.your_precioustime.ActivityListPackage.SubwayActivity

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.Model.SubwayItem
import com.example.your_precioustime.Model.SubwayModel.SubwayModel
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.R
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.SecondActivity.DB.SubwayDataBase
import com.example.your_precioustime.SecondActivity.DB.SubwayNameEntity
import com.example.your_precioustime.Url.Companion.SEOUL_SUBWAY_MAIN_URL
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.databinding.SubwayFragmentBinding
import kotlinx.android.synthetic.main.subway_fragment.*
import retrofit2.Call
import retrofit2.Response


@SuppressLint("StaticFieldLeak")
class SubwayFragment : AppCompatActivity() {

    private var subwayFragment: SubwayFragmentBinding? = null
    private val binding get() = subwayFragment!!

    private var retrofitInterface =
        Retrofit_Client.getJsonClienet(SEOUL_SUBWAY_MAIN_URL).create(Retrofit_InterFace::class.java)

    lateinit var subwayAdapter: SubwayAdapter
    lateinit var subwayDataBase: SubwayDataBase
    lateinit var subwayNameListEntity: List<SubwayNameEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subwayFragment = SubwayFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subwayDataBase = SubwayDataBase.getinstance(this)!!

        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )

        binding.backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }


        binding.clickhere.setOnClickListener {

            testgetAll()
            val searchtext = binding.SearchEditText.text.toString()
            binding.subtitleTextView.text = searchtext
            subwayfavroiteAddImageView.visibility = View.VISIBLE
            subtitleTextView.visibility = View.VISIBLE
            getsubwayCall(searchtext)


        }

        binding.subwayfavroiteAddImageView.setOnClickListener {
            val subwayname = binding.subtitleTextView.text.toString()

            val mylist = SubwayNameEntity(
                id = null,
                subwayname
            )

            subwayinsert(mylist)
        }


    }


    private fun setRecyclerView(subwayItem: List<SubwayItem>) {
        subwayAdapter = SubwayAdapter()


        binding.subwayRecyclerView.apply {
            adapter = subwayAdapter
            layoutManager = LinearLayoutManager(context)
            subwayAdapter.submitList(subwayItem)
        }


    }

    private fun getsubwayCall(statNm: String) {

        val call = retrofitInterface.SUBWAYGET(
            statnNm = statNm
        )

        call.enqueue(object : retrofit2.Callback<SubwayModel> {
            override fun onResponse(call: Call<SubwayModel>, response: Response<SubwayModel>) {


                Log.d(TAG, "onResponse: ${response.body()}")


                val body = response.body()
                val subwaymodel = mutableListOf<SubwayItem>()

                body?.let {
                    val hello = body.realtimeArrivalList

                    if (hello != null) {
                        for (i in hello.indices) {
                            val firstsubwayId = hello.get(i).subwayId!!
                            val trainLineNm = hello.get(i).trainLineNm
                            val bstatnNm = hello.get(i).bstatnNm
                            val arvlMsg2 = hello.get(i).arvlMsg2

                            subwaymodel.add(
                                SubwayItem(firstsubwayId, trainLineNm, bstatnNm, arvlMsg2)
                            )

                        }

                    } else {
                        Myobject.myobject.retrystation(binding.subwayFragmentActivity)
                        binding.subtitleTextView.visibility = View.INVISIBLE
                        binding.subwayfavroiteAddImageView.visibility = View.INVISIBLE
                    }


                    Log.d(TAG, "onResponse: $subwaymodel")

                    for (i in subwaymodel.indices) {
                        when (subwaymodel[i].subwayId) {
                            "1001" -> {
                                subwaymodel[i].subwayId = "1"
                            }
                            "1002" -> {
                                subwaymodel[i].subwayId = "2"
                            }

                            "1003" -> {
                                subwaymodel[i].subwayId = "3"
                            }

                            "1004" -> {
                                subwaymodel[i].subwayId = "4"
                            }

                            "1005" -> {
                                subwaymodel[i].subwayId = "5"
                            }
                            "1006" -> {
                                subwaymodel[i].subwayId = "6"
                            }

                            "1007" -> {
                                subwaymodel[i].subwayId = "7"
                            }
                            "1008" -> {
                                subwaymodel[i].subwayId = "8"
                            }

                            "1009" -> {
                                subwaymodel[i].subwayId = "9"
                            }

                            "1063" -> {
                                subwaymodel[i].subwayId = "경"
                            }

                            "1065" -> {
                                subwaymodel[i].subwayId = "공"
                            }

                            "1067" -> {
                                subwaymodel[i].subwayId = "경춘"
                            }


                            "1075" -> {
                                subwaymodel[i].subwayId = "수"
                            }


                            "1077" -> {
                                subwaymodel[i].subwayId = "신"
                            }

                            "1091" -> {
                                subwaymodel[i].subwayId = "자"
                            }

                            "1092" -> {
                                subwaymodel[i].subwayId = "우"
                            }

                        }
                    }

                    setRecyclerView(subwaymodel)
                }


            }

            override fun onFailure(call: Call<SubwayModel>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })

    }


    private fun subwayinsert(subwayNameEntity: SubwayNameEntity) {
        val insertTask = (object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                subwayNameListEntity = subwayDataBase.subwayNameDAO().subwayGetAll()

                Log.d(TAG, "지하철이름저장 로그: $subwayNameListEntity")

                val subwaynameList = mutableListOf<String>()
                for (i in subwayNameListEntity.indices) {
                    val subwayname = subwayNameListEntity.get(i).subwayName
                    subwaynameList.add(subwayname)
                }

                if (binding.subtitleTextView.text !in subwaynameList) {
                    subwayDataBase.subwayNameDAO().subwayInsert(subwayNameEntity)
                }

            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)

                val stationnameList = mutableListOf<String>()

                for (i in subwayNameListEntity.indices) {
                    val stationname = subwayNameListEntity.get(i).subwayName
                    stationnameList.add(stationname)
                }

                if (binding.subtitleTextView.text in stationnameList) {
                    Myobject.myobject.alreadyFavroiteSnackBar(subwayFragment_Activity)
                } else {
                    Myobject.myobject.FavroiteSnackBar(subwayFragment_Activity)
                    binding.subwayfavroiteAddImageView.setImageResource(R.drawable.shinigstar)
                }


            }

        }).execute()
    }

    private fun testgetAll() {
        val getAllTask = (object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                subwayNameListEntity = subwayDataBase.subwayNameDAO().subwayGetAll()
                Log.d(TAG, "지하철이름저장 로그: $subwayNameListEntity")
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                val stationnameList = mutableListOf<String>()

                for (i in subwayNameListEntity.indices) {
                    val stationname = subwayNameListEntity.get(i).subwayName
                    stationnameList.add(stationname)
                }

                if (binding.subtitleTextView.text in stationnameList) {
                    binding.subwayfavroiteAddImageView.setImageResource(R.drawable.shinigstar)
                } else {
                    binding.subwayfavroiteAddImageView.setImageResource(R.drawable.star)
                }

            }

        }).execute()
    }
}