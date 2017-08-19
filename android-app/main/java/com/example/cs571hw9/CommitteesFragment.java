package com.example.cs571hw9;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommitteesFragment extends Fragment {

    public CommitteesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_committees, container, false);

        getActivity().setTitle("Committees");

        ViewPager pager = (ViewPager) view.findViewById(R.id.view_pager_committees);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_committees);

        FragmentManager manager = getChildFragmentManager();
        PagerAdapter adapter = new CommitteesPagerAdapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);

        return view;
    }

}
