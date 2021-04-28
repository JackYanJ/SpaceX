package com.example.spacex.logic.dao


/**
 * @ClassName SpaceXDatabase
 * @Description Dao control class
 * @Author mailo
 * @Date 2021/4/27
 */
object SpaceXDatabase {
    private var mainDao: MainDao? = null

    fun getMainPageDao(): MainDao {
        if (mainDao == null) {
            mainDao = MainDao()
        }
        return mainDao!!
    }

}