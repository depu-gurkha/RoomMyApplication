package com.part.roommyapplication.videoPlayer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.part.roommyapplication.Login.LoGin;
import com.part.roommyapplication.NestedRecycler.ChapterLecture;
import com.part.roommyapplication.Registration.SignUpFirstPage;
import com.part.roommyapplication.dashboardpackage.Dashboard;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs = numOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChapterLecture();
            case 1:
                return new SignUpFirstPage();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
