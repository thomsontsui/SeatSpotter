package com.seatspotter.seatspotter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class ActivityLibraryMapB extends ActionBarActivity {

    int floorID;
    String floorName;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Map View");
        setContentView(R.layout.activity_library_mapb);

        Intent intent = getIntent();

        //Getting the floor from the list View
        floorName = intent.getStringExtra(ActivityLibraryFloor.LIBRARY_FLOOR);
        TextView e = (TextView)findViewById(R.id.libraryFloor);
        e.setText(floorName);
        if (intent.getStringExtra(ActivityLibraryFloor.FLOOR_ID) != null){
            floorID = Integer.parseInt(intent.getStringExtra(ActivityLibraryFloor.FLOOR_ID));
        } else {
            floorID = 4;
        }

        //Legend image
        ImageView mapLegend = (ImageView)findViewById(R.id.mapLegend);
        mapLegend.setImageResource(R.drawable.maplegend2);

        updateMapStatus();

        //Timer
        timer = new Timer();
        timer.schedule(timerTask, 0, 5000);
    }

    public void onTwoSeatButtonClick(View view){
        ViewMapB mapView = (ViewMapB) findViewById(R.id.canvasMap);
        mapView.searchTwoSeatBlock();
    }

    public void onThreeSeatButtonClick(View view){
        ViewMapB mapView = (ViewMapB) findViewById(R.id.canvasMap);
        mapView.searchThreeSeatBlock();
    }

    public void onFourSeatButtonClick(View view){
        ViewMapB mapView = (ViewMapB) findViewById(R.id.canvasMap);
        mapView.searchFourSeatBlock();
    }

    public void onFiveSeatButtonClick(View view){
        ViewMapB mapView = (ViewMapB) findViewById(R.id.canvasMap);
        mapView.searchFiveSeatBlock();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateMapStatus(){
        ViewMapB mapView = (ViewMapB) findViewById(R.id.canvasMap);
        mapView.updateMapStatus(floorID);
    }

    public void updateMapStatus(View view){
        updateMapStatus();
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            // When you need to modify a UI element, do so on the UI thread.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Update desk status
                    updateMapStatus();
                }
            });
        }
    };
}


