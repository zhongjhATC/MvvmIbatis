package com.zhongjh.mvvmibatis.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;

/**
 * 这是兼容处理了Glide圆角和ImageView的centerCrop
 *
 * @author zhongjh
 * @date 2022/4/6
 */
public class GlideRoundTransform extends BitmapTransformation {

    private final float radius;
    private boolean isLeftTop = true, isRightTop = true, isLeftBottom = true, isRightBottom = true;

    public GlideRoundTransform() {
        this(4);
    }

    /**
     * 全圆角
     *
     * @param dp 圆角幅度
     */
    public GlideRoundTransform(int dp) {
        super();
        radius = Resources.getSystem().getDisplayMetrics().density * dp;
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
    public GlideRoundTransform(int dp, boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom) {
        super();
        radius = Resources.getSystem().getDisplayMetrics().density * dp;
        isLeftTop = leftTop;
        isRightTop = rightTop;
        isLeftBottom = leftBottom;
        isRightBottom = rightBottom;
    }

    @Override
    protected Bitmap transform(@NotNull BitmapPool pool, @NotNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
        return roundCrop(pool, bitmap);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        // 如果都是需要圆角则不需要下面的判断
        if (!isLeftTop || !isRightTop || !isLeftBottom || !isRightBottom) {
            // 左上角圆角
            if (!isLeftTop) {
                canvas.drawRect(0, 0, radius, radius, paint);
            }
            // 右上角圆角
            if (!isRightTop) {
                canvas.drawRect(canvas.getWidth() - radius, 0, canvas.getWidth(), radius, paint);
            }
            // 左下角圆角
            if (!isLeftBottom) {
                canvas.drawRect(0, canvas.getHeight() - radius, radius, canvas.getHeight(), paint);
            }
            // 右下角圆角
            if (!isRightBottom) {
                canvas.drawRect(canvas.getWidth() - radius, canvas.getHeight() - radius, canvas.getWidth(), canvas.getHeight(), paint);
            }
        }

        return result;
    }

    public String getId() {
        return getClass().getName() + Math.round(radius);
    }

    @Override
    public void updateDiskCacheKey(@NotNull MessageDigest messageDigest) {

    }

}
