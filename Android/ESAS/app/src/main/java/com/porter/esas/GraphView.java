package com.porter.esas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;



public class GraphView extends View {

    public int width;
    public  int height;
    Context context;

    Paint paint;
    ArrayList<Survey> surveys;
    public GraphView(Context c, ArrayList<Survey> surveys)
    {
        super(c);
        context=c;
        this.surveys = surveys;
        paint = new Paint();
        invalidate();

    }


public void setSurveysList(ArrayList<Survey> surveys)
{
    this.surveys = surveys;
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
            //axes
        float sideMargin = 70.0f;
        paint.setStrokeWidth(4.0f);
            canvas.drawLine(sideMargin, (float)(height-sideMargin), (float)(width-sideMargin), (float)(height-sideMargin), paint);
            canvas.drawLine(sideMargin,sideMargin,sideMargin, (float)(height-sideMargin), paint);
            //labels
            paint.setTextSize(25.0f);
            canvas.drawText("Date", width/2, (float)(height-sideMargin+60.0), paint);
             float lineStartX =sideMargin;

            int numPoints = surveys.size();
            float segmentLength = (width-(sideMargin*2))/(numPoints+1);
            double heightScale = (height-sideMargin*2)*0.90;//amount of area to actually use
            for(int category =0; category<Survey.NUM_SURVEY_FIELDS; category++)
            {
                Resources res = getResources();
                int color = res.getColor(getResources().getIdentifier("color"+category, "color", context.getPackageName()));
                paint.setColor(color);
                float lineX = lineStartX;
                float prevLineY = (float) (height-sideMargin);
                for(int surveyIndex = numPoints-1; surveyIndex>-1; surveyIndex--)
                  {
                      paint.setStrokeWidth(3.0f);
                      int surveyVal = surveys.get(surveyIndex).getSurveyValues()[category];
                      float newY = (float) (height-sideMargin-surveyVal/10.0*heightScale);
                      canvas.drawLine(lineX,prevLineY,lineX+segmentLength,newY,paint);
                        lineX = lineX + segmentLength;
                      prevLineY =newY;
                  }
            }

    }
    public void reDraw()
    {

        invalidate();
    }
    public void cleanUp()
    {

        System.gc();
        Runtime.getRuntime().gc();
    }
}



