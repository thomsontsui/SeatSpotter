package com.seatspotter.seatspotter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class ActivityLibraryFloor extends ActionBarActivity {

    public final static String LIBRARY_FLOOR = "com.seatspotter.seatspotter.LIBRARYFLOOR";
    public final static String FLOOR_ID = "com.seatspotter.seatspotter.FLOORID";

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> floorCollection;
    ExpandableListView expFloorList;
    String responseResult = "";
    ProgressDialog pd;
    List <Floor> listFloors;
    //int libraryID;
    String libraryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Library Floors");
        setContentView(R.layout.activity_library_floor);
        Intent intent = getIntent();

        //Progress Dialog
        pd= new ProgressDialog(this);
        pd.setTitle("Loading list of floors");
        pd.setMessage("Please wait while loading...");
        pd.setCancelable(false);
        pd.show();

        //Getting the library from the list View
        libraryName = intent.getStringExtra(ActivityLibraryList.LIBRARY_NAME);
        TextView e = (TextView)findViewById(R.id.libraryName);
        e.setText(libraryName);
        //libraryID = Integer.parseInt(intent.getStringExtra(ActivityLibraryList.LIBRARY_ID));

        //Timer
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 10000);

        updateFloorStatus();
    }

    private void createGroupList(List<Floor> floors) {
        groupList = new ArrayList<String>();

        if (floors.isEmpty()){
            groupList.add("No floors were found");
        } else {
            for (Floor floor : floors) {
                groupList.add(floor.floorLevel);
            }
            pd.dismiss();
        }
    }

    private void createCollection(List<Floor> floors) {
        if (floors.isEmpty()){
            childList = new ArrayList<String>();
            childList.add("No additional information");
            floorCollection = new LinkedHashMap<String, List<String>>();
            floorCollection.put(groupList.get(0), childList);
        } else {
            // preparing floor collection(child)
            for (Floor floor : floors) {
                String[] floorInfo = {"Total Desks: " + String.valueOf(floor.totalDesks), "Empty Desks: " + String.valueOf(floor.emptyDesks),
                        "Unknown State: " + String.valueOf(floor.unknownState)};

                floorCollection = new LinkedHashMap<String, List<String>>();

                for (String f : groupList) {
                    if (f.equals(floor.floorLevel)) {
                        loadChild(floorInfo);
                    }
                    floorCollection.put(f, childList);
                }
            }
        }
    }

    private void loadChild(String[] floorInfo) {
        childList = new ArrayList<String>();
        for (String info : floorInfo)
            childList.add(info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library_floor, menu);
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

    public void updateFloorStatus() {
        //Call RestAPI
        //String urlString = "http://seatspotter.azurewebsites.net/seatspotter/webapi/libraries/" +String.valueOf(libraryID)+ "/floors";
        String urlString = "http://seatspotter.azurewebsites.net/seatspotter/webapi/libraries/3/floors";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlString, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                responseResult = response;
                System.out.println("String: " + response);
            }
        });

        listFloors = new ArrayList<Floor>();

        if (responseResult != ""){
            try {
                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = new JSONArray(responseResult);

                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int id = Integer.parseInt(jsonObject.optString("floorId").toString());
                    String floorLevel = jsonObject.optString("floorLevel").toString();
                    int totalDesks = Integer.parseInt(jsonObject.optString("totalDesks").toString());
                    int emptyDesks = Integer.parseInt(jsonObject.optString("emptyDesks").toString());
                    int unknownState = Integer.parseInt(jsonObject.optString("unknownState").toString());

                    Floor floor = new Floor(id, floorLevel, totalDesks, emptyDesks, unknownState);
                    listFloors.add(floor);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        createGroupList(listFloors);
        createCollection(listFloors);


        expFloorList = (ExpandableListView) findViewById(R.id.floorList);

        final MyLibraryListAdapter expListAdapter = new MyLibraryListAdapter(
                this, groupList, floorCollection){
            @Override
            public void OnIndicatorClick(boolean isExpanded, int position) {
                if(isExpanded){
                    expFloorList.collapseGroup(position);
                }else{
                    expFloorList.expandGroup(position);
                }
            }
        };
        expFloorList.setAdapter(expListAdapter);

        expFloorList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                int floorID = 0;
                for (Floor floor : listFloors){
                    if (floor.floorLevel.contains(groupList.get(groupPosition).toString())){
                        floorID = floor.id;
                    }
                }

                //Floor Plan B
                if (floorID == 4){
                    Intent intent = new Intent(ActivityLibraryFloor.this, ActivityLibraryMapB.class);

                    String libraryFloor = ((TextView) v.findViewById(R.id.heading)).getText().toString();
                    intent.putExtra(LIBRARY_FLOOR, libraryFloor);
                    intent.putExtra(FLOOR_ID, String.valueOf(floorID));

                    timerTask.cancel();

                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ActivityLibraryFloor.this, ActivityLibraryMap.class);

                    String libraryFloor = ((TextView) v.findViewById(R.id.heading)).getText().toString();
                    intent.putExtra(LIBRARY_FLOOR, libraryFloor);
                    intent.putExtra(FLOOR_ID, String.valueOf(floorID));

                    timerTask.cancel();

                    startActivity(intent);
                }
                return true;
            }
        });
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            // When you need to modify a UI element, do so on the UI thread.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Update desk status
                    updateFloorStatus();
                }
            });
        }
    };
}
