package com.ahomehk.constructioncity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ahomehk.constructioncity.items.Item;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rainvic on 6/6/15.
 */
public class SearchableActivity extends ActionBarActivity {

    private static final String TAG = "SearchableActivity";

    private ArrayList<Item> searchResults = new ArrayList<>();

    private ListView lv_searchable;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        lv_searchable = (ListView) findViewById(R.id.lv_searchable);
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            DBAdapter db = new DBAdapter(this);
            searchResults = db.getSearchResult(query);
            //Log.i(TAG, "Intent extra: " + query);
        }
        SearchResultAdapter adapter = new SearchResultAdapter(this, searchResults);
        lv_searchable.setAdapter(adapter);

        lv_searchable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(SearchableActivity.this, ItemDetailActivity.class);
                intent.putExtra(ItemDetailActivity.EXTRA_TAG, (Serializable)searchResults.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private class SearchResultAdapter extends ArrayAdapter<Item> {

        private ImageLoader mImageLoader;
        private int img_size;

        public SearchResultAdapter(Context context, ArrayList<Item> searchResult) {
            super(context, 0, searchResult);

            if(getImgSize()!=-1){
                img_size = getImgSize()/4;
            }else{
                //Log.e(TAG, "getImgSize() returns -1!!");
            }

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            // Get the data item for this position
            Item item = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_result, parent, false);
                holder.text = (TextView) convertView.findViewById(R.id.tv_sr_title);
                holder.img = (NetworkImageView) convertView.findViewById(R.id.iv_sr);

                mImageLoader = MySingleton.getInstance(SearchableActivity.this).getImageLoader();
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            // Lookup view for data population
            holder.text.setText(getItem(position).getItem_title());
            holder.img.setImageUrl(getItem(position).getImg_add()+getItem(position).getItem_img_file()+"_0.jpg", mImageLoader);


            holder.img.getLayoutParams().height = img_size;
            holder.img.getLayoutParams().width = img_size;
            // Return the completed view to render on screen
            return convertView;
        }


        private int getImgSize(){
            // Restore preferences
            SharedPreferences screen_size = SearchableActivity.this.getSharedPreferences(MainActivity.SCREEN_SIZE, 0);
            return screen_size.getInt(MainActivity.SCREEN_SIZE_X, -1);
        }
    }

    private class ViewHolder {
        NetworkImageView img;
        TextView text;
    }
}
