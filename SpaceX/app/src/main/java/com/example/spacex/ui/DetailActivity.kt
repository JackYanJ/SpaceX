package com.example.spacex.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.spacex.R
import com.example.spacex.extension.showToast
import com.example.spacex.ui.community.DetailViewModel
import com.example.spacex.util.InjectorUtil
import com.example.spacex.util.ResponseHandler
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getDetailViewModelFactory()).get(
            DetailViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar);
        val actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.title = "Detail"
        initParam()
        observe()
        viewModel.onRefresh()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                finish()
        }
        return true
    }

    private fun observe() {
        //launch detail
        if (!viewModel.launchDetailLiveData.hasObservers()) {
            viewModel.launchDetailLiveData.observe(this, Observer { result ->
                val response = result.getOrNull()
                if (response == null) {
                    ResponseHandler.getFailureTips(result.exceptionOrNull()).showToast()
                    return@Observer
                }

                missionName.text = "Mission Name:" + response.mission_name
                launchDate.text = "Launch Date:" + response.launch_date_utc
                launchSite.text = "Launch Site:" + response.launch_site.site_name_long
                launchState.text = "Launch State:" + when (response.launch_success) {
                    true -> {
                        "success"
                    }
                    false -> {
                        "failure"
                    }
                }
                flightClub.text = "Flight Club:" + response.telemetry.flight_club
                LaunchVideo.text = "Launch Video:" + response.links.video_link
                LaunchWiki.text = "Launch Wiki:" + response.links.wikipedia
            })
        }
        //rocket detail
        if (!viewModel.rocketDetailLiveData.hasObservers()) {
            viewModel.rocketDetailLiveData.observe(this, Observer { result ->
                val response = result.getOrNull()
                if (response == null) {
                    ResponseHandler.getFailureTips(result.exceptionOrNull()).showToast()
                    return@Observer
                }
                rocketName.text = "Rocket Name:" + response.rocket_name
                rocketType.text = "Rocket Type:" + response.rocket_type
                rocketHeight.text = "Rocket Height:" + response.height.meters + "meters"
                rocketDiameter.text = "Rocket Diameter:" + response.diameter.meters + "meters"
                rocketMass.text = "Rocket Mass:" + response.mass.kg + "kg"
                rockeDescription.text = "Rocket Description:" + response.description
                rocketWiki.text = "Rocket Wiki:" + response.wikipedia
            })
        }

    }

    fun initParam() {
        if (intent.getStringExtra(EXTRA_ROCKET_ID) != null) viewModel.rocketId =
            intent.getStringExtra(EXTRA_ROCKET_ID)!!
        if (intent.getIntExtra(EXTRA_FLIGHT_NUMBER, 0) != 0) viewModel.flightNumber =
            intent.getIntExtra(EXTRA_FLIGHT_NUMBER, 0)
    }

    companion object {
        const val TAG = "DetailActivity"

        const val EXTRA_FLIGHT_NUMBER = "flightNumber"
        const val EXTRA_ROCKET_ID = "rocketId"

        fun start(context: Activity, flightNumber: Int, rocketId: String) {
            val starter = Intent(context, DetailActivity::class.java)
            starter.putExtra(EXTRA_FLIGHT_NUMBER, flightNumber)
            starter.putExtra(EXTRA_ROCKET_ID, rocketId)
            context.startActivity(starter)
        }
    }
}