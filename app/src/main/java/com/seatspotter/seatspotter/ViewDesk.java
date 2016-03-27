package com.seatspotter.seatspotter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewDesk extends View {

    private Rect rectangle;
    private Paint paint = new Paint();
    private Paint statusPaint;

    String responseResult = "";
    List<Desk> desks;

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
        paint.setStrokeWidth(3);

        //Drawing desks
        for (Desk d : desks){
            //Initializing desk block
            //Rect desk = new Rect(canvas.getWidth()*d.x/100, canvas.getHeight()*d.y/100, canvas.getWidth()*d.x/100 + canvas.getWidth()*d.xLength/100, canvas.getHeight()*d.y/100 + canvas.getHeight()*d.yLength/100);
            //Rect desk = new Rect(d.x, d.y, d.x + d.xLength, d.y + d.yLength);

            int length;
            Rect desk;

            if (d.xLength > d.yLength){
                length = d.yLength;
                desk = new Rect(canvas.getWidth()*d.x/100+canvas.getWidth()/2-d.xLength/2, canvas.getHeight()*d.y/100, canvas.getWidth()*d.x/100 + canvas.getWidth()*length/100+canvas.getWidth()/2, canvas.getHeight()*d.y/100 + canvas.getHeight()*length/100);

            } else {
                length = d.xLength;
                //desk = new Rect(canvas.getWidth()*d.x/100, canvas.getHeight()*d.y/100+canvas.getHeight()/2-d.yLength/2, canvas.getWidth()*d.x/100 + canvas.getWidth()*length/100, canvas.getHeight()*d.y/100 + canvas.getHeight()*length/100+canvas.getHeight()/2);
                desk = new Rect(canvas.getWidth()*d.x/100, canvas.getHeight()*d.y/100, canvas.getWidth()*d.x/100 + canvas.getWidth()*d.xLength/100, canvas.getHeight()*d.y/100 + canvas.getHeight()*d.yLength/100);
            }



            // set the paint colour for the filling
            statusPaint = new Paint();

            if (d.deskState == 1){
                statusPaint.setColor(Color.YELLOW);
            } else if (d.deskState == 2){
                statusPaint.setColor(Color.RED);
            } else {
                statusPaint.setColor(Color.GREEN);
            }
            statusPaint.setStyle(Paint.Style.FILL);

            canvas.drawRect(desk, statusPaint);
            canvas.drawRect(desk, paint);
        }
    }

    public void updateDeskStatus(int deskHubID){
        //Call RestAPI
        String urlString = "http://seatspotter.azurewebsites.net/seatspotter/webapi/deskhubs/" +String.valueOf(deskHubID)+ "/desks";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlString, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                responseResult = response;
                System.out.println("String: " + response);
            }
        });

        desks = new ArrayList<Desk>();

        if (responseResult != ""){
            try {
                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = new JSONArray(responseResult);

                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int deskID = Integer.parseInt(jsonObject.optString("deskId").toString());
                    int hubID = Integer.parseInt(jsonObject.optString("deskHubId").toString());
                    int deskState = Integer.parseInt(jsonObject.optString("deskState").toString());
                    int x = Integer.parseInt(jsonObject.optString("coordinateX").toString());
                    int y = Integer.parseInt(jsonObject.optString("coordinateY").toString());
                    int xLength = Integer.parseInt(jsonObject.optString("lengthX").toString());
                    int yLength = Integer.parseInt(jsonObject.optString("lengthY").toString());

                    Desk desk = new Desk(deskID, hubID, deskState, x, y, xLength, yLength);
                    desks.add(desk);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        invalidate();
    }
}
