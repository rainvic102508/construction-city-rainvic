package com.ahomehk.constructioncity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rainvic on 19/8/15.
 */
public class SimpleListViewFragment extends Fragment {

    private final String TAG = SimpleListViewFragment.class.getSimpleName();



    private TextView phone, fax, email;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment TypesGridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimpleListViewFragment newInstance() {
        SimpleListViewFragment fragment = new SimpleListViewFragment();
        return fragment;
    }

    public SimpleListViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_contact, container, false);




        updateContact(rootView);

        return rootView;
    }

    /**
     *  get provider table and update to the database
     */
    public void updateContact(final ViewGroup rootView){
        String url = getActivity().getResources().getString(R.string.server_address)+
                getActivity().getResources().getString(R.string.update_contact);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (url, new JSONObject(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        //populate the jsonObject values to the class
                        try {
//                            data.add();
//                            data.add(jsonObject.getString("fax"));
//                            data.add(jsonObject.getString("email"));
                            phone = (TextView) rootView.findViewById(R.id.phone_main);
                            fax = (TextView) rootView.findViewById(R.id.fax_main);
                            email = (TextView) rootView.findViewById(R.id.email_main);

                            phone.setText(jsonObject.getString("phone"));
                            fax.setText(jsonObject.getString("fax"));
                            email.setText(jsonObject.getString("email"));
                        } catch (JSONException e) {
                            //Log.e(TAG, "updateItemTable() : while populate the object-"+e.toString());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "updateContact() : " + error.toString());
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

}
