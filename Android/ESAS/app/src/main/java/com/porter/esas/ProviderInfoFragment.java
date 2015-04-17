package com.porter.esas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProviderInfoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_NAME= "name";
    private static final String ARG_PHONE= "phone";
    private static final String ARG_EMAIL= "email";



    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ProviderInfoFragment newInstance(String name,String phone, String email) {
        ProviderInfoFragment fragment = new ProviderInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME,name);
        args.putString(ARG_PHONE,phone);
        args.putString(ARG_EMAIL,email);

        fragment.setArguments(args);
        return fragment;
    }

    public ProviderInfoFragment() {
    }
    View rootView;
    TextView nameView;
    TextView emailView;
    TextView phoneView;
    String name;
    String phone;
    String email;
    boolean init;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_provider_info, container, false);
         nameView = (TextView)rootView.findViewById(R.id.providerName);
        phoneView = (TextView)rootView.findViewById(R.id.providerPhone);
        emailView = (TextView)rootView.findViewById(R.id.providerEmail);
        String[] providerInfo = ((MainActivity)getActivity()).getPatientsProviderInfo();
            nameView.setText("Name: "+providerInfo[0]);
        phoneView.setText("Phone: "+providerInfo[1]);
        emailView.setText("Email: "+providerInfo[2]);


        init = true;
        Log.e("mylog","view created");
        return rootView;
    }





}