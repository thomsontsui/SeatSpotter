package com.seatspotter.seatspotter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewMapB extends View{

    public final static String DESK_HUB_ID = "com.seatspotter.seatspotter.DESKHUBID";

    private Rect rectangle;
    private List<Rect> rectangleDeskBlocks;
    private Paint paint;
    private Paint statusPaint;
    private Paint textPaint;

    List<DeskBlock> deskBlocks;
    String responseResult = "";

    int searchCount = 0;

    public ViewMapB(Context context) {
        super(context);
    }

    public ViewMapB(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas){
        //Text Paint
        textPaint = new Paint();
        textPaint.setTextSize(15);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        // create the Paint and set its colour for the border
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        rectangleDeskBlocks = new ArrayList<Rect>();

        //Drawing desk block hubs
        for (DeskBlock db : deskBlocks){
            //Initializing desk block
            Rect deskBlock = new Rect(canvas.getWidth()*db.x/100, canvas.getHeight()*db.y/100, canvas.getWidth()*db.x/100 + canvas.getWidth()*db.xLength/100, canvas.getHeight()*db.y/100 + canvas.getHeight()*db.yLength/100);
            rectangleDeskBlocks.add(deskBlock);

            // set the paint colour for the filling
            statusPaint = new Paint();

            if ((float)db.emptyDesks / (float)db.totalDesks >= 0.5){
                statusPaint.setColor(Color.GREEN);
            } else if ((float)db.emptyDesks / (float)db.totalDesks <= 0.25){
                statusPaint.setColor(Color.RED);
            } else {
                statusPaint.setColor(Color.YELLOW);
            }
            statusPaint.setStyle(Paint.Style.FILL);

            canvas.drawRect(deskBlock, statusPaint);
            canvas.drawRect(deskBlock, paint);

            //Setting up text inside desk block
            String text = String.valueOf(db.emptyDesks);
            canvas.drawText(text, deskBlock.exactCenterX(), deskBlock.exactCenterY(), textPaint);
        }

        //Searching Functionality
        if (searchCount > 0){
            //Drawing blue rectangle outlining desk block
            for (DeskBlock db : deskBlocks){
                if (db.emptyDesks >= searchCount){
                    Rect outline = new Rect(canvas.getWidth()*db.x/100-5, canvas.getHeight()*db.y/100-5, canvas.getWidth()*db.x/100 + canvas.getWidth()*db.xLength/100+5, canvas.getHeight()*db.y/100 + canvas.getHeight()*db.yLength/100+5);

                    // set the paint colour for the outline
                    paint.setColor(Color.BLUE);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(3);
                    canvas.drawRect(outline, paint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int touchX = (int)event.getX();
        int touchY = (int)event.getY();
        Context context = getContext();

        //Default Floor Plan B Desk Hub ID
        int deskHubID = 4;

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("Touching down!");

                for (Rect rect : rectangleDeskBlocks){
                    if(rect.contains(touchX, touchY)) {
                        //Finding the deskBlock ID from the x,y coordinates of the rectangle
                        for (DeskBlock db : deskBlocks){
                            if (rect.left == 758*db.x/100 && rect.top == 978*db.y/100){
                                deskHubID = db.id;
                            }
                        }

                        System.out.println("Touched Rectangle, start activity.");
                        Intent intent = new Intent(context, ActivityLibraryDesk.class);
                        intent.putExtra(DESK_HUB_ID, String.valueOf(deskHubID));

                        context.startActivity(intent);
                        break;
                    }
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

    public void updateMapStatus(int floorID){
        //Call RestAPI
        String urlString = "http://seatspotter.azurewebsites.net/seatspotter/webapi/floors/" +String.valueOf(floorID)+ "/deskhubs";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlString, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                responseResult = response;
                System.out.println("String: " + response);
            }
        });

        deskBlocks = new ArrayList<DeskBlock>();

        if (responseResult != ""){
            try {
                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = new JSONArray(responseResult);

                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int deskHubID = Integer.parseInt(jsonObject.optString("deskHubId").toString());
                    int totalDesks = Integer.parseInt(jsonObject.optString("totalDesks").toString());
                    int emptyDesks = Integer.parseInt(jsonObject.optString("emptyDesks").toString());
                    int x = Integer.parseInt(jsonObject.optString("coordinateX").toString());
                    int y = Integer.parseInt(jsonObject.optString("coordinateY").toString());
                    int xLength = Integer.parseInt(jsonObject.optString("lengthX").toString());
                    int yLength = Integer.parseInt(jsonObject.optString("lengthY").toString());
                    int unknownState = Integer.parseInt(jsonObject.optString("unknownState").toString());

                    DeskBlock db = new DeskBlock(deskHubID, emptyDesks, x, y, xLength, yLength, floorID, totalDesks, unknownState);
                    deskBlocks.add(db);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        invalidate();
    }

    public void searchTwoSeatBlock(){
        searchCount = 2;
        invalidate();
    }

    public void searchThreeSeatBlock(){
        searchCount = 3;
        invalidate();
    }

    public void searchFourSeatBlock(){
        searchCount = 4;
        invalidate();
    }

    public void searchFiveSeatBlock(){
        searchCount = 5;
        invalidate();
    }
}
