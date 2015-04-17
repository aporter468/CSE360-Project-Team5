package com.porter.esas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class PatientsFragment extends Fragment {
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
    public static PatientsFragment newInstance() {
        PatientsFragment fragment = new PatientsFragment();
        Bundle args = new Bundle();
       // args.putInt(ARG_SECTION_NUMBER, );
        fragment.setArguments(args);
        return fragment;
    }

    public PatientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_patients, container, false);

        return rootView;
    }



}