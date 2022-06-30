package com.zhongjh.sidebarview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
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

    /**
     * 该控件相关属性
     */
    private val sideBarEntity = SideBarEntity()

    /**
     * 控件列表数据源
     */
    private lateinit var mLetters: List<String>
    private var mSelect = -1
    private val mAnimationRatio = 0f

    private var mSlideBarRect = RectF()
    private var mTextPaint = TextPaint()
    private var mPaint = Paint()

    init {
        initAttribute(attrs, defStyleAttr)
        initData()
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

    /**
     * 初始化View的属性
     */
    private fun initAttributeView(typeArray: TypedArray) {
        sideBarEntity.backgroundColor = typeArray.getColor(
            R.styleable.SideBarView_backgroundColor, Color.parseColor("#F9F9F9")
        )
        sideBarEntity.strokeColor =
            typeArray.getColor(R.styleable.SideBarView_strokeColor, Color.parseColor("#000000"))
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
        sideBarEntity.hintTextColor =
            typeArray.getColor(R.styleable.SideBarView_hintTextColor, Color.parseColor("#FFFFFF"))
        sideBarEntity.hintTextSize = typeArray.getDimensionPixelOffset(
            R.styleable.SideBarView_hintTextSize,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16f,
                resources.displayMetrics
            ).toInt()
        )
        sideBarEntity.hintCircleRadius = typeArray.getDimensionPixelOffset(
            R.styleable.SideBarView_hintCircleRadius,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 24f,
                resources.displayMetrics
            ).toInt()
        )
        sideBarEntity.hintCircleColor = typeArray.getColor(
            R.styleable.SideBarView_hintCircleColor,
            Color.parseColor("#bef9b81b")
        )
        sideBarEntity.hintShape = typeArray.getInteger(R.styleable.SideBarView_hintShape, 0)
    }

    private fun initData() {
        mLetters = context.resources.getStringArray(R.array.slide_bar_value_list).toMutableList()
        mPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val contentLeft: Int = sideBarEntity.barPadding
        val contentRight: Int = measuredWidth - sideBarEntity.barPadding
        val contentTop: Int = sideBarEntity.barPadding
        val contentBottom = (measuredHeight - sideBarEntity.barPadding).toFloat()
        // 设置画布，可以看到画布
        mSlideBarRect.set(
            contentLeft.toFloat(),
            contentTop.toFloat(),
            contentRight.toFloat(),
            contentBottom
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制slide bar 上字母列表
        drawLetters(canvas)
//        // 绘制选中时的提示信息(圆＋文字)
//        drawHint(canvas)
//        // 绘制选中的slide bar上的那个文字
//        drawSelect(canvas)
    }

    /**
     * 绘制slide bar 上字母列表
     */
    private fun drawLetters(canvas: Canvas) {
        // 顺序绘制文字 每个文字的高度,计算：(取整个view高度 - view上下间隔) / 集合长度
        val itemHeight: Float =
            (mSlideBarRect.bottom - mSlideBarRect.top - sideBarEntity.contentPadding * 2) / mLetters.size
        mTextPaint.color = sideBarEntity.textColor
        mTextPaint.textSize = sideBarEntity.textSize.toFloat()
        // 文字以居左形式绘画
        mTextPaint.textAlign = Paint.Align.LEFT
        for (index in mLetters.indices) {
            // 获取每一个索引的坐标中心点
            val center =
                mSlideBarRect.top + sideBarEntity.contentPadding + itemHeight * index + itemHeight / 2
            val pointY: Float =
                getTextBaseLineByCenter(center, mTextPaint, sideBarEntity.textSize)
            val pointX = mSlideBarRect.left + (mSlideBarRect.right - mSlideBarRect.left) / 2.0f
            // 以文字的左下角的xy坐标形式画文字
            drawMiddleText(canvas, mLetters[index], pointY)
//            canvas.drawText(mLetters[index], 0F, pointY, mTextPaint)
        }
    }

    /**
     * 绘制选中时的提示信息(圆＋文字)
     */
    private fun drawHint(canvas: Canvas) {
        // x轴的移动路径
        val circleCenterX: Float = measuredWidth + sideBarEntity.hintCircleRadius -
                (-measuredWidth / 2 + (measuredWidth + sideBarEntity.hintCircleRadius)) * mAnimationRatio
        mPaint.style = Paint.Style.FILL
        mPaint.color = sideBarEntity.hintCircleColor
        if (sideBarEntity.hintShape == 0) {
            canvas.drawCircle(
                circleCenterX, measuredHeight / 2.0f,
                sideBarEntity.hintCircleRadius.toFloat(), mPaint
            )
        } else {
            canvas.drawRect(
                circleCenterX - sideBarEntity.hintCircleRadius,
                measuredHeight / 2.0f - sideBarEntity.hintCircleRadius,
                circleCenterX + sideBarEntity.hintCircleRadius,
                measuredHeight / 2.0f + sideBarEntity.hintCircleRadius,
                mPaint
            )
        }
        // 绘制圆中心的提示字符
        if (mSelect != -1) {
            val target = mLetters[mSelect]
            val textY: Float = getTextBaseLineByCenter(
                measuredHeight / 2.0f,
                mTextPaint,
                sideBarEntity.hintTextSize
            )
            mTextPaint.color = sideBarEntity.hintTextColor
            mTextPaint.textSize = sideBarEntity.hintTextSize.toFloat()
            mTextPaint.textAlign = Paint.Align.CENTER
            canvas.drawText(target, circleCenterX, textY, mTextPaint)
        }
    }

    /**
     * 绘制选中的slide bar上的那个文字
     */
    private fun drawSelect(canvas: Canvas) {
        if (mSelect != -1) {
            mTextPaint.color = sideBarEntity.selectTextColor
            mTextPaint.textSize = sideBarEntity.selectTextSize.toFloat()
            mTextPaint.textAlign = Paint.Align.CENTER
            val itemHeight: Float =
                (mSlideBarRect.bottom - mSlideBarRect.top - sideBarEntity.contentPadding * 2) / mLetters.size
            val baseLine: Float = getTextBaseLineByCenter(
                mSlideBarRect.top + sideBarEntity.contentPadding + itemHeight * mSelect + itemHeight / 2,
                mTextPaint,
                sideBarEntity.textSize
            )
            val pointX = mSlideBarRect.left + (mSlideBarRect.right - mSlideBarRect.left) / 2.0f
            canvas.drawText(mLetters[mSelect], pointX, baseLine, mTextPaint)
        }
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
    private fun drawMiddleText(canvas: Canvas, text: String, pointY: Float) {
        val textWidth: Float = mTextPaint.measureText(text)
        // 计算公式:view宽度一半 - 文字宽度一半 = 文字居中
        canvas.drawText(text, measuredWidth / 2 - textWidth / 2, pointY, mTextPaint)
    }
}