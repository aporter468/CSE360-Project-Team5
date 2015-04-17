package com.porter.esas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class GraphView extends View {

    public int width;
    public  int height;
    //private Bitmap  mBitmap;
    Context context;
    private int graphMode;//-1 = all foods; 0-5 are food categories; 6=height, 7=weight, 8= bmi

    public  final int NUM_FACTS = 6;

    //sizes- ratios to 1, for touch scaling



    Paint paint;

    public GraphView(Context c)
    {
        super(c);
        context=c;
        Log.e("mylog","make graph");
        paint = new Paint();
        invalidate();

    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w>0 && h>0)
        {


        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLUE);

        int height = getHeight();
        int width = getWidth();

       paint.setColor(Color.BLACK);
            canvas.drawRect((float)1,(float)(20),(float)( 3), (float)50, paint);
    Log.e("mylog","paint graph");
        /*    //axes
            paint.setStrokeWidth(4.0f);
            canvas.drawLine(sideMargin, (float)(height-sideMargin), (float)(width-sideMargin), (float)(height-sideMargin), paint);
            canvas.drawLine(sideMargin,sideMargin,sideMargin, (float)(height-sideMargin), paint);
            //labels
            paint.setTextSize(30.0f);
            canvas.drawText("Date", width/2, (float)(height-sideMargin+60.0), paint);
            float barStartX =sideMargin;
            float barX = barStartX;
            float barWidth = (width-(sideMargin*2))/(numBars+1);
            float barGap = barWidth/4;
            barWidth-=barGap;
            double heightScale = (height-sideMargin*2)*0.90;//amount of area to actually use
*/

    }
public void reDraw()
{
    invalidate();
}
    public void cleanUp()
    {
        //	mBitmap.recycle();
        //	mBitmap = null;
        System.gc();
        Runtime.getRuntime().gc();
    }
}



