package com.ahomehk.constructioncity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.io.Serializable;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class MainActivity2Activity extends ActionBarActivity{

    public static final String TAG = "MAINACTIVITY2ACTIVITY";

    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    Main2PagerAdapter mMain2PagerAdapter;
    ViewPager mViewPager;
    static PagerTitleStrip mPagerTitleStrip;

    //Selected item from MainActivity
    int selectedInfo;

    //Menu item
    String[] menuTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        //set menuTitles
        Resources res = this.getResources();
        menuTitles = res.getStringArray(R.array.main_menu_list);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mMain2PagerAdapter =
                new Main2PagerAdapter(
                        getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.pager_main_2);
        mViewPager.setAdapter(mMain2PagerAdapter);

        mPagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);

        //move to the selected section from MainActivity
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                selectedInfo = -1;
            } else {
                selectedInfo = extras.getInt("WHICH_SECTION");
            }
        } else {
            selectedInfo= (int) savedInstanceState.getSerializable("WHICH_SECTION");
        }
        mViewPager.setCurrentItem(selectedInfo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);


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
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                // close this activity
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
    public class Main2PagerAdapter extends FragmentStatePagerAdapter {

        public Main2PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new ListViewFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(ListViewFragment.ARG_OBJECT, i);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getCount() {
            return menuTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return menuTitles[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }



    // Instances of this class are fragments representing a single
// object in our collection.
    public static class ListViewFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.fragment_listview, container, false);
            Bundle args = getArguments();
            final int data = args.getInt(ARG_OBJECT);

            StickyListHeadersListView stickyList = (StickyListHeadersListView) rootView.findViewById(R.id.lv_fragment);
            final ListFragmentAdapter adapter = new ListFragmentAdapter(getActivity(), data);
            stickyList.setAdapter(adapter);

            stickyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent i = new Intent(getActivity(), ItemDetail.class);
                    i.putExtra(ItemDetail.EXTRA_TAG, (Serializable)adapter.getItem(position));
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            });

            return rootView;
        }
    }
}

