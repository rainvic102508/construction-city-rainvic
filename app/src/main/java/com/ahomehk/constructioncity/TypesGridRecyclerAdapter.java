package com.ahomehk.constructioncity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rainvic on 25/7/15.
 */
public class TypesGridRecyclerAdapter extends RecyclerView.Adapter<TypesGridRecyclerAdapter.ViewHolder> {

    static final private String TAG = "TypesGridAdapter";

    public static ArrayList<String> types = new ArrayList<>();

    public TypesGridRecyclerAdapter(ArrayList<String> types) {
        this.types = types;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        public MyViewHolderClickListener mListener;

        public ViewHolder(TextView v, MyViewHolderClickListener listener) {
            super(v);
            mTextView = v;
            mListener = listener;
            mTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(view, getAdapterPosition());
        }

        public static interface MyViewHolderClickListener {
            public void onItemClick(View caller, int pos);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(types.get(position));

    }


    @Override
    public int getItemCount() {
        return types.size();
    }


}
