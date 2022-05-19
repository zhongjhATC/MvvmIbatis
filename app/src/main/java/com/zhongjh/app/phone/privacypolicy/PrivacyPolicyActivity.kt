package com.zhongjh.app.phone.privacypolicy

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import com.zhongjh.app.R
import com.zhongjh.app.databinding.ActivityPrivacyPolicyBinding
import com.zhongjh.app.phone.main.MainActivity
import com.zhongjh.mvvmibatis.base.BaseApplication
import com.zhongjh.mvvmibatis.base.ui.BaseActivity
import com.zhongjh.mvvmibatis.extend.onClick
import com.zhongjh.mvvmibatis.utils.LinkUrlText


/**
 * 这是一个向用户申请是否同意隐私协议的界面
 * @author zhongjh
 * @date 2022/5/5
 */
class PrivacyPolicyActivity :
    BaseActivity<ActivityPrivacyPolicyBinding, PrivacyPolicyModel>(R.layout.activity_privacy_policy) {

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initialize() {
        setContent()
    }

    override fun initListener() {
        mBinding.btnAgree.onClick {
            val intent = Intent(this@PrivacyPolicyActivity, MainActivity::class.java)
            startActivity(intent)
            BaseApplication.instance.init()
            this@PrivacyPolicyActivity.finish()
        }

        mBinding.btnDisagree.onClick {
            this@PrivacyPolicyActivity.finish()
        }
    }

    /**
     * 设置文本和链接
     */
    private fun setContent() {
        // 文本内容
        val ss =
            SpannableString("欢迎您使用MVIDemo！\n\n在使用App之前，请您阅读并充分理解MVIDemo的《隐私政策》，了解您的用户权益及相关数据的处理方法。如果您同意以上协议内容，请点击“同意”，开始使用我们的产品和服务。我们将以业界领先的信息安全保护水平，保护您的个人信息，感谢您对MVIDemo的信任。")
        // 设置字符颜色,点击事件
        ss.setSpan(
            LinkUrlText(this@PrivacyPolicyActivity, "https://www.baidu.com/"),
            41,
            47,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mBinding.tvContent.text = ss
        // 不添加这一句，拨号，http，发短信的超链接不能执行.
        mBinding.tvContent.movementMethod = LinkMovementMethod.getInstance()
    }

}