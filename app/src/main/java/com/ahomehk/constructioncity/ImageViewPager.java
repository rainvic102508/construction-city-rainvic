package com.ahomehk.constructioncity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


public class ImageViewPager extends Fragment {

    private final static String TAG = "AdsFragment";

    //image view
    NetworkImageView ads;


    ImageLoader mImageLoader;

    //img url
    String img_url;

    public ImageViewPager() {
        // Required empty public constructor
    }

    public void setUrl(String url){
        this.img_url = url;
        Log.i(TAG, "url: "+img_url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_ads, container, false);
        ads = (NetworkImageView) rootView.findViewById(R.id.iv_ads);

        // Get the ImageLoader through your singleton class.
        mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();

        ads.setImageUrl(img_url, mImageLoader);

        //ads.setImageResource(getRscId(position));
        return rootView;
    }

}
