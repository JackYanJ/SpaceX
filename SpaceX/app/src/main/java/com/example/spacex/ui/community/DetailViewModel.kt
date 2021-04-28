package com.example.spacex.ui.community

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.spacex.logic.MainRepository
import com.example.spacex.logic.model.LaunchItem
import com.example.spacex.logic.model.RocketItem


/**
 * @ClassName OneLaunchViewModel
 * @Description one Launch ViewModel
 * @Author mailo
 * @Date 2021/4/28
 */
class DetailViewModel(repository: MainRepository) : ViewModel() {
    var launchDetail : LaunchItem? = null
    var RocketDetail : RocketItem? = null

    var flightNumber = 0
    var rocketId = ""

    private var requestLaunchParamLiveData = MutableLiveData<RequestParam>()
    private var requestRocketParamLiveData = MutableLiveData<RequestParam2>()

    val launchDetailLiveData = Transformations.switchMap(requestLaunchParamLiveData) {
        liveData {
            val resutlt = try {
                val launchDetail = repository.refreshLaunchDetail(it.flightNumber)
                Result.success(launchDetail)
            } catch (e: Exception) {
                Result.failure<LaunchItem>(e)
            }
            emit(resutlt)
        }
    }

    val rocketDetailLiveData = Transformations.switchMap(requestRocketParamLiveData) {
        liveData {
            val resutlt = try {
                val rocketDetail = repository.refreshRocketDetail(it.rocketId)
                Result.success(rocketDetail)
            } catch (e: Exception) {
                Result.failure<RocketItem>(e)
            }
            emit(resutlt)
        }
    }

    fun onRefresh() {
        if (launchDetail == null) {
            requestLaunchParamLiveData.value = RequestParam(flightNumber)
        }
        if (RocketDetail == null){
            requestRocketParamLiveData.value = RequestParam2(rocketId)
        }
    }

    inner class RequestParam(val flightNumber: Int)
    inner class RequestParam2(val rocketId: String)

}
