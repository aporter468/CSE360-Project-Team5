package com.porter.esas;

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

        return rootView;
    }
private void makePatientsTable()
{

    TableLayout.LayoutParams rowParams =
            new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT, 1f);

    TableRow.LayoutParams itemParams =
            new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.FILL_PARENT, 1f);


    TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.patientsTable);
    tableLayout.removeAllViews();
    for (int i = 0; i <patientsList.size(); i++) {
        TableRow tableRow = new TableRow(getActivity());
        tableRow.setLayoutParams(rowParams);

        TextView rowTitleText = new TextView(getActivity());
        rowTitleText.setLayoutParams(itemParams);
        Patient p = patientsList.get(i);
        rowTitleText.setText(p.getFirstName()+" "+p.getLastName());
        tableRow.addView(rowTitleText);
        tableLayout.addView(tableRow);

    }

}


}