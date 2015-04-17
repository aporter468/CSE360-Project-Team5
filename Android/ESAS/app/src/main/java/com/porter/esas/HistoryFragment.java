package com.porter.esas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HistoryFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HistoryFragment newInstance(int userType) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, userType);
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment() {
    }
View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_history, container, false);
        ((MainActivity)getActivity()).setupHistoryTable();
        return rootView;
    }
    public View getRootView()
    {
        return rootView;
    }

    public void setDate(int[] selectedDate)
    {
        TextView mTextView;
        mTextView = (TextView)getView().findViewById(R.id.selectedDate);
        mTextView.setText("Date: "+selectedDate[0]+"/"+selectedDate[1]+"/"+selectedDate[2]);
    }

}