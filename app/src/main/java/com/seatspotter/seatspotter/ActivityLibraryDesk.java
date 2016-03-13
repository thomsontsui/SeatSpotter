package com.seatspotter.seatspotter;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


public class ActivityLibraryDesk extends ActionBarActivity {

    int deskHubID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Desk View");
        setContentView(R.layout.activity_library_desk);

        Intent intent = getIntent();

        if (intent.getStringExtra(ViewMap.DESK_HUB_ID) != null){
            deskHubID = Integer.parseInt(intent.getStringExtra(ViewMap.DESK_HUB_ID));
        } else {
            deskHubID = Integer.parseInt(intent.getStringExtra(ViewMapB.DESK_HUB_ID));
        }

        System.out.println("DeskHubID: " + String.valueOf(deskHubID));

        //Legend image
        ImageView deskLegend = (ImageView)findViewById(R.id.deskLegend);
        deskLegend.setImageResource(R.drawable.desklegend2);

        updateDeskStatus();

        //Timer
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 5000);
    }

    public void onPollButtonClick(View view){
        updateDeskStatus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library_desk, menu);
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

    public void updateDeskStatus(){
        ViewDesk deskBlockView = (ViewDesk) findViewById(R.id.canvasDesk);
        deskBlockView.updateDeskStatus(deskHubID);
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            // When you need to modify a UI element, do so on the UI thread.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Update desk status
                    updateDeskStatus();
                }
            });
        }
    };
}
