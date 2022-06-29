package com.zhongjh.app.phone.classify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhongjh.app.data.http.service.ClassifyApi
import com.zhongjh.app.entity.Classify
import com.zhongjh.app.entity.SubClass
import com.zhongjh.mvvmibatis.entity.State
import com.zhongjh.mvvmibatis.extend.launchApiFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * @author zhongjh
 * @date 2022/6/14
 */
@HiltViewModel
class ClassifyModel @Inject constructor(private val classifyApi: ClassifyApi) : ViewModel() {



    /**
     * 获取分类数据
     */
    private val _uiClassify = MutableStateFlow<State<MutableList<Classify>>>(State.Empty())
    val uiClassify: StateFlow<State<MutableList<Classify>>> = _uiClassify

    /**
     * 获取小类数据
     */
    private val _uiSubClass = MutableStateFlow<State<MutableList<SubClass>>>(State.Empty())
    val uiSubClass: StateFlow<State<MutableList<SubClass>>> = _uiSubClass

    /**
     * 分类数据
     */
    fun getClassify() {
        viewModelScope.launch {
            launchApiFlow(_uiClassify) {
                // 分类数据
                val classifies = classifyApi.classify()
                classifies.data?.get(0)?.isCheck = true
                classifies
            }
        }
    }

    /**
     * 分类数据
     */
    fun getSubClass(id: Int?) {
        id.let {
            viewModelScope.launch {
                launchApiFlow(_uiSubClass) {
                    // 分类数据
                    val subclass =
                        if (it == 1) {
                            classifyApi.subclass()
                        } else {
                            classifyApi.subclass2()
                        }
                    // 给数据源添加头部数据
                    subclass.data?.let {
                        it.sortBy { subClass -> subClass.name }
                        addHeadData(it)
                    }
                    subclass
                }
            }
        }
    }

    /**
     * 给数据源添加头部数据
     */
    private fun addHeadData(list: MutableList<SubClass>) {
        // 添加头部数据
        addHeadItem(list, 0)
        // 使用while形式循环，不能使用for循环方式，因为数据源长度会随着添加头部数据而变化
        var i = 0
        while (i < list.size) {
            // （1）. 如果没有下一个数据，则不执行添加头部数据方法
            if (i + 1 >= list.size) {
                i++
                continue
            }
            // （2）. 如果当前数据跟下一个数据的第一个字母不一样，则执行添加头部数据
            if (list[i].name?.get(0) != list[i + 1].name?.get(0)) {
                addHeadItem(list, i + 1)
            }
            i++
        }
    }

    /**
     * 添加头部数据
     * @param list 数据源
     * @param position 索引
     */
    private fun addHeadItem(list: MutableList<SubClass>, position: Int) {
        val subclass = SubClass()
        subclass.name = list[position].name?.get(0).toString()
        subclass.id = -1
        subclass.image = ""
        list.add(position, subclass)
    }

    /**
     * 能被3整除的则 给数据源设置左边间距
     */
    private fun setLeftMargin(subclass: SubClass, position: Int) {
        if (position % 3 == 0) {
            subclass.leftMargin = true
        }
    }

}