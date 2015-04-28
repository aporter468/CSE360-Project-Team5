package com.porter.esas;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryFragment extends Fragment {


    public static HistoryFragment newInstance(int userType) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment() {
    }
View rootView;
    boolean isSet;
    GraphView graph;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_history, container, false);
        ((MainActivity)getActivity()).setupHistoryTable();
        isSet = true;
        graph =new GraphView((MainActivity)getActivity(),new ArrayList<Survey>());

        RelativeLayout graphViewLayout =  (RelativeLayout)(rootView.findViewById(R.id.surveyGraphContainer));
        graphViewLayout.addView(graph);
        graphViewLayout.setBackgroundColor(Color.parseColor("#ffffff"));
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
        mTextView.setText("Date: "+selectedDate[1]+"/"+selectedDate[2]+"/"+selectedDate[0]);
        ArrayList<Survey> dateMatches = new ArrayList<Survey>();
        for(int i =0; i<surveys.size(); i++)
        {
            Calendar c = surveys.get(i).getCalendar();
            if(selectedDate[0] == c.get(Calendar.YEAR) && selectedDate[1] == c.get(Calendar.MONTH) && selectedDate[2] == c.get(Calendar.DAY_OF_MONTH))
            {
                dateMatches.add(surveys.get(i));
            }
        }

       //show first (= most recent survey for that day) in top table
        TableLayout.LayoutParams rowParams =
                new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT, 1f);

        TableRow.LayoutParams itemParams =
                new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.FILL_PARENT, 1f);


            TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.selectedSurveyTable);
tableLayout.removeAllViews();
            for (int i = 0; i < Survey.NUM_SURVEY_FIELDS; i++) {
                TableRow tableRow = new TableRow(getActivity());
                tableRow.setLayoutParams(rowParams);
                tableRow.setBackgroundResource(R.drawable.row_border);
                TextView rowTitleText = new TextView(getActivity());
                rowTitleText.setLayoutParams(itemParams);
                rowTitleText.setText(Survey.SURVEY_FIELDS[i]);
                rowTitleText.setTextColor(Color.BLACK);
                Resources res = getResources();
                int color = res.getColor(getResources().getIdentifier("color"+i, "color", getActivity().getPackageName()));
                rowTitleText.setBackgroundColor(color);
                tableRow.addView(rowTitleText);
                for (int column = dateMatches.size()-1; column >-1; column--) {

                    TextView textView = new TextView(getActivity());
                    textView.setLayoutParams(itemParams);
                    textView.setText("" + dateMatches.get(column).getSurveyValues()[i]);
                    tableRow.addView(textView);
                }
                tableLayout.addView(tableRow);

            }

        }

public void setGraphSurveys(ArrayList<Survey> surveys)
{
    graph.setSurveysList(surveys);
}
}