package com.porter.esas;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.widget.EditText;
import java.util.Calendar;

public class RecentSurveysFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    final private int[] surveyValues = new int[Survey.numSurveyFields];
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent_surveys, container, false);

        return rootView;
    }



}