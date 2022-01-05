package com.example.your_precioustime.SecondActivity.SubwayFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.Model.SubwayItem
import com.example.your_precioustime.Model.SubwayModel.RealtimeArrival
import com.example.your_precioustime.Model.SubwayModel.SubwayModel
import com.example.your_precioustime.R
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.Url.Companion.SEOUL_SUBWAY_MAIN_URL
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.databinding.SubwayFragmentBinding
import com.example.your_precioustime.databinding.SubwayItemBinding
import retrofit2.Call
import retrofit2.Response

class SubwayFragment : Fragment(R.layout.subway_fragment) {

    private var subwayFragment: SubwayFragmentBinding? = null
    private val binding get() = subwayFragment!!

    private var fuckbinding: SubwayItemBinding? = null
    private val realFuckbinding get() = fuckbinding!!

    private var retrofitInterface = Retrofit_Client.getJsonClienet(SEOUL_SUBWAY_MAIN_URL)
        .create(Retrofit_InterFace::class.java)

    lateinit var subwayAdapter: SubwayAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subwayFragment = SubwayFragmentBinding.bind(view)

        binding.clickhere.setOnClickListener {
            val searchtext = binding.SearchEditText.text.toString()
            getsubwayCall(searchtext)
        }

//        setRecyclerView()


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
                val body = response.body()
                var subwaymodel = mutableListOf<SubwayItem>()
                val resultsubwaymodel = mutableListOf<SubwayItem>()


                body?.let {
                    val hello = body.realtimeArrivalList!!

                    for (i in hello.indices) {
                        val firstsubwayId = hello.get(i).subwayId!!

                        val trainLineNm = hello.get(i).trainLineNm
                        val bstatnNm = hello.get(i).bstatnNm
                        val arvlMsg2 = hello.get(i).arvlMsg2

                        subwaymodel.add(
                            SubwayItem(firstsubwayId, trainLineNm, bstatnNm, arvlMsg2)
                        )

                    }

                    Log.d(TAG, "onResponse: $subwaymodel")

                    for( i in subwaymodel.indices){
                        when(subwaymodel[i].subwayId){
                            "1001"->{
                                subwaymodel[i].subwayId="1"
                            }
                            "1002"->{
                                subwaymodel[i].subwayId="2"
                            }

                            "1003"->{
                                subwaymodel[i].subwayId="3"
                            }

                            "1004"->{
                                subwaymodel[i].subwayId="4"
                            }

                            "1005"->{
                                subwaymodel[i].subwayId="5"
                            }
                            "1006"->{
                                subwaymodel[i].subwayId="6"
                            }

                            "1007"->{
                                subwaymodel[i].subwayId="7"
                            }
                            "1008"->{
                                subwaymodel[i].subwayId="8"
                            }

                            "1009"->{
                                subwaymodel[i].subwayId="9"
                            }

                            "1063"->{
                                subwaymodel[i].subwayId="경의"
                            }

                            "1065"->{
                                subwaymodel[i].subwayId="공항"
                            }

                            "1067"->{
                                subwaymodel[i].subwayId="경춘"
                            }


                            "1075"->{
                                subwaymodel[i].subwayId="수인"
                            }


                            "1077"->{
                                subwaymodel[i].subwayId="신"
                            }

                            "1091"->{
                                subwaymodel[i].subwayId="자기"
                            }

                            "1092"->{
                                subwaymodel[i].subwayId="우이"
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
}