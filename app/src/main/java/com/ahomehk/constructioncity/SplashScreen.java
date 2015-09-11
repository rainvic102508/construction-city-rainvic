package com.ahomehk.constructioncity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ahomehk.constructioncity.items.Item;
import com.ahomehk.constructioncity.items.ItemType;
import com.ahomehk.constructioncity.items.Provider;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 0;

    private static final String TAG = "SplashScreen";

    private Resources res;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = (ProgressBar) findViewById(R.id.pb_splash_loading);
        progressBar.setVisibility(View.VISIBLE);

        ArrayList<Integer> tryit = new ArrayList<>();
        res = getResources();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                String url = res.getString(R.string.server_address) + res.getString(R.string.has_db_updated_address);
                final DBAdapter dbAdapter = new DBAdapter(SplashScreen.this);


                JsonArrayRequest jsArrRequest = new JsonArrayRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {

                                int num_json = response.length();
                                for (int i = 0; i < num_json; i++) {
                                    JSONObject jsonObject = new JSONObject();
                                    String table_name = "", new_date = "";
                                    String result;
                                    try {
                                        jsonObject = response.getJSONObject(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        table_name = jsonObject.getString("Name");
                                        new_date = jsonObject.getString("Update_time");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    result = dbAdapter.needUpdate(table_name, new_date);
                                    //Log.i(TAG, "needUpdate(" + table_name + ", " + new_date + ") ---> return with: " + result);
                                    if (result != null) {
                                        updateDatabase(result);
                                        dbAdapter.updateToNewDate(result, new_date);
                                    }
                                }

                                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                                Resources res = SplashScreen.this.getResources();

                                //set string for volley GET request with params
                                String url = res.getString(R.string.server_address)
                                        + res.getString(R.string.count_num_of_ads);
                                //Log.i(TAG, url);

                                // Formulate the request and handle the response.
                                StringRequest request = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.i(TAG, "ads_src :" + response);

                                                //save the screen size in shared preferences
                                                SharedPreferences adsSource = getSharedPreferences(MainActivity.ADS_SRC, 0);
                                                SharedPreferences.Editor editor = adsSource.edit();
                                                editor.putString(MainActivity.ADS_IMGS, response);
                                                // Commit the edits!
                                                editor.commit();


                                                // This method will be executed once the timer is over
                                                // Start your app main activity
                                                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                                                startActivity(i);

                                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                                                // close this activity
                                                finish();
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                //Log.e(TAG, error.toString());
                                            }
                                        });
                                // Access the RequestQueue through your singleton class.
                                MySingleton.getInstance(SplashScreen.this).addToRequestQueue(request);

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                                //Toast.makeText(SplashScreen.this, "volley error!!\n" + error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                // Access the RequestQueue through your singleton class.
                MySingleton.getInstance(SplashScreen.this).addToRequestQueue(jsArrRequest);


            }
        }, SPLASH_TIME_OUT);

    }

    public void updateDatabase(String table_name){
        switch (table_name){
            case DBAdapter.FTS_TABLE_ITEM:
                updateItemTable();
                break;
            case DBAdapter.FTS_TABLE_ITEM_TYPE:
                updateItemTypeTable();
                break;
            case DBAdapter.FTS_TABLE_PROVIDER:
                updateProviderTable();
                break;
            default:
                //Log.w(TAG, "updateDatabase(String table_name) : table name did not match");
                break;

        }
    }

    /**
     *  get item table and update to the database
     */
    public void updateItemTable(){
        String url = res.getString(R.string.server_address)+res.getString(R.string.update_db_item_table);
        final DBAdapter dbAdapter = new DBAdapter(SplashScreen.this);

        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        int num_json = response.length();
                        ArrayList<Item> items = new ArrayList<>();
                        for(int i=0; i<num_json; i++){

                            //get jsonObject at the point
                            JSONObject jsonObject = new JSONObject();
                            try{
                                jsonObject = response.getJSONObject(i);
                            }catch(JSONException e){
                                //Log.e(TAG, "updateItemTable() : while getting an obj-"+e.toString());
                            }


                            //populate the jsonObject values to the class
                            try{
                                Item item = new Item(
                                        jsonObject.getInt(DBAdapter.COLUMN_ITEM_ID),
                                        jsonObject.getInt(DBAdapter.COLUMN_TYPE_ID),
                                        jsonObject.getInt(DBAdapter.COLUMN_PROVIDER_ID),
                                        jsonObject.getDouble(DBAdapter.COLUMN_ITEM_PRICE_MIN),
                                        jsonObject.getDouble(DBAdapter.COLUMN_ITEM_PRICE_MAX),
                                        jsonObject.getInt(DBAdapter.COLUMN_ITEM_WIDTH),
                                        jsonObject.getInt(DBAdapter.COLUMN_ITEM_HEIGHT),
                                        jsonObject.getInt(DBAdapter.COLUMN_ITEM_THICKNESS),
                                        jsonObject.getString(DBAdapter.COLUMN_ITEM_WIDTH_HEIGHT),
                                        jsonObject.getString(DBAdapter.COLUMN_ITEM_IMG_FILE),
                                        jsonObject.getString(DBAdapter.COLUMN_ITEM_TITLE),
                                        jsonObject.getString(DBAdapter.COLUMN_ITEM_CREATED_AT),
                                        jsonObject.getString(DBAdapter.COLUMN_ITEM_EXTRA_DESCRIPTION),
                                        jsonObject.getString(DBAdapter.COLUMN_ITEM_TAG),
                                        jsonObject.getString(DBAdapter.COLUMN_ITEM_COLOR),
                                        jsonObject.getString(DBAdapter.COLUMN_ITEM_FINISH),
                                        jsonObject.getString(DBAdapter.COLUMN_ITEM_PLACE_OF_ORIGIN),
                                        jsonObject.getInt(DBAdapter.COLUMN_ITEM_LEAD_TIME)
                                        );
                                item.setImg_names(jsonObject.getString(DBAdapter.COLUMN_ITEM_IMG_NAMES));
                                //Log.i(TAG, "extra: "+jsonObject.getString(DBAdapter.COLUMN_ITEM_EXTRA_DESCRIPTION));
                                items.add(item);
                            }catch(JSONException e){
                                //Log.e(TAG, "updateItemTable() : while populate the object-"+e.toString());
                            }
                        }


                        //in order to update to the new data set, drop old table and create a new one and insert all data back
                        dbAdapter.dropTable(DBAdapter.FTS_TABLE_ITEM);
                        dbAdapter.createTable(DBAdapter.FTS_TABLE_ITEM);

                        dbAdapter.moveItemTable(items);

                        //Toast.makeText(SplashScreen.this, "update Item table", Toast.LENGTH_LONG).show();


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e(TAG, "updateItemTable() : " + error.toString());
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(SplashScreen.this).addToRequestQueue(jsArrRequest);
    }

    /**
     *  get item type table and update to the database
     */
    public void updateItemTypeTable(){
        String url = res.getString(R.string.server_address)+res.getString(R.string.update_db_address)+"?table_name="+DBAdapter.FTS_TABLE_ITEM_TYPE;
        final DBAdapter dbAdapter = new DBAdapter(SplashScreen.this);

        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        int num_json = response.length();
                        ArrayList<ItemType> items = new ArrayList<>();
                        for(int i=0; i<num_json; i++){

                            //get jsonObject at the point
                            JSONObject jsonObject = new JSONObject();
                            try{
                                jsonObject = response.getJSONObject(i);
                            }catch(JSONException e){
                                //Log.e(TAG, "updateItemTable() : while getting an obj -"+e.toString());
                            }

                            //populate the jsonObject values to the class
                            try{
                                ItemType itemType = new ItemType(
                                        jsonObject.getInt(DBAdapter.COLUMN_TYPE_ID),
                                        jsonObject.getString(DBAdapter.COLUMN_TYPE_MAIN),
                                        jsonObject.getString(DBAdapter.COLUMN_TYPE_ONE),
                                        jsonObject.getString(DBAdapter.COLUMN_TYPE_TWO),
                                        jsonObject.getString(DBAdapter.COLUMN_TYPE_THREE),
                                        jsonObject.getString(DBAdapter.COLUMN_TYPE_FOUR),
                                        jsonObject.getString(DBAdapter.COLUMN_TYPE_FIVE),
                                        jsonObject.getString(DBAdapter.COLUMN_TYPE_EXTRA),
                                        jsonObject.getString(DBAdapter.COLUMN_TYPE_CREATED_AT)
                                );
                                items.add(itemType);
                            }catch(JSONException e){
                                //Log.e(TAG, "updateItemTypeTable() : while populate the object -"+e.toString());
                            }
                        }



                        //in order to update to the new data set, drop old table and create a new one and insert all data back
                        dbAdapter.dropTable(DBAdapter.FTS_TABLE_ITEM_TYPE);
                        dbAdapter.createTable(DBAdapter.FTS_TABLE_ITEM_TYPE);

                        dbAdapter.moveItemTypeTable(items);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e(TAG, "updateItemTable() : " + error.toString());
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(SplashScreen.this).addToRequestQueue(jsArrRequest);
    }


    /**
     *  get provider table and update to the database
     */
    public void updateProviderTable(){
        String url = res.getString(R.string.server_address)+res.getString(R.string.update_db_address)+"?table_name="+DBAdapter.FTS_TABLE_PROVIDER;
        final DBAdapter dbAdapter = new DBAdapter(this);

        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        int num_json = response.length();
                        ArrayList<Provider> items = new ArrayList<>();
                        for(int i=0; i<num_json; i++){

                            //get jsonObject at the point
                            JSONObject jsonObject = new JSONObject();
                            try{
                                jsonObject = response.getJSONObject(i);
                            }catch(JSONException e){
                                //Log.e(TAG, "updateItemTable() : while getting an obj-"+e.toString());
                            }

                            //populate the jsonObject values to the class
                            try{
                                Provider item = new Provider(
                                        jsonObject.getInt(DBAdapter.COLUMN_PROVIDER_ID),
                                        jsonObject.getString(DBAdapter.COLUMN_PROVIDER_TITLE),
                                        jsonObject.getString(DBAdapter.COLUMN_PROVIDER_CONTACT),
                                        jsonObject.getString(DBAdapter.COLUMN_PROVIDER_EMAIL),
                                        jsonObject.getString(DBAdapter.COLUMN_PROVIDER_EXTRA_INFO),
                                        jsonObject.getString(DBAdapter.COLUMN_PROVIDER_CREATED_AT),
                                        jsonObject.getString(DBAdapter.COLUMN_PROVIDER_LINK)
                                );
                                items.add(item);
                            }catch(JSONException e){
                                //Log.e(TAG, "updateItemTable() : while populate the object-"+e.toString());
                            }
                        }



                        //in order to update to the new data set, drop old table and create a new one and insert all data back
                        dbAdapter.dropTable(DBAdapter.FTS_TABLE_PROVIDER);
                        dbAdapter.createTable(DBAdapter.FTS_TABLE_PROVIDER);

                        dbAdapter.moveProviderTable(items);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e(TAG, "updateItemTable() : " + error.toString());
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrRequest);
    }

}
