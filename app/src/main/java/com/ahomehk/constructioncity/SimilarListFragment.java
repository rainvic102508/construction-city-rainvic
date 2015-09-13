package com.ahomehk.constructioncity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahomehk.constructioncity.items.Item;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SimilarListFragment extends Fragment {

    private static final String TAG = SimilarListFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private ArrayList<Item> products;

    private RecyclerView rvProducts;
    private RecyclerView.Adapter rvProductsAdapter;
    private RecyclerView.LayoutManager rvProductsLayoutManager;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment TypesGridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimilarListFragment newInstance(String param1) {
        SimilarListFragment fragment = new SimilarListFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }

    public SimilarListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBAdapter dbAdapter = new DBAdapter(getActivity());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        products = dbAdapter.getProductsForAType(mParam1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_similar_list, container, false
        );
        rvProducts = (RecyclerView) rootView.findViewById(R.id.rv_similar_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvProducts.setHasFixedSize(true);

        // use a linear layout manager
        rvProductsLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        rvProducts.setLayoutManager(rvProductsLayoutManager);

        // specify an adapter (see also next example)
        rvProductsAdapter = new ProductsRecyclerAdapter(products, getActivity());

        rvProducts.setAdapter(rvProductsAdapter);

        return rootView;
    }
}
