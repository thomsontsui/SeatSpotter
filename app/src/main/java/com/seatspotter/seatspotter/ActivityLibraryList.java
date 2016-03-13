package com.seatspotter.seatspotter;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityLibraryList extends ActionBarActivity {

    public final static String LIBRARY_NAME = "com.seatspotter.seatspotter.LIBRARYNAME";
    public final static String LIBRARY_ID = "com.seatspotter.seatspotter.LIBRARYID";

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> libraryCollection;
    ExpandableListView expLibraryList;
    String responseResult = "";
    ProgressDialog pd;
    List <Library> libs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Library List");
        setContentView(R.layout.activity_library_list);

        //Progress Dialog
        pd= new ProgressDialog(this);
        pd.setTitle("Loading list of libraries");
        pd.setMessage("Please wait while loading...");
        pd.setCancelable(false);
        pd.show();

        //Timer
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 10000);

        updateLibraryStatus();
    }

    private void createGroupList(List<Library> libraries) {
        groupList = new ArrayList<String>();

        if (libraries.isEmpty()){
            groupList.add("No Libraries were found");
        } else {
            for (Library lib : libraries) {
                groupList.add(lib.name);
            }
            pd.dismiss();
        }
    }

    private void createCollection(List<Library> libraries) {
        if (libraries.isEmpty()){
            childList = new ArrayList<String>();
            childList.add("No additional information");
            libraryCollection = new LinkedHashMap<String, List<String>>();
            libraryCollection.put(groupList.get(0), childList);
        } else {
            // preparing library collection(child)
            for (Library lib : libraries) {
                String[] libInfo = {"Total Desks: " + String.valueOf(lib.totalDesks), "Empty Desks: " + String.valueOf(lib.emptyDesks),
                        "Unknown State: " + String.valueOf(lib.unknownState)};

                libraryCollection = new LinkedHashMap<String, List<String>>();

                for (String library : groupList) {
                    if (library.equals(lib.name)) {
                        loadChild(libInfo);
                    }
                    libraryCollection.put(library, childList);
                }
            }
        }
    }

    private void loadChild(String[] libraryInfo) {
        childList = new ArrayList<String>();
        for (String info : libraryInfo)
            childList.add(info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library_list, menu);
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

    public void updateLibraryStatus() {
        //Call RestAPI
        String urlString = "http://seatspotter.azurewebsites.net/seatspotter/webapi/libraries";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlString, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                responseResult = response;
                System.out.println("String: " + response);
            }
        });

        libs = new ArrayList<Library>();

        if (responseResult != ""){
            try {
                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = new JSONArray(responseResult);

                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int id = Integer.parseInt(jsonObject.optString("libraryId").toString());
                    String name = jsonObject.optString("libraryName").toString();
                    int totalDesks = Integer.parseInt(jsonObject.optString("totalDesks").toString());
                    int emptyDesks = Integer.parseInt(jsonObject.optString("emptyDesks").toString());
                    int unknownState = Integer.parseInt(jsonObject.optString("unknownState").toString());

                    Library lib = new Library(id, name, totalDesks, emptyDesks, unknownState);
                    libs.add(lib);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        createGroupList(libs);
        createCollection(libs);


        expLibraryList = (ExpandableListView) findViewById(R.id.libraryList);

        final MyLibraryListAdapter expListAdapter = new MyLibraryListAdapter(
                this, groupList, libraryCollection){
            @Override
            public void OnIndicatorClick(boolean isExpanded, int position) {
                if(isExpanded){
                    expLibraryList.collapseGroup(position);
                }else{
                    expLibraryList.expandGroup(position);
                }
            }
        };
        expLibraryList.setAdapter(expListAdapter);

        expLibraryList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                int libraryID = 0;
                for (Library lib : libs){
                    if (lib.name.contains(groupList.get(groupPosition).toString())){
                        libraryID = lib.id;
                    }
                }

                if (groupList.get(groupPosition).toString().contains("Demo Library")) {
                    Intent intent = new Intent(ActivityLibraryList.this, ActivityLibraryFloor.class);

                    String libraryName = ((TextView) v.findViewById(R.id.heading)).getText().toString();
                    intent.putExtra(LIBRARY_NAME, libraryName);
                    intent.putExtra(LIBRARY_ID, String.valueOf(libraryID));

                    timerTask.cancel();

                    startActivity(intent);
                } else {
                    String item = ((TextView) v.findViewById(R.id.heading)).getText().toString();

                    Toast.makeText(getBaseContext(), groupList.get(groupPosition).toString(), Toast.LENGTH_LONG).show();
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
                    updateLibraryStatus();
                }
            });
        }
    };
}

