package com.zhongjh.app.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.zhongjh.app.R
import com.zy.multistatepage.MultiState
import com.zy.multistatepage.MultiStateContainer

/**
 * 一个等待的动画Lottie界面
 * @author zhongjh
 * @date 2022/5/27
 */
class LottieWaitingState : MultiState() {
    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.multi_lottie_waiting, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
    }
}