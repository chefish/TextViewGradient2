package com.fish.textviewgradient2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自己draw view并且用渐变色来渲染，从左到右的渐变
 * Created by fish on 2019/3/13.
 * yuxm_zju@aliyun.com
 */
public class CustomView extends View {
    public CustomView(Context context) {
        super(context);
        init(context);
    }

    private Paint paintOuterText;

    private void init(Context context) {

        paintOuterText = new Paint();
        //不要用setcolor冲突的
//        paintOuterText.setColor(Color.RED);
        paintOuterText.setAntiAlias(true);
        paintOuterText.setTextSize(100);
        paintOuterText.setTextAlign(Paint.Align.CENTER);


        Log.d("AAA", "width is " + width);


        String text = "2019";

        Rect rect = new Rect();
        paintOuterText.getTextBounds(text, 0, text.length(), rect);


        width = paintOuterText.measureText("2019");
        LinearGradient mLinearGradient = new LinearGradient(0, 0, width, 0, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
        paintOuterText.setShader(mLinearGradient);
    }

    float width;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        LinearGradient mLinearGradient = new LinearGradient(0,0,getMeasuredWidth(),0,new int[]{Color.RED,Color.YELLOW},new float[]{1.0f,1.0f}, Shader.TileMode.CLAMP);

    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //这里的x 居然用的是中间的点
        canvas.drawText("2019", width/2, 150, paintOuterText);
        canvas.drawLine(0, 150, width, 150, paintOuterText);
    }

}
