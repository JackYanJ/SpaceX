package com.example.spacex.util

import com.example.spacex.Const
import com.example.spacex.logic.model.LaunchItem
import com.example.spacex.ui.community.LaunchesViewModel


/**
 * @ClassName QuickSort
 * @Description quick sort
 * @Author mailo
 * @Date 2021/4/28
 */
object QuickSort {
    val alphabets = listOf(
        'A',
        'B',
        'C',
        'D',
        'E',
        'F',
        'G',
        'H',
        'I',
        'J',
        'K',
        'L',
        'M',
        'N',
        'O',
        'P',
        'Q',
        'R',
        'S',
        'T',
        'U',
        'V',
        'W',
        'X',
        'Y',
        'Z'
    )

    public fun quickSort(dataList: ArrayList<LaunchItem>, type: Int) {
        quickSort(dataList, 0, dataList.size - 1, type)
    }

    private fun quickSort(src: ArrayList<LaunchItem>, l: Int, r: Int, type: Int) {
        if (l < r) {
            val p = quickSortPartition(src, l, r, type)
            quickSort(src, l, p - 1, type)
            quickSort(src, p + 1, r, type)
        }
    }

    private fun quickSortPartition(src: ArrayList<LaunchItem>, l: Int, r: Int, type: Int): Int {
        var li = l
        for (i in l until r) {
            when (type) {
                Const.SortType.SORT_BY_DATE -> if (src[i].launch_date_unix <= src[r].launch_date_unix) {
                    src.swap(i, li)
                    li++
                }
                Const.SortType.SORT_BY_MISSION -> if (alphabets.indexOf(
                        src[i].mission_name.get(0).toUpperCase()
                    ) <= alphabets.indexOf(
                        src[r].mission_name.get(0).toUpperCase()
                    )
                ) {
                    src.swap(i, li)
                    li++
                }
            }

        }
        src.swap(r, li)
        return li
    }

    //switch position
    fun ArrayList<LaunchItem>.swap(i: Int, j: Int) {
        val tmp = this[i]
        this[i] = this[j]
        this[j] = tmp
    }
}