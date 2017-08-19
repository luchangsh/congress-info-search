package com.example.cs571hw9;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class BillsPagerAdapter extends FragmentStatePagerAdapter {

    public BillsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new BillsActiveFragment();
                break;
            case 1:
                frag = new BillsNewFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "ACTIVE BILLS";
                break;
            case 1:
                title = "NEW BILLS";
                break;
        }
        return title;
    }
}
