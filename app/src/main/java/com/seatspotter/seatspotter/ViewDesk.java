package com.seatspotter.seatspotter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ViewDesk extends View {

    private Rect rectangle;
    private Paint paint = new Paint();
    private Paint statusPaint = new Paint();
    //private boolean onFirstDraw = true;
    private int[] xPosition;
    private int[] yPosition;
    private int[] status;

    public ViewDesk(Context context) {
        super(context);
    }

    public ViewDesk(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas){

        //Create the Paint and its colour
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        //outside rectangle
        rectangle = new Rect(canvas.getWidth() / 10, 10, canvas.getWidth() * 9 / 10, canvas.getHeight() / 2);

        canvas.drawRect(rectangle, paint);

        canvas.drawLine(canvas.getWidth() / 2, 10, canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
        canvas.drawLine(canvas.getWidth() / 10, canvas.getHeight() / 4, canvas.getWidth() * 9 / 10, canvas.getHeight() / 4, paint);

        //if (onFirstDraw) {
        statusPaint.setColor(Color.RED);
           // onFirstDraw = false;

        //Need to circles one for outline, one for fill
        canvas.drawCircle(canvas.getWidth() * 3 / 10, canvas.getHeight() / 8, 50, statusPaint);
        canvas.drawCircle(canvas.getWidth() * 3 / 10, canvas.getHeight() / 8, 50, paint);

        canvas.drawCircle(canvas.getWidth() * 7 / 10, canvas.getHeight() / 8, 50, statusPaint);
        canvas.drawCircle(canvas.getWidth() * 7 / 10, canvas.getHeight() / 8, 50, paint);

        canvas.drawCircle(canvas.getWidth() * 3 / 10, canvas.getHeight() * 3 / 8, 50, statusPaint);
        canvas.drawCircle(canvas.getWidth() * 3 / 10, canvas.getHeight() * 3 / 8, 50, paint);

        canvas.drawCircle(canvas.getWidth() * 7 / 10, canvas.getHeight() * 3 / 8, 50, statusPaint);
        canvas.drawCircle(canvas.getWidth() * 7 / 10, canvas.getHeight() * 3 / 8, 50, paint);
        //}

        if (status != null) {
            for (int i = 0; i < status.length; i++) {
                switch (status[i]) {
                    case 0:
                        statusPaint.setColor(Color.RED);
                        canvas.drawCircle(xPosition[i], yPosition[i], 50, statusPaint);
                        canvas.drawCircle(xPosition[i], yPosition[i], 50, paint);
                        break;
                    case 1:
                        statusPaint.setColor(Color.YELLOW);
                        canvas.drawCircle(xPosition[i], yPosition[i], 50, statusPaint);
                        canvas.drawCircle(xPosition[i], yPosition[i], 50, paint);
                        break;
                    case 2:
                        statusPaint.setColor(Color.GREEN);
                        canvas.drawCircle(xPosition[i], yPosition[i], 50, statusPaint);
                        canvas.drawCircle(xPosition[i], yPosition[i], 50, paint);
                        break;
                    default:
                        statusPaint.setColor(Color.RED);
                        canvas.drawCircle(xPosition[i], yPosition[i], 50, statusPaint);
                        canvas.drawCircle(xPosition[i], yPosition[i], 50, paint);
                }
            }
        }

    }

    public void updateDeskStatus(){
        //Static Data before database is created
        Desk designFairDemoFloor1Desk1 = new Desk(0, 0, 227, 131);
        Desk designFairDemoFloor1Desk2 = new Desk(1, 0, 530, 131);
        Desk designFairDemoFloor1Desk3 = new Desk(0, 0, 227, 394);
        Desk designFairDemoFloor1Desk4 = new Desk(1, 0, 530, 394);

        Desk[] desks = new Desk[] {designFairDemoFloor1Desk1, designFairDemoFloor1Desk2, designFairDemoFloor1Desk3, designFairDemoFloor1Desk4};

        designFairDemoFloor1Desk1.setStatus(0);
        designFairDemoFloor1Desk2.setStatus(1);
        designFairDemoFloor1Desk3.setStatus(2);
        designFairDemoFloor1Desk4.setStatus(1);

        xPosition = new int[desks.length];
        yPosition = new int[desks.length];
        status = new int[desks.length];

        for (int i = 0; i < desks.length; i++) {
            xPosition[i] = desks[i].getX();
            yPosition[i] = desks[i].getY();
            status[i] = desks[i].getStatus();
        }

        invalidate();
    }
}