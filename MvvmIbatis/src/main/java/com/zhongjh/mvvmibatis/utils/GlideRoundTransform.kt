package com.zhongjh.mvvmibatis.utils

import android.content.res.Resources
import android.graphics.*
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import kotlin.jvm.JvmOverloads
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest
import kotlin.math.roundToInt

/**
 * 这是兼容处理了Glide圆角和ImageView的centerCrop
 *
 * @author zhongjh
 * @date 2022/4/6
 */
class GlideRoundTransform : BitmapTransformation {
    private val radius: Float
    private var isLeftTop = true
    private var isRightTop = true
    private var isLeftBottom = true
    private var isRightBottom = true

    /**
     * 全圆角
     *
     * @param dp 圆角幅度
     */
    @JvmOverloads
    constructor(dp: Int = 4) : super() {
        radius = Resources.getSystem().displayMetrics.density * dp
    }

    /**
     * 选择性的圆角
     *
     * @param dp          圆角幅度
     * @param leftTop     左上角
     * @param rightTop    右上角
     * @param leftBottom  左下角
     * @param rightBottom 右下角
     */
    constructor(
        dp: Int,
        leftTop: Boolean,
        rightTop: Boolean,
        leftBottom: Boolean,
        rightBottom: Boolean
    ) : super() {
        radius = Resources.getSystem().displayMetrics.density * dp
        isLeftTop = leftTop
        isRightTop = rightTop
        isLeftBottom = leftBottom
        isRightBottom = rightBottom
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight)
        return roundCrop(pool, bitmap)
    }

    private fun roundCrop(pool: BitmapPool, source: Bitmap): Bitmap {
        val result = pool[source.width, source.height, Bitmap.Config.ARGB_8888]
        val canvas = Canvas(result)
        val paint = Paint()
        paint.shader =
            BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val rectF = RectF(
            0f, 0f, source.width.toFloat(), source.height
                .toFloat()
        )
        canvas.drawRoundRect(rectF, radius, radius, paint)
        // 如果都是需要圆角则不需要下面的判断
        if (!isLeftTop || !isRightTop || !isLeftBottom || !isRightBottom) {
            // 左上角圆角
            if (!isLeftTop) {
                canvas.drawRect(0f, 0f, radius, radius, paint)
            }
            // 右上角圆角
            if (!isRightTop) {
                canvas.drawRect(canvas.width - radius, 0f, canvas.width.toFloat(), radius, paint)
            }
            // 左下角圆角
            if (!isLeftBottom) {
                canvas.drawRect(0f, canvas.height - radius, radius, canvas.height.toFloat(), paint)
            }
            // 右下角圆角
            if (!isRightBottom) {
                canvas.drawRect(
                    canvas.width - radius,
                    canvas.height - radius,
                    canvas.width.toFloat(),
                    canvas.height.toFloat(),
                    paint
                )
            }
        }
        return result
    }

    val id: String
        get() = javaClass.name + radius.roundToInt()

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}
}