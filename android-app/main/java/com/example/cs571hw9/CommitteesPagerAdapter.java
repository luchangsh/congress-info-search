package com.example.cs571hw9;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CommitteesPagerAdapter extends FragmentStatePagerAdapter {

    public CommitteesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new CommitteesHouseFragment();
                break;
            case 1:
                frag = new CommitteesSenateFragment();
                break;
            case 2:
                frag = new CommitteesJointFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "HOUSE";
                break;
            case 1:
                title = "SENATE";
                break;
            case 2:
                title = "JOINT";
                break;
        }
        return title;
    }
}
