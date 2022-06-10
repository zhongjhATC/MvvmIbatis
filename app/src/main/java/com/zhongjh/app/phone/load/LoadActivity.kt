package com.zhongjh.app.phone.load

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.zhongjh.app.R
import com.zhongjh.app.data.local.MMKVLocal
import com.zhongjh.app.phone.privacypolicy.PrivacyPolicyActivity
import com.zhongjh.app.phone.splash.AdvertisingActivity
import com.zhongjh.mvvmibatis.base.BaseApplication
import com.zhongjh.mvvmibatis.utils.SPCacheUtil

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

        // 判断是否已经同意协议
        val intent : Intent
        if (SPCacheUtil.getBoolean(MMKVLocal.IS_PRIVACY_POLICY, false)) {
            // 已经同意协议，跳转到广告界面
            intent = Intent(this@LoadActivity, AdvertisingActivity::class.java)
            BaseApplication.instance.init()
        } else {
            // 未同意协议，跳转到协议界面
            intent = Intent(this@LoadActivity, PrivacyPolicyActivity::class.java)
        }
        startActivity(intent)
        this@LoadActivity.finish()

    }

}