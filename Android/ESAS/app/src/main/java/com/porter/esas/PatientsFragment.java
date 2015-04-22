package com.porter.esas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import android.util.Log;

public class PatientsFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    final private int[] surveyValues = new int[Survey.NUM_SURVEY_FIELDS];
    private EditText commentsET;
   private ArrayList<Patient> patientsList;
   private View rootView;
    private GraphView graph;
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
       rootView = inflater.inflate(R.layout.fragment_patients, container, false);
        patientsList = ((MainActivity)getActivity()).getPatientsList();
        if(patientsList == null|| patientsList.size()==0)
        {
            ((MainActivity)getActivity()).setupProviderPatientsSurveys();
            patientsList = ((MainActivity)getActivity()).getPatientsList();
        }
        makePatientsTable();
        graph =new GraphView((MainActivity)getActivity(),new ArrayList<Survey>());

        RelativeLayout graphViewLayout =  (RelativeLayout)(rootView.findViewById(R.id.surveyGraphContainer));
        graphViewLayout.addView(graph);
        graphViewLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        return rootView;
    }
private void makePatientsTable()
{

    TableLayout.LayoutParams rowParams =
            new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT, 1f);

    TableRow.LayoutParams itemParams =
            new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.FILL_PARENT, 1f);


    TableLayout tableLayout = (TableLayout)rootView.findViewById(R.id.patientsTable);
    tableLayout.removeAllViews();
    for (int i = 0; i <patientsList.size(); i++) {
       final Patient p = patientsList.get(i);
        TableRow tableRow = new TableRow(getActivity());
        tableRow.setLayoutParams(rowParams);

        TextView rowTitleText = new TextView(getActivity());
        rowTitleText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openPatientDetails(p);
            }
        });

        rowTitleText.setLayoutParams(itemParams);

        rowTitleText.setText(p.getFirstName()+" "+p.getLastName());
        tableRow.addView(rowTitleText);
        tableLayout.addView(tableRow);

    }

}
private void openPatientDetails(Patient p) {

        graph.setSurveysList(p.getSurveys());
    openPatientSurveysList(p.getSurveys());

}
 private void openPatientSurveysList(ArrayList<Survey> surveys)
 {
     TableLayout.LayoutParams rowParams =
             new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT, 1f);

     TableRow.LayoutParams itemParams =
             new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                     TableRow.LayoutParams.FILL_PARENT, 1f);


     TableLayout tableLayout = (TableLayout)rootView.findViewById(R.id.patientsSurveysTable);
     tableLayout.removeAllViews();
     for (int i = 0; i <surveys.size(); i++) {
         TableRow tableRow = new TableRow(getActivity());
         tableRow.setLayoutParams(rowParams);

         TextView rowTitleText = new TextView(getActivity());
         rowTitleText.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                // openPatientDetails(p);
             }
         });

         rowTitleText.setLayoutParams(itemParams);

         rowTitleText.setText(surveys.get(i).getDateText());
         tableRow.addView(rowTitleText);
         tableLayout.addView(tableRow);

     }
 }


}