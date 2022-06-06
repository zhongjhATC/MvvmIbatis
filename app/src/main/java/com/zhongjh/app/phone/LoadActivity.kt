package com.zhongjh.app.phone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.zhongjh.app.R
import com.zhongjh.app.phone.privacypolicy.PrivacyPolicyActivity
import com.zhongjh.mvvmibatis.base.BaseApplication

/**
 * 启动过渡界面，如果直接跳到正式界面，会因为样式不同而导致白屏
 * @author zhongjh
 * @date 2022/6/6
 */
class LoadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        ImmersionBar.with(this).fullScreen(true).init()
        val intent = Intent(this@LoadActivity, PrivacyPolicyActivity::class.java)
        startActivity(intent)
        BaseApplication.instance.init()
        this@LoadActivity.finish()
    }

}