package com.fish.textviewgradient2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自己draw view并且用渐变色来渲染，从上到下的渐变
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
        paintOuterText.setColor(Color.RED);
        paintOuterText.setAntiAlias(true);
        paintOuterText.setTextSize(100);
        paintOuterText.setTextAlign(Paint.Align.CENTER);
//        paintOuterText.setShadowLayer(8,0,-6,Color.BLACK);


        Log.d("AAA", "width is " + width);


        String text = getText();

        Rect rect = new Rect();
        paintOuterText.getTextBounds(text, 0, text.length(), rect);
        Rect bounds = new Rect();
        paintOuterText.getTextBounds(getText(), 0, 1, bounds);
//        LinearGradient mLinearGradient = new LinearGradient(0, 0, width, 0, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);

        // 77 - 150
        LinearGradient mLinearGradient = new LinearGradient(0, baseY() -bounds.height(), 0, baseY(), Color.RED, Color.GREEN, Shader.TileMode.CLAMP);

        width = paintOuterText.measureText(getText());
        paintOuterText.setShader(mLinearGradient);
    }

    @NonNull
    private String getText() {
        return "2019";
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
        //y是基线的点
        canvas.drawText(getText(), width / 2, baseY(), paintOuterText);
        canvas.drawLine(0, baseY(), width, baseY(), paintOuterText);
    }

    private int baseY() {
        return 150;
    }

}
