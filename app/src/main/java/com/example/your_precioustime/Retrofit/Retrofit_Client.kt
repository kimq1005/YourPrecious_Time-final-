package com.example.your_precioustime.Retrofit

import com.example.your_precioustime.Model.OdsayModel.OdasyModel
import com.example.your_precioustime.Url
import com.example.your_precioustime.Url.Companion.BUS_API_KEY
import com.google.gson.Gson
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.create

//retrofit client 싱글톤 생성
object Retrofit_Client {

    //XML변환 Client
    fun getClient(baseurl: String): Retrofit {

        val client = OkHttpClient.Builder()

        val baseInterceptor: Interceptor = (object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {

                val realRequest = chain.request()

                val goaddurl = realRequest
                    .url()
                    .newBuilder()
                    .addQueryParameter("serviceKey", BUS_API_KEY)
                    .build()

                val lastRequest = realRequest.newBuilder()
                    .url(goaddurl)
                    .build()

                return chain.proceed(lastRequest)
            }

        })

        client.addInterceptor(baseInterceptor)

        val retrofitClient = Retrofit.Builder()
            .baseUrl(baseurl)
            .client(client.build())
            .addConverterFactory(
                TikXmlConverterFactory.create(
                    TikXml.Builder().exceptionOnUnreadXml(false).build()
                )
            )
            .build()

        return retrofitClient

    }

    //Json 변환 Client
    fun getJsonClienet(baseUrl: String): Retrofit {

        val retrofitclient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitclient
    }


}