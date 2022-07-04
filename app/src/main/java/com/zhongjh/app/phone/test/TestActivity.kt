package com.zhongjh.app.phone.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.zhongjh.app.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).transparentBar().init()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classify)
    }
}