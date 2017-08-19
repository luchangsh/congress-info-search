package com.example.cs571hw9;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FavoritesPagerAdapter extends FragmentStatePagerAdapter {

    public FavoritesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new FavoritesLegislatorsFragment();
                break;
            case 1:
                frag = new FavoritesBillsFragment();
                break;
            case 2:
                frag = new FavoritesCommitteesFragment();
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
                title = "LEGISLATORS";
                break;
            case 1:
                title = "BILLS";
                break;
            case 2:
                title = "COMMITTEES";
                break;
        }
        return title;
    }
}
