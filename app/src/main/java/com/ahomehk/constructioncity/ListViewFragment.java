package com.ahomehk.constructioncity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ahomehk.constructioncity.items.Item;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by rainvic on 25/7/15.
 */
public class ListViewFragment extends Fragment {
    public static final String ARG_ITEMS = "items_dataset";


    private ArrayList<Item> items;
    private ArrayList<String> img_add = new ArrayList<>();

    private int data;
    private Resources res;
    private DBAdapter dbAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param Parameter 1.
     * @return A new instance of fragment ListViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListViewFragment newInstance(int param) {
        ListViewFragment fragment = new ListViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ITEMS, param);
        fragment.setArguments(args);
        return fragment;
    }

    public ListViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            this.data = getArguments().getInt(ARG_ITEMS);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dbAdapter = new DBAdapter(getActivity());

        res = getActivity().getResources();
        //String type_sys = res.getStringArray(R.array.main_menu_list_sys)[data];
        String IMAGE_URL = res.getString(R.string.server_address)+res.getString(R.string.image_folder);
        String data_string = res.getStringArray(R.array.main_menu_list)[data].toLowerCase();

        items = new ArrayList<>((dbAdapter.getItems(data_string)));
        for(int i=0; i<items.size(); i++){
            img_add.add(IMAGE_URL+"/"+items.get(i).getItem_img_file()+"/"+items.get(i).getImg_names()[0]);
        }

        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_listview, container, false);

        StickyListHeadersListView stickyList = (StickyListHeadersListView) rootView.findViewById(R.id.lv_fragment);
        final ItemsListAdapter adapter = new ItemsListAdapter(getActivity(), items, img_add);
        stickyList.setAdapter(adapter);

        stickyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(getActivity(), ItemDetail.class);
                i.putExtra(ItemDetail.EXTRA_TAG, adapter.getItem(position));
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        return rootView;
    }
}
