package com.example.spacex.ui.community

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spacex.R
import com.example.spacex.extension.inflate
import com.example.spacex.logic.model.LaunchItem
import com.example.spacex.ui.DetailActivity
import com.example.spacex.ui.MainActivity
import com.example.spacex.util.DateUtil
import com.example.spacex.util.GlobalUtil


/**
 * @ClassName LaunchAdapter
 * @Description Launch Adapter
 * @Author mailo
 * @Date 2021/4/27
 */
class LaunchAdapter(val activity: MainActivity, var dataList: List<LaunchItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LaunchesViewHolder(R.layout.item_launch.inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        holder as LaunchesViewHolder
        item.run {
            holder.launchesDate.text = item.launch_date_utc

            holder.missionName.text = item.mission_name

            holder.launchState.text = when (item.launch_success) {
                true -> GlobalUtil.getString(R.string.launch_successful)
                false -> GlobalUtil.getString(R.string.launch_false)
            }

            holder.rootView.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    DetailActivity.start(activity, item.flight_number, item.rocket.rocket_id)
                }

            })
        }
    }

    inner class LaunchesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val launchesDate = view.findViewById<TextView>(R.id.date)
        val missionName = view.findViewById<TextView>(R.id.name)
        val launchState = view.findViewById<TextView>(R.id.launchState)
        val rootView = view.findViewById<LinearLayout>(R.id.rootView)
    }

    companion object {
        const val TAG = "FollowAdapter"
    }
}