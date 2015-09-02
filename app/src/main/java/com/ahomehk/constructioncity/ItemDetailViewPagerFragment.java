package com.ahomehk.constructioncity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


/**
 * A placeholder fragment containing a simple view.
 */
public class ItemDetailViewPagerFragment extends Fragment {
    private static final String TAG = "ItemDetailsFragment";
    public static final String IMG_URL = "img_url";

    //image view
    NetworkImageView item_img;
    ImageLoader mImageLoader;

    //position num
    String img_url;




    public ItemDetailViewPagerFragment() {
    }

    public void setImgUrl(String url){
        this.img_url = url;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_detail, container, false);
        item_img = (NetworkImageView) rootView.findViewById(R.id.iv_item_img);
        item_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ZoomableActivity.class);
                intent.putExtra(IMG_URL, img_url);
                startActivity(intent);
            }
        });

        // Get the ImageLoader through your singleton class.
        mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
        Log.i(TAG, "img url: "+img_url);

        item_img.setImageUrl(img_url, mImageLoader);
        //mImageLoader.get(img_url, ImageLoader.getImageListener(item_img, R.drawable.warning, R.drawable.warning));

        return rootView;
    }

}

