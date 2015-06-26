package com.ahomehk.constructioncity;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rainvic on 27/4/15.
 */
public class GVAdapter extends BaseAdapter {

    Context mContext;
    String[] menuTitles;

    public GVAdapter(Context c){
        this.mContext = c;
        Resources res = mContext.getResources();
        menuTitles = res.getStringArray(R.array.main_menu_list);
    }
    @Override
    public int getCount() {
        return menuTitles.length;
    }

    @Override
    public String getItem(int position) {
        return menuTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.main_menu_icon, viewGroup, false);

        holder.txt = (TextView) itemView.findViewById(R.id.tv_main_menu);
        holder.img = (ImageView) itemView.findViewById(R.id.iv_mainbutton);
        //set image of the each item
        holder.txt.setText(menuTitles[position]);
        holder.img.setImageResource(images[position]);


        return itemView;
    }

    Integer[] images = {
      R.drawable.main_product, R.drawable.main_new_arrival,
            R.drawable.main_service, R.drawable.main_recent_project,
            R.drawable.main_design, R.drawable.main_news
    };

    class Holder {
        TextView txt;
        ImageView img;
    }
}
