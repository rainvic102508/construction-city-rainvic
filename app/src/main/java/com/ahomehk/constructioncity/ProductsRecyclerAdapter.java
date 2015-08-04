package com.ahomehk.constructioncity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahomehk.constructioncity.items.Item;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Adapter for recycler view for product items
 */
public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder> {

    static final private String TAG = "ProductsAdapter";

    private ArrayList<Item> products = new ArrayList<>();
    private Context context;
    private ImageLoader mImageLoader;
    private DisplayMetrics metrics;
    private static final int img_size_px = 200;
    private static int img_size_dp;

    public ProductsRecyclerAdapter(ArrayList<Item> products, Context c) {
        this.products = products;
        this.context = c;
        this.mImageLoader = MySingleton.getInstance(context).getImageLoader();
        metrics = context.getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);

        img_size_dp = img_size_px * 160 / densityDpi;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public NetworkImageView img;
        public TextView text;
        //public ProgressBar progressBar;

        public MyViewHolderClickListener mListener;

        public ViewHolder(View v, MyViewHolderClickListener listener) {
            super(v);

            text = (TextView) v.findViewById(R.id.tv_products_item);
            img = (NetworkImageView) v.findViewById(R.id.iv_products_item);
            //progressBar = (ProgressBar) v.findViewById(R.id.pb_products_item);

            mListener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(view, getAdapterPosition());
        }

        public interface MyViewHolderClickListener {
            void onItemClick(View caller, int pos);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_products_item, parent, false);

        //v.setLayoutParams(new ActionBar.LayoutParams(img_size_dp, img_size_dp));

        ProductsRecyclerAdapter.ViewHolder vh = new ProductsRecyclerAdapter.ViewHolder(v, new ProductsRecyclerAdapter.ViewHolder.MyViewHolderClickListener(){
            public void onItemClick(View caller, int pos) {

                //Log.i(TAG, "onItemClick~~:::" + products.get(pos).getItem_title());
                Intent i = new Intent(context, ItemDetailActivity.class);
                i.putExtra(ItemDetailActivity.EXTRA_TAG, products.get(pos));
                context.startActivity(i);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Item product = products.get(position);

        holder.text.setText(product.getItem_title());
        //mImageLoader.get(product.getImg_add() + product.getImg_names()[0],
        // ImageLoader.getImageListener(holder.img,R.drawable.splash_logo, R.drawable.image_load_error));
        holder.img.setDefaultImageResId(R.drawable.image_load_error);
        holder.img.setErrorImageResId(R.drawable.image_load_error);
        holder.img.setImageUrl(product.getImg_add()+product.getImg_names()[0], mImageLoader);
        /*
        mImageLoader.get(product.getImg_add()+product.getImg_names()[0], new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response != null) {
                    Bitmap bitmap = response.getBitmap();
                    if (bitmap != null) {
                        // ** code to turn off the progress wheel **
                        // ** code to use the bitmap in your imageview **

                        holder.img.setImageBitmap(bitmap);
                        holder.progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                holder.img.setImageResource(R.drawable.image_load_error);
                holder.progressBar.setVisibility(View.GONE);
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

}
