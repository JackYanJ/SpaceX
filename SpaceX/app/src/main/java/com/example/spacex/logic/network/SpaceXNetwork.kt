package com.example.spacex.logic.network

import com.example.spacex.logic.network.api.MainService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * @ClassName SpaceXNetwork
 * @Description manage all request
 * @Author mailo
 * @Date 2021/4/27
 */
class SpaceXNetwork {
    private val mainService = ServiceCreator.create(MainService::class.java)

    suspend fun fetchLaunches(url: String) = mainService.getLaunches(url).await()

    suspend fun fetchOneLaunch(flightNumber: Int) = mainService.getOneLaunch(flightNumber).await()

    suspend fun fetchOneRocket(RocketId: String) = mainService.getOneRocket(RocketId).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object {

        private var network: SpaceXNetwork? = null

        fun getInstance(): SpaceXNetwork {
            if (network == null) {
                synchronized(SpaceXNetwork::class.java) {
                    if (network == null) {
                        network = SpaceXNetwork()
                    }
                }
            }
            return network!!
        }
    }
}