package com.lhr.water.ui.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.lhr.water.R
import com.lhr.water.databinding.ActivityCoverBinding
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import timber.log.Timber

class DeepLinkActivity : BaseActivity() {
    private val viewModel:SettingViewModel by viewModels{ (applicationContext as APP).appContainer.viewModelFactory }
    private var _binding: ActivityCoverBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityCoverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.primaryBlue, null)

        // 從 Intent 獲取 URL
        val url = intent?.data?.toString()

        // 根據不同的 URL 路徑處理
        url?.let {
            handleUrl(it)
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent) // 設置新的Intent為當前Activity的Intent
        // 從 Intent 獲取 URL
        val url = intent?.data?.toString()

        // 根據不同的 URL 路徑處理
        url?.let {
            handleUrl(it)
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun handleUrl(url: String) {
        val thisActivity = this
        try {
            when {
                url.contains("https://pda-internal.water.gov.tw/auto-download") -> {
                    viewModel.uploadFiles(thisActivity)
                    finish()
                }

                url.contains("https://pda-internal.water.gov.tw/auto-upload") -> {
                    viewModel.writeJsonObjectToFolder(thisActivity)
                    finish()
                }
            }
        } catch (e: Exception) {
            // 處理其他異常
            Timber.tag("Exception").e(e.toString())
        }
    }

}