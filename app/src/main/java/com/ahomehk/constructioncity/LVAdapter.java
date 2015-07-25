package com.ahomehk.constructioncity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ahomehk.constructioncity.items.Item;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class LVAdapter extends BaseAdapter {
    private static final String TAG = "LVAdapter";
    private Context mContext;
    private ArrayList<Item> items;
    private LayoutInflater inflater;
    private ImageLoader mImageLoader;
    private int img_size;



    public LVAdapter(Context mContext, String[] arr) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        DBAdapter dbAdapter = new DBAdapter(mContext);
        this.items = new ArrayList<>(dbAdapter.getThisWeekTop(arr));

        if(getImgSize()!=-1){
            img_size = getImgSize()/4;
        }else{
            Log.e(TAG, "getImgSize() returns -1!!");
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_listview_item, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.tv_fragment_lv_item);
            holder.img = (NetworkImageView) convertView.findViewById(R.id.iv_fragment_lv_item);


            mImageLoader = MySingleton.getInstance(mContext).getImageLoader();
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(getItem(position).getItem_title());
        holder.img.setImageUrl(getItem(position).getImg_add()+getItem(position).getImg_names()[0], mImageLoader);
        // Set the URL of the image that should be loaded into this view, and
        // specify the ImageLoader that will be used to make the request.

        holder.img.getLayoutParams().height = img_size;
        holder.img.getLayoutParams().width = img_size;

        return convertView;
    }

    public void setItems(ArrayList arr){
        this.items = arr;
    }

    class ViewHolder {
        NetworkImageView img;
        TextView text;
    }

    private int getImgSize(){
        // Restore preferences
        SharedPreferences screen_size = mContext.getSharedPreferences(MainActivity.SCREEN_SIZE, 0);
        return screen_size.getInt(MainActivity.SCREEN_SIZE_X, -1);
    }
}
