package com.seatspotter.seatspotter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ViewMap extends View{
    private Rect rectangle;
    private Rect desk1;
    private Paint paint;
    private Paint statusPaint;
    private Paint textPaint;

    private int[] xPosition;
    private int[] yPosition;
    private int[] numAvailDesks;
    private int[] status;

    public ViewMap(Context context) {
        super(context);
    }

    public ViewMap(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas){
        // create the Paint and set its colour for the border
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setTextSize(10);
        textPaint.setTextAlign(Paint.Align.CENTER);

        //Outside rectangle
        rectangle = new Rect(canvas.getWidth()/10, canvas.getHeight()/10, canvas.getWidth()*9/10, canvas.getHeight()/2);

        canvas.drawRect(rectangle, paint);

        //Draw the left/right lines
        canvas.drawLine(canvas.getWidth() / 11, canvas.getHeight() / 4, canvas.getWidth() / 9, canvas.getHeight() / 4, paint);
        canvas.drawLine(canvas.getWidth() / 11, canvas.getHeight() * 7 / 20, canvas.getWidth() / 9, canvas.getHeight() * 7 / 20, paint);
        canvas.drawLine(canvas.getWidth() * 10 / 11, canvas.getHeight() / 4, canvas.getWidth() * 8 / 9, canvas.getHeight() / 4, paint);
        canvas.drawLine(canvas.getWidth() * 10 / 11, canvas.getHeight() * 7 / 20, canvas.getWidth() * 8 / 9, canvas.getHeight() * 7 / 20, paint);

        //Text
        canvas.drawText("To MC", canvas.getWidth() / 8, canvas.getHeight() * 6 / 20, paint);
        canvas.drawText("To Ring Road", canvas.getWidth() * 75 / 100, canvas.getHeight() * 6 / 20, paint);
        canvas.drawText("To DC Library", canvas.getWidth() * 9 / 20, canvas.getHeight() * 47 / 100, paint);

        //Draw the bottom lines
        canvas.drawLine(canvas.getWidth() * 3 / 10, canvas.getHeight() * 49 / 100, canvas.getWidth() * 3 / 10, canvas.getHeight() * 51 / 100, paint);
        canvas.drawLine(canvas.getWidth() * 7 / 10, canvas.getHeight() * 49 / 100, canvas.getWidth() * 7 / 10, canvas.getHeight() * 51 / 100, paint);

        //Drawing desk block
        desk1 = new Rect(canvas.getWidth()/4, canvas.getHeight()/5, canvas.getWidth()/2, canvas.getHeight()/3);

        // set the paint colour for the filling
        statusPaint = new Paint();
        statusPaint.setColor(Color.GREEN);
        statusPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(desk1, statusPaint);

        canvas.drawRect(desk1, paint);

        //Setting up text inside desk block
        int desk1Width = desk1.width();
        String text = "Number of available desk: 4";        // Static for now, will have to update the number to work with numAvailDesks variable
        int numOfChars = textPaint.breakText(text, true, desk1Width, null);
        int start = (text.length()-numOfChars)/2;

        canvas.drawText(text, start, start+numOfChars, desk1.exactCenterX(), desk1.exactCenterY(), textPaint);

        //Hardcoded search 4 desk block
        if (status != null){
            //Drawing blue rectangle outlining desk block
            desk1 = new Rect(canvas.getWidth()/4 - 5, canvas.getHeight()/5 - 5, canvas.getWidth()/2 + 5, canvas.getHeight()/3 + 5);

            // set the paint colour for the outline
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            canvas.drawRect(desk1, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int touchX = (int)event.getX();
        int touchY = (int)event.getY();
        Context context = getContext();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("Touching down!");
                if(desk1.contains(touchX,touchY)) {
                    System.out.println("Touched Rectangle, start activity.");
                    Intent intent = new Intent(context, ActivityLibraryDesk.class);
                    context.startActivity(intent);
                }
            break;
            case MotionEvent.ACTION_UP:
                System.out.println("Touching up!");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("Sliding your finger around on the screen.");
                break;
        }
        return true;
    }

    public void searchFourSeatBlock(){
        //Static Data before database is created
        DeskBlock designFairDemoFloor1DeskBlock1 = new DeskBlock(0, 4, 227, 131);

        DeskBlock[] deskBlocks = new DeskBlock[] {designFairDemoFloor1DeskBlock1};

        designFairDemoFloor1DeskBlock1.setStatus(2);

        xPosition = new int[deskBlocks.length];
        yPosition = new int[deskBlocks.length];
        status = new int[deskBlocks.length];

        for (int i = 0; i < deskBlocks.length; i++) {
            xPosition[i] = deskBlocks[i].getX();
            yPosition[i] = deskBlocks[i].getY();
            status[i] = deskBlocks[i].getStatus();
        }

        invalidate();
    }
}
