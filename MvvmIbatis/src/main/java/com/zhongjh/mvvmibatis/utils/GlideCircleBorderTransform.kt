package com.zhongjh.mvvmibatis.utils

import android.graphics.*
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import java.security.MessageDigest
import kotlin.math.min

/**
 * 圆形 + 描边效果
 * Glide 的图形变换可参考Glide 示例源码
 * [com.bumptech.glide.load.resource.bitmap.CenterCrop]
 * [com.bumptech.glide.load.resource.bitmap.BitmapTransformation]
 *
 * @author zhongjh
 * @date 2022/6/7
 */
class GlideCircleBorderTransform(private val borderWidth: Float, borderColor: Int) :
    BitmapTransformation() {
    private val mID = javaClass.name
    private val mBorderPaint: Paint = Paint()
    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        return circleCrop(pool, toTransform)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(mID.toByteArray(CHARSET))
    }

    override fun equals(other: Any?): Boolean {
        return other is GlideCircleBorderTransform
    }

    override fun hashCode(): Int {
        return mID.hashCode()
    }

    private fun circleCrop(bitmapPool: BitmapPool, source: Bitmap): Bitmap {
        val size = min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        val square = Bitmap.createBitmap(source, x, y, size, size)
        val result = bitmapPool[size, size, Bitmap.Config.ARGB_8888]

        // 画图
        val canvas = Canvas(result)
        val paint = Paint()
        // 设置 Shader
        paint.shader =
            BitmapShader(square, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val radius = size / 2f
        // 绘制一个圆
        canvas.drawCircle(radius, radius, radius, paint)

        // 描边，注意：避免出现描边被屏幕边缘裁掉
        val borderRadius = radius - borderWidth / 2
        // 画边框
        canvas.drawCircle(radius, radius, borderRadius, mBorderPaint)
        return result
    }

    init {
        mBorderPaint.color = borderColor
        mBorderPaint.style = Paint.Style.STROKE
        mBorderPaint.isAntiAlias = true
        mBorderPaint.strokeWidth = borderWidth
        mBorderPaint.isDither = true
    }
}