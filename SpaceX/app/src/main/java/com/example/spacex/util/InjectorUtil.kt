package com.example.spacex.util

import com.example.spacex.logic.MainRepository
import com.example.spacex.logic.dao.SpaceXDatabase
import com.example.spacex.logic.network.SpaceXNetwork
import com.example.spacex.ui.community.DetailViewModelFactory
import com.example.spacex.ui.community.LaunchesViewModelFactory


/**
 * @ClassName InjectorUtil
 * @Description logic control class
 * @Author mailo
 * @Date 2021/4/27
 */
object InjectorUtil {

    private fun getLaunchesRepository() =
        MainRepository.getInstance(SpaceXDatabase.getMainPageDao(), SpaceXNetwork.getInstance())

    fun getLaunchesViewModelFactory() = LaunchesViewModelFactory(getLaunchesRepository())

    fun getDetailViewModelFactory() = DetailViewModelFactory(getLaunchesRepository())
}