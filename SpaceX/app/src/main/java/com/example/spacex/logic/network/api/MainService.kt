package com.example.spacex.logic.network.api

import com.example.spacex.logic.model.LaunchItem
import com.example.spacex.logic.model.Launches
import com.example.spacex.logic.model.RocketItem
import com.example.spacex.logic.network.ServiceCreator
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


/**
 * @ClassName MainService
 * @Description include all api
 * @Author mailo
 * @Date 2021/4/27
 */
interface MainService {
    /*
    * all launch list
    * */
    @GET
    fun getLaunches(@Url url: String): Call<Launches>


    /*
    * one Launch
    * */
    @GET("launches/{flight_number}")
    fun getOneLaunch(@Path("flight_number") flightNumber: Int): Call<LaunchItem>

    /*
    * one rocket
    * */
    @GET("rockets/{rocket_id}")
    fun getOneRocket(@Path("rocket_id") rocketId: String): Call<RocketItem>


    companion object {

        /**
         * All Launches list
         */
        const val LAUNCHES_URL = "${ServiceCreator.BASE_URL}launches"


    }
}