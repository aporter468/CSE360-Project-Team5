package com.porter.esas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import android.util.Log;

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
    boolean isSet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_history, container, false);
        ((MainActivity)getActivity()).setupHistoryTable();
        isSet = true;
        return rootView;
    }
    public View getRootView()
    {
        return rootView;
    }
    public boolean isSet(){
        return isSet;
    }

    public void setDate(int[] selectedDate, ArrayList<Survey> surveys)
    {
        TextView mTextView;
        mTextView = (TextView)getView().findViewById(R.id.selectedDate);
        mTextView.setText("Date: "+selectedDate[0]+"/"+selectedDate[1]+"/"+selectedDate[2]);
        ArrayList<Survey> dateMatches = new ArrayList<Survey>();
        for(int i =0; i<surveys.size(); i++)
        {
            Calendar c = surveys.get(i).getCalendar();
            if(selectedDate[0] == c.get(Calendar.YEAR) && selectedDate[1] == c.get(Calendar.MONTH) && selectedDate[2] == c.get(Calendar.DAY_OF_MONTH))
            {
                dateMatches.add(surveys.get(i));
                Log.e("mylog","datematch: "+surveys.get(i).getSurveyValues());
            }
        }

        //popup list of surveys for selected date
    }

}