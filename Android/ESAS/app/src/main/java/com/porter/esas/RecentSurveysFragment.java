package com.porter.esas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class RecentSurveysFragment extends Fragment {

    final private int[] surveyValues = new int[Survey.NUM_SURVEY_FIELDS];
    private EditText commentsET;
    private  ArrayList<Survey> topSurveysList;

    public static RecentSurveysFragment newInstance() {
        RecentSurveysFragment fragment = new RecentSurveysFragment();
        Bundle args = new Bundle();
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
        topSurveysList = ((MainActivity)getActivity()).getTopSurveysList();
        if(topSurveysList == null || topSurveysList.size()==0)
        {
            ((MainActivity)getActivity()).setupProviderPatientsSurveys();
            topSurveysList = ((MainActivity)getActivity()).getTopSurveysList();
        }
        makeTopSurveysTable();

        return rootView;
    }

private void makeTopSurveysTable()
{

    TableLayout.LayoutParams rowParams =
            new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT, 1f);

    TableRow.LayoutParams itemParams =
            new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.FILL_PARENT, 1f);


    TableLayout tableLayout = (TableLayout)rootView.findViewById(R.id.topSurveysTable);
    tableLayout.removeAllViews();
    for (int i = 0; i <topSurveysList.size(); i++) {
        final Survey s = topSurveysList.get(i);
        TableRow tableRow = new TableRow(getActivity());
        tableRow.setLayoutParams(rowParams);
        tableRow.setBackgroundResource(R.drawable.row_border);

        TextView rowTitleText = new TextView(getActivity());
        rowTitleText.setTextSize(18);
        rowTitleText.setTextColor(Color.BLACK);
        rowTitleText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Survey Details");
                final TextView infoText = new TextView(getActivity());
                infoText.setText(s.getFullValueString()+"\n"+s.getPatient().getInfoString());

                builder.setView(infoText);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                builder.show();
            }
        });

        rowTitleText.setLayoutParams(itemParams);

        rowTitleText.setText(s.getPatientString()+"   "+s.getDateText());
        tableRow.addView(rowTitleText);
        tableLayout.addView(tableRow);

    }
}
    public View getRootView()
    {
        return rootView;
    }
}