package com.example.spacex.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spacex.logic.MainRepository


/**
 * @ClassName ViewModelFactory
 * @Description ViewModel Factory
 * @Author mailo
 * @Date 2021/4/27
 */
class DetailViewModelFactory (val repository: MainRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(repository) as T
    }
}