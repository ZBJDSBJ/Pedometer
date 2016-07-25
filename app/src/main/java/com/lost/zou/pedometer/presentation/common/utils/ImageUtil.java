package com.lost.zou.pedometer.presentation.common.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

/**
 */
public class ImageUtil {
    /**
     * 通过传入的大小返回对应的图片(缩放/剪切等操作)
     *
     * @param bitmap    要缩放的图片
     * @param newWidth  要缩放的宽度
     * @param newHeight 要缩放的高度
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        if (bitmap == null) {
            return bitmap;
        }
        //获取Bitmap宽度
        int width = bitmap.getWidth();
        //获取Bitmap高度
        int height = bitmap.getHeight();
        if (width == newWidth && height == newHeight) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        //参考Bitmap高度获取缩放比例(高度)
        float scale = newHeight / (float) height;
        //如果缩放出来的宽度少于需要的宽度,则参照宽度比例缩放Bitmap.
        if (width * scale < newWidth) {
            scale = newWidth / (float) width;
        }
        //设置缩放比例
        matrix.postScale(scale, scale);
        if (width * scale - newWidth > 0 || height * scale - newHeight > 0) {
            // 缩放并截取图片
            return Bitmap.createBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true), width * scale - newWidth > 0 ? (int) (width * scale - newWidth) / 2 : 0, height * scale - newHeight > 0 ? (int) (height * scale - newHeight) / 2 : 0, newWidth, newHeight);
        }
        //缩放图片
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 获得圆角图片的方法
     *
     * @param bitmap
     * @param roundPx
     * @return , int newWidth, int newHeight
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        if (bitmap == null) {
            return null;
        }
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        ;
        canvas.drawRoundRect(rect, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return target;
    }
}
