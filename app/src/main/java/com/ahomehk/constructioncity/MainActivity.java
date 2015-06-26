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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;


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
    private GVAdapter gvAdapter;

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
        ads_src = response.split(":");
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

        //resource size

/*
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fragment_test_img_1, dimensions);
        int image_height = dimensions.outHeight;
        int image_width =  dimensions.outWidth;
*/

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

        ads_next = (ImageView) findViewById(R.id.iv_ads_next);
        ads_prev = (ImageView) findViewById(R.id.iv_ads_prev);

        //ll_main_buttons = (LinearLayout) findViewById(R.id.ll_main_buttons);
        //ll_main_buttons.getLayoutParams().height = mPagerHeight;


        padding_top_next_prev = (mPagerHeight - nextPrevHeight)/2;

        ads_next.setPadding(0, padding_top_next_prev, 0, 0);
        ads_prev.setPadding(0, padding_top_next_prev, 0, 0);

        //do something when pager state changed
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position > 0) {
                    ads_prev.setVisibility(View.VISIBLE);
                } else {
                    ads_prev.setVisibility(View.GONE);
                }

                if (position + 1 < ads_src.length) {
                    ads_next.setVisibility(View.VISIBLE);
                } else {
                    ads_next.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        gridView = (GridView) findViewById(R.id.gvButtons);
        gvAdapter = new GVAdapter(this);
        gridView.setAdapter(gvAdapter);

        //initialize DBAdapter
        dBAdapter = new DBAdapter(this, null, null, 1);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                DBAdapter dbAdapter = new DBAdapter(MainActivity.this);

                Intent i = new Intent(MainActivity.this, MainActivity2Activity.class);
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
            AdsFragment adsFragment = new AdsFragment();
            adsFragment.setUrl(img_url+ads_src[position]);
            return adsFragment;
        }

        @Override
        public int getCount() {
            return ads_src.length;
        }
    }


}
