package com.porter.esas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.util.Calendar;

public class SurveyFragment extends Fragment {
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
    public static SurveyFragment newInstance(int sectionNumber) {
        SurveyFragment fragment = new SurveyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SurveyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_survey, container, false);
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.surveyLayout);
        for(int i =0; i<Survey.numSurveyFields;i++)
        {
           final TextView fieldLabel = new TextView(getActivity());
            fieldLabel.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            fieldLabel.setText(Survey.SURVEY_FIELDS[i]);
            fieldLabel.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            linearLayout.addView(fieldLabel);
            SeekBar fieldBar = new SeekBar(getActivity());
            fieldBar.setMax(10);
            final int index = i;
            fieldBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                int progress = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                    progress = progressValue;
                    surveyValues[index] = progress;
                    fieldLabel.setText(Survey.SURVEY_FIELDS[index]+": "+progress);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            linearLayout.addView(fieldBar);
        }


      /*   TextView commentsLabel = new TextView(getActivity());
        commentsLabel.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        commentsLabel.setText("Comments:");
        commentsLabel.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
        linearLayout.addView(commentsLabel);
           commentsET = new EditText(getActivity());
           linearLayout.addView(commentsET);*/

        Button submitButton = new Button(getActivity());
        submitButton.setText("Submit");
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {
                submitSurvey();
            }
        });

        linearLayout.addView(submitButton);
        return rootView;
    }
    private void submitSurvey()
    {
        Survey survey = new Survey(surveyValues,"",Calendar.getInstance());
        ((MainActivity) getActivity()).submitSurvey(survey);
    }


}