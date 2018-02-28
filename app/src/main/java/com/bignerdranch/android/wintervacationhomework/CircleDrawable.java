package com.bignerdranch.android.wintervacationhomework;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class CircleDrawable extends Drawable {

    private Paint mPaint;
    private int mWidth;
    private Bitmap mBitmap;
    private Matrix matrix;

    public CircleDrawable(Bitmap bitmap){
        this.mBitmap=bitmap;
        mWidth=70;
        matrix = new Matrix();
        BitmapShader bitmapShader=new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if(bitmap.getWidth()!=70){
            scale = 70*1.0f/bitmap.getWidth();
        }
        matrix.setScale(scale,scale);
        bitmapShader.setLocalMatrix(matrix);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(bitmapShader);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(mWidth/2,mWidth/2,mWidth/2,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicHeight(){
        return mWidth;
    }

    @Override
    public int getIntrinsicWidth(){
        return mWidth;
    }

}
