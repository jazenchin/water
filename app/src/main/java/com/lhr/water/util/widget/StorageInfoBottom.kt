package com.lhr.water.util.widget

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.data.StorageDetail
import com.lhr.water.databinding.WidgetBottomStorageInfoBinding
import com.lhr.water.ui.map.MapActivity

class StorageInfoBottom : RelativeLayout, View.OnClickListener {
    private var binding: WidgetBottomStorageInfoBinding
    private val listener: Listener
    private val activity: MapActivity
    private val storageDetail: StorageDetail
    private val map: String
    private val region: String
    constructor(
        listener: Listener,
        activity: MapActivity,
        storageDetail: StorageDetail,
        map: String,
        region: String
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_bottom_storage_info, this, true
        )
        this@StorageInfoBottom.listener = listener
        this@StorageInfoBottom.activity = activity
        this@StorageInfoBottom.storageDetail = storageDetail
        this@StorageInfoBottom.map = map
        this@StorageInfoBottom.region = region

        activity.onBackPressedDispatcher.addCallback(
            activity, // LifecycleOwner
            activity.onBackPressedCallback
        )

        initView()
    }

    fun initView(){
        binding.textMapName.text = map
        binding.textRegionName.text = region
        binding.textStorageName.text = storageDetail.StorageName
        binding.textStorageNum.text = storageDetail.StorageNum

        binding.root.setOnClickListener(this)
        binding.constraintBack.setOnClickListener(this)
        binding.linearLayoutStorageContent.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.constraintBack -> {
                activity.onBackPressedCallback.handleOnBackPressed()
            }
            R.id.imageBack -> {
                activity.onBackPressedCallback.handleOnBackPressed()
            }
            R.id.linearLayoutStorageContent -> {
//                val intent = Intent(activity, StorageContentActivity::class.java)
//                activity.startActivity(intent)
                listener.onStorageContentClick(map, region, storageDetail)
            }
        }
    }

    interface Listener{
        fun onStorageContentClick(map: String, region: String, storageDetail: StorageDetail)
    }
}