package com.huaqing.property.ui

import android.content.Intent
import com.huaqing.property.R
import com.huaqing.property.base.ui.activity.BaseActivity
import com.huaqing.property.databinding.ActivityMainBinding
import com.huaqing.property.ext.statusbar.StatusBarCompat

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId = R.layout.activity_main

    override fun initView() {
        StatusBarCompat.translucentStatusBar(this, true)
    }

    companion object {

        fun launch(activity: androidx.fragment.app.FragmentActivity) =
            activity.apply {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }
}