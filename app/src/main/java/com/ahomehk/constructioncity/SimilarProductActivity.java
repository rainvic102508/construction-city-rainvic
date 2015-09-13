package com.ahomehk.constructioncity;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ahomehk.constructioncity.items.Item;

import java.util.ArrayList;

public class SimilarProductActivity extends AppCompatActivity {

    private static final String TAG = SimilarProductActivity.class.getSimpleName();

    private int seriesOrType;
    private String similarKeyString;

    private ArrayList<Item> products;

    private RecyclerView rvProducts;
    private RecyclerView.Adapter rvProductsAdapter;
    private RecyclerView.LayoutManager rvProductsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_product);

        ActionBar ab = getActionBar();
        ab.setTitle("Similar Product");

        seriesOrType = getIntent().getIntExtra(ItemDetailActivity.KEY_SIMILAR_PRODUCT, 0);
        similarKeyString = getIntent().getStringExtra(ItemDetailActivity.KEYWORD_SIMILAR);

        Log.v(TAG, "keyword: " + similarKeyString);
        DBAdapter dbAdapter = new DBAdapter(this);
        products = dbAdapter.getSimilarProduct(seriesOrType, similarKeyString);

        rvProducts = (RecyclerView) findViewById(R.id.rv_similar_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvProducts.setHasFixedSize(true);

        // use a linear layout manager
        rvProductsLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        rvProducts.setLayoutManager(rvProductsLayoutManager);

        // specify an adapter (see also next example)
        rvProductsAdapter = new ProductsRecyclerAdapter(products, this);

        rvProducts.setAdapter(rvProductsAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop:");
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_similar_product, menu);
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
}
