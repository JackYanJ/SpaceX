package com.example.spacex.logic

import com.example.spacex.logic.dao.MainDao
import com.example.spacex.logic.network.SpaceXNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext


/**
 * @ClassName LaunchsRepository
 * @Description  manage repository data
 * @Author mailo
 * @Date 2021/4/27
 */
class MainRepository private constructor(private val launchsDao: MainDao, private val spaceXNetworl: SpaceXNetwork) {

    suspend fun refreshLaunches(url: String) = requestLaunches(url)

    suspend fun refreshLaunchDetail(flightNumber: Int) = requestLaunchDetail(flightNumber)

    suspend fun refreshRocketDetail(rocketId: String) = requestRocketDetail(rocketId)

    private suspend fun requestLaunches(url: String) = withContext(Dispatchers.IO) {
        val response = spaceXNetworl.fetchLaunches(url)
//        launchsDao.cacheLaunches(response)
        response
    }

    private suspend fun requestLaunchDetail(flightNumber: Int) = withContext(Dispatchers.IO) {
        coroutineScope {
            val deferOneLaunchDetail = async { spaceXNetworl.fetchOneLaunch(flightNumber) }
            val oneLaunchDetail = deferOneLaunchDetail.await()
            oneLaunchDetail
        }
    }

    private suspend fun requestRocketDetail(rocketId: String) = withContext(Dispatchers.IO) {
        coroutineScope {
            val deferOneRocketDetail = async { spaceXNetworl.fetchOneRocket(rocketId) }
            val oneRocketDetail = deferOneRocketDetail.await()
            oneRocketDetail
        }
    }

    companion object {

        private var repository: MainRepository? = null

        fun getInstance(dao: MainDao, network: SpaceXNetwork): MainRepository {
            if (repository == null) {
                synchronized(MainRepository::class.java) {
                    if (repository == null) {
                        repository = MainRepository(dao, network)
                    }
                }
            }

            return repository!!
        }
    }
}