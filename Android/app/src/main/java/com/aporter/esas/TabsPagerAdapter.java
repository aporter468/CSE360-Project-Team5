package com.aporter.esas;

import com.aporter.esas.HistoryFragment;
import com.aporter.esas.SurveyFragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private HistoryFragment historyFragment;
    private SurveyFragment surveyFragment;

    private MainActivity mainActivity;

    public TabsPagerAdapter(FragmentManager fm, MainActivity mainActivity) {
        super(fm);
       historyFragment = new HistoryFragment();
        surveyFragment = new SurveyFragment();
        this.mainActivity = mainActivity;

    }

    @Override
    public Fragment getItem(int index) {
        Fragment fragment;
        double rand =   Math.random();

        switch (index) {
            case 0:
                return historyFragment;
            case 1:
                return surveyFragment;

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}