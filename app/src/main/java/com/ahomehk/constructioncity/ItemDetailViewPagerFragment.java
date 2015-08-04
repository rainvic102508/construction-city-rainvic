package com.ahomehk.constructioncity;

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

        // Get the ImageLoader through your singleton class.
        mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
        Log.i(TAG, "img url: "+img_url);

        item_img.setImageUrl(img_url, mImageLoader);
        //mImageLoader.get(img_url, ImageLoader.getImageListener(item_img, R.drawable.warning, R.drawable.warning));

        return rootView;
    }

}

