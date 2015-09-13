package com.ahomehk.constructioncity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    public static final String SCREEN_SIZE = "screen_size";
    public static final String SCREEN_SIZE_X = "screen_size_x";
    public static final String SCREEN_SIZE_Y = "screen_size_y";
    public static final String ADS_SRC = "ads_src";
    public static final String ADS_IMGS = "ads_imgs";

    /**Gridview(main menu icon)
     */
    private GridView gridView;
    private MainScreenMenuGridAdapter mainScreenMenuGridAdapter;

    /**
     * ListView(This week top)
     */
   // private ListView listView;
   // private LVAdapter lvAdapter;

    /**
     * GridView(TWT)
     *
     */
    private ListView twtListView;
    private ThisWeekTopListAdapter thisWeekTopListAdapter;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    /**
     * The Database handler, which handles all the database process.
     */
    private DBAdapter dBAdapter;

    /**
     * Pager height
     */
    private static int mPagerHeight;

    /**
     * Number of advertisement images
     */
    String[] ads_src;
    static int currentPage = 0;
    String img_url;

    private ImageView ads_next, ads_prev;
    private LinearLayout ll_main_buttons;
    private int ads_next_height;
    private int padding_top_next_prev;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAdsImgs();

        initializeAds();

        initializeThisWeekTop();

        slideAdsAutomatically();


    }

    private void slideAdsAutomatically(){

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                currentPage++;
                if (currentPage == ads_src.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 3000);
    }

    private void initializeThisWeekTop(){
        twtListView = (ListView) findViewById(R.id.lv_this_week_top);

        //initialize DBAdapter
        dBAdapter = new DBAdapter(this, null, null, 1);

        Resources res = this.getResources();
        String url = res.getString(R.string.server_address)+res.getString(R.string.get_this_week_top);
        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] arr = response.split(":");
                        Log.i(TAG, "arr :"+response);
                        //initialize list view adapter
                        thisWeekTopListAdapter = new ThisWeekTopListAdapter(MainActivity.this, arr);
                        twtListView.setAdapter(thisWeekTopListAdapter);

                        twtListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
                                intent.putExtra(ItemDetailActivity.EXTRA_TAG, thisWeekTopListAdapter.getItem(position));
                                startActivity(intent);
                                MainActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

    }

    public void getAdsImgs(){
        //set directory url for ads images
        Resources res = getResources();
        img_url = res.getString(R.string.server_address)
                + res.getString(R.string.image_folder)
                + "ads/";

        // Restore ads images
        SharedPreferences adsImgs = getSharedPreferences(ADS_SRC, 0);
        String response = adsImgs.getString(ADS_IMGS, null);
        try{
            ads_src = response.split(":");
        }catch (NullPointerException e){
            Log.e(TAG, "response.split produce 'NullPointerException'");
        }
    }



    public void initializeAds(){
        //screend size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_width = size.x;
        int screen_height = size.y;

        //save the screen size in shared preferences
        SharedPreferences settings = getSharedPreferences(SCREEN_SIZE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(SCREEN_SIZE_X, screen_width);
        editor.putInt(SCREEN_SIZE_Y, screen_height);
        // Commit the edits!
        editor.commit();

        //next/prev imageView size
        BitmapFactory.Options vext_prev_dimensions = new BitmapFactory.Options();
        vext_prev_dimensions.inJustDecodeBounds = true;
        Bitmap nextPrevBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_next_item, vext_prev_dimensions);
        int nextPrevHeight = vext_prev_dimensions.outHeight;

        int mPagerHeight = screen_height/7*2;


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.getLayoutParams().height = mPagerHeight;
        mPager.setPadding(screen_width/10, 0, screen_width/10, 0);
        mPager.setClipToPadding(false);
        mPager.setPageMargin(16);

        ads_next = (ImageView) findViewById(R.id.iv_ads_next);
        ads_prev = (ImageView) findViewById(R.id.iv_ads_prev);


        padding_top_next_prev = (mPagerHeight - nextPrevHeight)/2;

        ads_next.setPadding(0, padding_top_next_prev, 0, 0);
        ads_prev.setPadding(0, padding_top_next_prev, 0, 0);

        //do something when pager state changed
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                setSlidingButtons(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        gridView = (GridView) findViewById(R.id.gvButtons);
        mainScreenMenuGridAdapter = new MainScreenMenuGridAdapter(this);
        gridView.setAdapter(mainScreenMenuGridAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Intent i = new Intent(MainActivity.this, NavigateItemsActivity.class);
                i.putExtra("WHICH_SECTION", position);
                startActivity(i);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });


    }

    public void toNextPage(View v){
        mPager.setCurrentItem(mPager.getCurrentItem()+1);
    }

    public void toPrevPage(View v){
        mPager.setCurrentItem(mPager.getCurrentItem()-1);
    }

    public static int getmPagerHeight(){
        return mPagerHeight;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.menu_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            AdsViewPagerFragment adsViewPagerFragment = new AdsViewPagerFragment();
            adsViewPagerFragment.setUrl(img_url+ads_src[position]);
            return adsViewPagerFragment;
        }

        @Override
        public int getCount() {
            return ads_src.length;
        }


    }

    private void setSlidingButtons(int position) {
        if (position == 0) {
            ads_prev.setVisibility(View.GONE);
        } else {
            ads_prev.setVisibility(View.VISIBLE);
        }

        if (position + 1 == ads_src.length) {
            ads_next.setVisibility(View.GONE);
        } else {
            ads_next.setVisibility(View.VISIBLE);
        }
    }

}
