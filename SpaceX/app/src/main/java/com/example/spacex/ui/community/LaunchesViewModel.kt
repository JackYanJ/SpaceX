package com.example.spacex.ui.community

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.spacex.Const
import com.example.spacex.logic.MainRepository
import com.example.spacex.logic.model.Launches
import com.example.spacex.logic.network.api.MainService
import com.example.spacex.util.QuickSort


/**
 * @ClassName LaunchesViewModel
 * @Description Launch ViewModel
 * @Author mailo
 * @Date 2021/4/27
 */
class LaunchesViewModel(repository: MainRepository) : ViewModel() {


    var dataList = Launches()

    var dataListBackup = Launches()

    private var requestParamLiveData = MutableLiveData<String>()

    val dataListLiveData = Transformations.switchMap(requestParamLiveData) { url ->
        liveData {
            val resutlt = try {
                val launches = repository.refreshLaunches(url)
                Result.success(launches)
            } catch (e: Exception) {
                Result.failure<Launches>(e)
            }
            emit(resutlt)
        }
    }

    fun onRefresh() {
        requestParamLiveData.value = MainService.LAUNCHES_URL
    }

    fun sortByDate(adapter: LaunchAdapter) {
        QuickSort.quickSort(dataList, Const.SortType.SORT_BY_DATE)
        adapter.notifyDataSetChanged()
    }

    fun sortByMission(adapter: LaunchAdapter) {
        QuickSort.quickSort(dataList, Const.SortType.SORT_BY_MISSION)
        adapter.notifyDataSetChanged()
    }

    fun filterByLaunch(adapter: LaunchAdapter, isFilter : Boolean, sortType:Int){
        when(isFilter){
            true -> {
                var temp = Launches()
                for(i in 0 until dataList.size - 1 ){
                    if (dataList[i].launch_success){
                        temp.add(dataList[i])
                    }
                }
                dataList.clear()
                dataList.addAll(temp)
                when (sortType) {
                    Const.SortType.SORT_BY_DATE -> sortByDate(adapter)
                    Const.SortType.SORT_BY_MISSION -> sortByMission(adapter)
                }
            }
            false -> {
                dataList.clear()
                dataList.addAll(dataListBackup)
                when (sortType) {
                    Const.SortType.SORT_BY_DATE -> sortByDate(adapter)
                    Const.SortType.SORT_BY_MISSION -> sortByMission(adapter)
                }
            }
        }
    }


}