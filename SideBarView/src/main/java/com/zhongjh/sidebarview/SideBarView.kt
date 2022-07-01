package com.zhongjh.sidebarview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

/**
 * 侧边控件
 * @author zhongjh
 * @date 2022/6/30
 */
class SideBarView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val tag = SideBarView::class.java.simpleName
    private var mListener: OnSideBarViewListener? = null

    /**
     * 该控件相关属性
     */
    private val sideBarEntity = SideBarEntity()

    /**
     * 控件列表数据源
     */
    private lateinit var mLetters: List<String>
    private var mSelect = 0

    /**
     * 上一个的选择索引
     */
    private var mPrevSelect = 0

    private var mSlideBarRect = RectF()

    /**
     * 未选择文本画笔
     */
    private var mTextPaint = TextPaint()

    /**
     * 已选择文本画笔
     */
    private var mSelectTextPaint = TextPaint()
    private var mPaint = Paint()

    init {
        initAttribute(attrs, defStyleAttr)
        initTool()
        initData()
    }

    fun setOnLetterChangeListener(listener: OnSideBarViewListener) {
        this.mListener = listener
    }

    /**
     * 初始化属性
     */
    private fun initAttribute(attrs: AttributeSet?, defStyleAttr: Int) {
        val typeArray =
            context.obtainStyledAttributes(attrs, R.styleable.SideBarView, defStyleAttr, 0)
        initAttributeView(typeArray)
        initAttributeSelect(typeArray)
        sideBarEntity.textColor =
            typeArray.getColor(R.styleable.SideBarView_textColor, Color.parseColor("#969696"))
        sideBarEntity.textSize = typeArray.getDimensionPixelOffset(
            R.styleable.SideBarView_textSize,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 10f,
                resources.displayMetrics
            ).toInt()
        )
        sideBarEntity.contentPadding = typeArray.getDimensionPixelOffset(
            R.styleable.SideBarView_contentPadding,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2f,
                resources.displayMetrics
            ).toInt()
        )
        typeArray.recycle()
    }

    private fun initTool() {
        // 抗锯齿开启,防止模糊
        mTextPaint.isAntiAlias = true
        // 文字以居左形式绘画
        mTextPaint.textAlign = Paint.Align.LEFT
        mTextPaint.color = sideBarEntity.textColor
        mTextPaint.textSize = sideBarEntity.textSize.toFloat()

        mSelectTextPaint.isAntiAlias = true
        mSelectTextPaint.textAlign = Paint.Align.LEFT
        mSelectTextPaint.color = sideBarEntity.selectTextColor
        mSelectTextPaint.textSize = sideBarEntity.selectTextSize.toFloat()
    }

    /**
     * 初始化View的属性
     */
    private fun initAttributeView(typeArray: TypedArray) {
        sideBarEntity.barPadding = typeArray.getDimensionPixelOffset(
            R.styleable.SideBarView_barPadding,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 6f,
                resources.displayMetrics
            ).toInt()
        )
    }

    /**
     * 绘制选中时的样式
     */
    private fun initAttributeSelect(typeArray: TypedArray) {
        sideBarEntity.selectTextColor = typeArray.getColor(
            R.styleable.SideBarView_selectTextColor,
            Color.parseColor("#FF0000")
        )
        sideBarEntity.selectTextSize = typeArray.getDimensionPixelOffset(
            R.styleable.SideBarView_selectTextSize,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 10f,
                resources.displayMetrics
            ).toInt()
        )
    }

    private fun initData() {
        mLetters = context.resources.getStringArray(R.array.slide_bar_value_list).toMutableList()
        mPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val contentLeft: Int = paddingLeft
        val contentRight: Int = measuredWidth - paddingRight
        val contentTop: Int = paddingTop
        val contentBottom = (measuredHeight - paddingBottom)
        // 设置画布，可以看到画布
        mSlideBarRect.set(
            contentLeft.toFloat(),
            contentTop.toFloat(),
            contentRight.toFloat(),
            contentBottom.toFloat()
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制slide bar 上字母列表
        drawLetters(canvas)
        // 绘制选中的slide bar上的那个文字
        drawSelect(canvas)
    }

    /**
     * 绘制slide bar 上列表
     */
    private fun drawLetters(canvas: Canvas) {
        // 顺序绘制文字 每个文字的高度,计算：(取整个view高度 - view上下间隔) / 集合长度
        val itemHeight: Float =
            (mSlideBarRect.bottom - mSlideBarRect.top - sideBarEntity.contentPadding * 2) / mLetters.size

        for (index in mLetters.indices) {
            // 获取每一个索引的坐标中心点
            val center =
                mSlideBarRect.top + sideBarEntity.contentPadding + itemHeight * index + itemHeight / 2
            val pointY: Float =
                getTextBaseLineByCenter(center, mTextPaint, sideBarEntity.textSize)
            // 以文字的左下角的xy坐标形式画文字
            drawMiddleText(canvas, mLetters[index], pointY, mTextPaint)
        }
    }

    /**
     * 绘制选中的slide bar上的那个文字
     */
    private fun drawSelect(canvas: Canvas) {
        if (mSelect != -1) {
            val itemHeight: Float =
                (mSlideBarRect.bottom - mSlideBarRect.top - sideBarEntity.contentPadding * 2) / mLetters.size
            val center =
                mSlideBarRect.top + sideBarEntity.contentPadding + itemHeight * mSelect + itemHeight / 2
            val pointY: Float =
                getTextBaseLineByCenter(center, mSelectTextPaint, sideBarEntity.textSize)
            // 以文字的左下角的xy坐标形式画文字
            drawMiddleText(canvas, mLetters[mSelect], pointY, mSelectTextPaint)
        }
    }

    /**
     * 触摸本身便触发相关事件
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.d(tag, "dispatchTouchEvent " + event.action)
        // 获取xy坐标
        val y = event.y - paddingTop
        mPrevSelect = mSelect
        // 计算当前触摸的索引，计算公式： y坐标 / view的height * 数据长度
        // 因为整个view的height是 / 数据长度的，所以用点击的 y坐标/view height 再 * 数据长度回来能获取具体的索引
        mSelect = (y / (mSlideBarRect.bottom - mSlideBarRect.top) * mLetters.size).toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (mPrevSelect != mSelect && mSelect >= 0 && mSelect < mLetters.size) {
                    mListener?.onSideBarViewStart(mLetters[mSelect])
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (mPrevSelect != mSelect && mSelect >= 0 && mSelect < mLetters.size) {
                    mListener?.onSideBarViewChange(mLetters[mSelect])
                    invalidate()
                    Log.d(tag, "MotionEvent.ACTION_MOVE")
                }
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                mSelect = -1
                mListener?.onSideBarViewEnd()
            }
            else -> {}
        }
        return true
    }

    /**
     * @param center 文字中心点
     * @param paint 文字画笔
     * @param size 文字
     */
    private fun getTextBaseLineByCenter(center: Float, paint: TextPaint, size: Int): Float {
        paint.textSize = size.toFloat()
        val fontMetrics = paint.fontMetrics
        val height = fontMetrics.bottom - fontMetrics.top
        return center + height / 2 - fontMetrics.bottom
    }

    /**
     * 绘制居中的文字,因为drawText是在文字的左下角坐标xy绘画的，所以要做些居中处理
     * @param canvas 绘图类
     * @param text 绘画的文字
     */
    private fun drawMiddleText(canvas: Canvas, text: String, pointY: Float, textPaint: TextPaint) {
        val textWidth: Float = textPaint.measureText(text)
        // 计算公式:view宽度一半 - 文字宽度一半 = 文字居中
        canvas.drawText(text, measuredWidth / 2 - textWidth / 2, pointY, textPaint)
    }
}