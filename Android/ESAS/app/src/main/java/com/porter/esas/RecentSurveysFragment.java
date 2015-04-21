package com.porter.esas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class RecentSurveysFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    final private int[] surveyValues = new int[Survey.NUM_SURVEY_FIELDS];
    private EditText commentsET;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RecentSurveysFragment newInstance() {
        RecentSurveysFragment fragment = new RecentSurveysFragment();
        Bundle args = new Bundle();
        // args.putInt(ARG_SECTION_NUMBER, );
        fragment.setArguments(args);
        return fragment;
    }

    public RecentSurveysFragment() {
    }

    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_recent_surveys, container, false);

        return rootView;
    }


    public View getRootView()
    {
        return rootView;
    }
}