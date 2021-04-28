package com.example.spacex.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.widget.CompoundButton
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.CallSuper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacex.Const
import com.example.spacex.R
import com.example.spacex.extension.gone
import com.example.spacex.extension.logD
import com.example.spacex.extension.showToast
import com.example.spacex.extension.visible
import com.example.spacex.ui.common.callback.RequestLifecycle
import com.example.spacex.ui.common.view.SimpleDividerDecoration
import com.example.spacex.ui.common.view.StickyDecoration
import com.example.spacex.ui.community.LaunchAdapter
import com.example.spacex.ui.community.LaunchesViewModel
import com.example.spacex.util.DateUtil
import com.example.spacex.util.GlobalUtil
import com.example.spacex.util.InjectorUtil
import com.example.spacex.util.ResponseHandler
import com.scwang.smart.refresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RequestLifecycle {

    /**
     * if has loaded data
     */
    private var mHasLoadedData = false

    /**
     * error view
     */
    private var loadErrorView: View? = null

    /**
     * root view
     */
    protected var rootView: View? = null

    /**
     * loading view
     */
    protected var loading: ProgressBar? = null


    /**
     * log tag
     */
    protected val TAG: String = this.javaClass.simpleName

    private val viewModel by lazy { ViewModelProvider(this, InjectorUtil.getLaunchesViewModelFactory()).get(LaunchesViewModel::class.java) }

    private lateinit var adapter: LaunchAdapter

    private var sortType = Const.SortType.SORT_BY_DATE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        rootView = LayoutInflater.from(this).inflate(R.layout.activity_main, null)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        loading = findViewById(R.id.loading)

        toolbar.title = GlobalUtil.getString(R.string.app_name)

        adapter = LaunchAdapter(this, viewModel.dataList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(StickyDecoration(this, object : StickyDecoration.DecorationCallback{
            override fun getData(position: Int): String {
                when(sortType){
                    Const.SortType.SORT_BY_DATE ->
                        return viewModel.dataList[position].launch_year
                    Const.SortType.SORT_BY_MISSION ->
                        return viewModel.dataList[position].mission_name.substring(0,1)
                    else ->
                        return viewModel.dataList[position].launch_year
                }
            }

        }))
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = null
        refreshLayout.setOnRefreshListener { viewModel.onRefresh() }
        refreshLayout.gone()
        observe()

        rgSort.check(R.id.rbSortByDate)

        rgSort.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbSortByDate -> {
                    viewModel.sortByDate(adapter)
                    sortType = Const.SortType.SORT_BY_DATE
                }
                R.id.rbSortByMission -> {
                    viewModel.sortByMission(adapter)
                    sortType = Const.SortType.SORT_BY_MISSION
                }
            }
        }

        cbFilter.setOnCheckedChangeListener { _, isChecked ->
            viewModel.filterByLaunch(
                adapter,
                isChecked,
                sortType
            )
        }
    }

    override fun onResume() {
        super.onResume()
        if (!mHasLoadedData) {
            loadDataOnce()
            mHasLoadedData = true
        }
    }

    /**
     * loading start
     */
    @CallSuper
    override fun startLoading() {
        loading?.visibility = View.VISIBLE
        hideLoadErrorView()
        viewModel.onRefresh()
        refreshLayout.gone()
    }

    /**
     * loading finished
     */
    @CallSuper
    override fun loadFinished() {
        loading?.visibility = View.GONE
        refreshLayout.visible()
    }

    /**
     * loading fail
     */
    @CallSuper
    override fun loadFailed(msg: String?) {
        loading?.visibility = View.GONE
        showLoadErrorView(msg ?: GlobalUtil.getString(R.string.unknown_error)) { startLoading() }
        refreshLayout.visible()
    }


    open fun loadDataOnce() {
        startLoading()
    }

    /**
     * return error information to user
     *
     * @param tip
     * @param block
     */
    protected fun showLoadErrorView(tip: String, block: View.() -> Unit) {
        if (loadErrorView != null) {
            loadErrorView?.visibility = View.VISIBLE
            return
        }
        if (rootView != null) {
            val viewStub = rootView?.findViewById<ViewStub>(R.id.loadErrorView)
            if (viewStub != null) {
                loadErrorView = viewStub.inflate()
                val loadErrorText = loadErrorView?.findViewById<TextView>(R.id.loadErrorText)
                loadErrorText?.text = tip
                val loadErrorkRootView = loadErrorView?.findViewById<View>(R.id.loadErrorkRootView)
                loadErrorkRootView?.setOnClickListener {
                    it?.block()
                }
            }
        }
    }

    /**
     * 将load error view进行隐藏。
     */
    protected fun hideLoadErrorView() {
        loadErrorView?.visibility = View.GONE
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }


    private fun observe() {
        viewModel.dataListLiveData.observe(this, Observer { result ->
            val response = result.getOrNull()
            if (response == null) {
                ResponseHandler.getFailureTips(result.exceptionOrNull()).let { if (viewModel.dataList.isNullOrEmpty()) loadFailed(it) else it.showToast() }
                refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            loadFinished()
            if (response.isNullOrEmpty() && viewModel.dataList.isEmpty()) {
                refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            if (response.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@Observer
            }
            when (refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    viewModel.dataList.clear()
                    viewModel.dataListBackup.clear()
                    viewModel.dataList.addAll(response)
                    viewModel.dataListBackup.addAll(response)
                    when(sortType){
                        Const.SortType.SORT_BY_DATE -> {
                            viewModel.sortByDate(adapter)
                        }
                        Const.SortType.SORT_BY_MISSION -> {
                            viewModel.sortByMission(adapter)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
                else -> {
                }
            }
            refreshLayout.finishLoadMoreWithNoMoreData()
        })
    }
}