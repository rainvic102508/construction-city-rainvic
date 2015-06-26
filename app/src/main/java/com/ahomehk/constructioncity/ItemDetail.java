package com.ahomehk.constructioncity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahomehk.constructioncity.items.Item;
import com.ahomehk.constructioncity.items.Provider;


public class ItemDetail extends ActionBarActivity {

    private static final String TAG = "ItemDetails";
    public static final String EXTRA_TAG = "ItemDetailsExtra";
    public static final String EXTRA_DATA_TYPE = "DataTypeExtra";

    /**
     * Type of data (Products/Services/News....)
     */
    private String dataType;

    /**
     * Item data
     */
    private Item item;

    /**
     * Provider of the Item
     */
    private Provider itemProvider;

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
     * Image url
     */
    private String img_url = "";

    /**
     * Pager height
     */
    private int pagerHeight;

    private ImageView pager_next, pager_previous;
    private int pager_next_height;
    private int padding_top_next_prev;

    /**
     * Item details display
     */
    private TextView tv_item_type, tv_price, tv_size, tv_company, tv_color, tv_finish;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        getExtras();


        Resources res = ItemDetail.this.getResources();
        img_url += res.getString(R.string.server_address) + res.getString(R.string.image_folder) +
                dataType + "/" + item.getItem_img_file() + "/";



        initializePager();

        //set actionbar title as an item title
        setTitle(item.getItem_title());

        tv_item_type = (TextView) findViewById(R.id.tv_item_type);
        tv_size = (TextView) findViewById(R.id.tv_size);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_company = (TextView) findViewById(R.id.tv_company);
        tv_color = (TextView) findViewById(R.id.tv_color);
        tv_finish = (TextView) findViewById(R.id.tv_finishing);

        String size_txt = "(WxHxD) ";
        if(item.getItem_width()!=-1)
            size_txt += item.getItem_width_str()+"x";
        else
            size_txt += "Nx";
        if(item.getItem_height()!=-1)
            size_txt += item.getItem_height_str()+"x";
        else
            size_txt += "Nx";
        if(item.getItem_thickness()!=-1)
            size_txt += item.getItem_thickness_str();
        else
            size_txt += "N";

        String price_txt = "";
        if(item.getItem_price_min()!=-1)
            price_txt += item.getItem_price_min()+" ~ ";
        else
            price_txt += "N ~ ";
        if(item.getItem_price_max()!=-1)
            price_txt += item.getItem_price_max();
        else
            price_txt += "N";

        String color_txt = "-";
        if(!item.getItem_color().equals("null"))
            color_txt = item.getItem_color();

        String finish_txt = "-";
        if(!item.getItem_finish().equals("null"))
            finish_txt = item.getItem_finish();

        tv_item_type.setText(getItemTypeText());
        tv_size.setText(size_txt);
        tv_price.setText(price_txt);
        tv_company.setText(itemProvider.getProvider_title());
        tv_color.setText(color_txt);
        tv_finish.setText(finish_txt);
        Log.i(TAG, "tag: " + item.getItem_tags() + "||| finish: " + item.getItem_finish());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    private String getItemTypeText(){
        String result = "";
        result += item.getType_main();
        if(!item.getType_one().equals("null")) {
            result += " > " + item.getType_one();
            if(!item.getType_two().equals("null")) {
                result += " > " + item.getType_two();
                if(!item.getType_three().equals("null")) {
                    result += " > " + item.getType_three();
                    if(!item.getType_four().equals("null")) {
                        result += " > " + item.getType_four();
                        if(!item.getType_five().equals("null")) {
                            result += " > " + item.getType_five();
                            if(!item.getType_extra().equals("null")) {
                                String[] extra = item.getType_extra().split(",");
                                for(int i=0; i<extra.length; i++){
                                    result += ">>" + extra[i];
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public void initializePager(){
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(MainActivity.SCREEN_SIZE, 0);
        pagerHeight = settings.getInt(MainActivity.SCREEN_SIZE_X, -1) / 2;

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager_item_details_img);
        pager_next = (ImageView) findViewById(R.id.iv_to_next);
        pager_previous = (ImageView) findViewById(R.id.iv_to_prev);

        //resource size
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_next_item, dimensions);
        pager_next_height = dimensions.outHeight;

        //set pager next/prev position
        padding_top_next_prev = (pagerHeight-pager_next_height)/2;

        pager_next.setPadding(0, padding_top_next_prev, 0, 0);
        pager_previous.setPadding(0, padding_top_next_prev, 0, 0);

        mPagerAdapter = new ItemSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.getLayoutParams().height = pagerHeight;
        //do something when pager state changed
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position > 0) {
                    pager_previous.setVisibility(View.VISIBLE);
                } else {
                    pager_previous.setVisibility(View.GONE);
                }

                if (position + 1 < item.getImg_names().length) {
                    pager_next.setVisibility(View.VISIBLE);
                } else {
                    pager_next.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void toNextPage(View v){
        mPager.setCurrentItem(mPager.getCurrentItem()+1);
    }

    public void toPrevPage(View v){
        mPager.setCurrentItem(mPager.getCurrentItem()-1);
    }

    public void getExtras(){
        Intent intent = getIntent();
        item = (Item)intent.getSerializableExtra(EXTRA_TAG);
        Log.i(TAG, "############  item.getType_main(): "+item.getType_main());
        switch(item.getType_main()){
            case "product":
                dataType = "products";
                break;
            case "service":
                dataType = "services";
                break;
            case "job reference":
                dataType = "job_reference";
                break;
            case "news":
                dataType = "news";
                break;
        }
        itemProvider = new Provider(getProviderOfItem());

    }

    public Provider getProviderOfItem(){
        DBAdapter dbAdapter = new DBAdapter(ItemDetail.this);
        return dbAdapter.getProviderOfItem(item.get_provider_id());
    }

    public void howManyImgsAreThere(String img_file_name){





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_details, menu);


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
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(this, MainActivity2Activity.class);
                startActivity(i);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                // close this activity
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ItemSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ItemSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            String url = img_url + item.getImg_names()[position];
            Log.i(TAG, "image url: " + url);
            ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
            itemDetailFragment.setImgUrl(url);
            return itemDetailFragment;
        }

        @Override
        public int getCount() {
            return item.getImg_names().length;
        }
    }


    public void sendWhatsapp(View view){
        Log.i(TAG, "Send message to provider via whatsapp!!");

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "Hi, I saw your item on Construction city app and want to know more information about "
                    +item.getItem_title()
                    +"("+item.getItem_img_file()+").";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Send a message to "));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }



    }

    public void callProvider(View view){
        Log.i(TAG, "call provider!!");

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + itemProvider.getProvider_contact()));
        startActivity(callIntent);
    }
}
