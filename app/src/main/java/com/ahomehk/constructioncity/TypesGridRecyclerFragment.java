package com.ahomehk.constructioncity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahomehk.constructioncity.items.ProductType;

import java.util.ArrayList;
import java.util.List;


/**
 * Use the {@link TypesGridRecyclerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TypesGridRecyclerFragment extends Fragment {

    private static final String ARG_TYPES_GRID = "types_grid";

    ArrayList<ProductType> arr;
    ArrayList<String> sectionstrset = new ArrayList<>();
    ArrayList<String> types = new ArrayList<>();

    private RecyclerView rv_types;
    private RecyclerView.Adapter rvTypesAdapter;
    private RecyclerView.LayoutManager rvTypesLayoutManager;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment TypesGridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TypesGridRecyclerFragment newInstance() {
        TypesGridRecyclerFragment fragment = new TypesGridRecyclerFragment();
        return fragment;
    }

    public TypesGridRecyclerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBAdapter dbAdapter = new DBAdapter(getActivity());
        arr = dbAdapter.getProductTypes();
        for(ProductType productType : arr){
            sectionstrset.add(productType.getType());
            for(String type : productType.getItems()){
                types.add(type);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_types_grid, container, false);
        rv_types = (RecyclerView) rootView.findViewById(R.id.rv_types);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv_types.setHasFixedSize(true);

        // use a linear layout manager
        rvTypesLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        rv_types.setLayoutManager(rvTypesLayoutManager);

        // specify an adapter (see also next example)
        rvTypesAdapter = new TypesGridRecyclerAdapter(types);

        //This is the code to provide a sectioned grid
        List<SectionedGridRecyclerViewAdapter.Section> sections =
                new ArrayList<SectionedGridRecyclerViewAdapter.Section>();

        //Sections
        for(int i=0; i<arr.size(); i++){
            int offset =0;
            for(int j=0; j<i; j++){
                offset += arr.get(j).getItems().size();
            }
            sections.add(new SectionedGridRecyclerViewAdapter.Section(offset,sectionstrset.get(i)));
        }

        //Add your adapter to the sectionAdapter
        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
        SectionedGridRecyclerViewAdapter mSectionedAdapter = new
                SectionedGridRecyclerViewAdapter(getActivity(),R.layout.item_types_gridview_header,R.id.tv_txt_types_grid_section,rv_types,rvTypesAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        rv_types.setAdapter(mSectionedAdapter);

        return rootView;
    }


}
