package com.ahomehk.constructioncity;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahomehk.constructioncity.items.Item;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by rainvic on 20/5/15.
 */
public class ListViewAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    public static final String TAG = "ItemsListAdapter";



    private ArrayList<Item> items;
    private ArrayList<String> img_add;
    private LayoutInflater inflater;
    private Context mContext;
    private int img_size;
    private ImageLoader mImageLoader;

    public ListViewAdapter(Context context, ArrayList<Item> items, ArrayList<String> img_add) {
        //initializing
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);

        this.items = items;
        this.img_add = img_add;

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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_main_listview, parent, false);
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
        Log.i(TAG, "image url: " + img_add.get(position));

        holder.img.getLayoutParams().height = img_size;
        holder.img.getLayoutParams().width = img_size;

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.item_listview_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.tv_fragment_lv_header);
            holder.background = (RelativeLayout) convertView.findViewById(R.id.rv_lv_header);
            convertView.setTag(holder);

        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = "" + getItemTypeText(getItem(position));
        holder.text.setText(headerText);

        return convertView;
    }

    private String getItemTypeText(Item item){
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

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return items.get(position).get_type_id();
    }

    class HeaderViewHolder {
        TextView text;
        RelativeLayout background;
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
