package com.example.cs571hw9;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cs571hw9.model.Committee;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesCommitteesFragment extends Fragment {

    public static final String ITEM_KEY = "item_key_favorites_committee";

    private List<Committee> committeeList;

    public FavoritesCommitteesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites_committees, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences(MainActivity.MY_GLOBAL_PREFS, Context.MODE_PRIVATE);
        Set<String> favorCommittees = prefs.getStringSet(MainActivity.PREFS_COMMITTEES, new HashSet<String>());

        if (favorCommittees.size() != 0) {
            String[] favorCommitteesArray = favorCommittees.toArray(new String[favorCommittees.size()]);

            Gson gson = new Gson();
            committeeList = new ArrayList<>();
            for (int i = 0; i < favorCommitteesArray.length; i++) {
                Committee obj = gson.fromJson(favorCommitteesArray[i], Committee.class);
                committeeList.add(obj);
            }

            Collections.sort(committeeList, new Comparator<Committee>() {
                @Override
                public int compare(Committee c1, Committee c2) {
                    return c1.getCommitteeName().compareTo(c2.getCommitteeName());
                }
            });

            CommitteesListAdapter adapter = new CommitteesListAdapter(
                    this.getActivity(), R.layout.list_item_committees, committeeList);
            ListView lv = (ListView) view.findViewById(R.id.listView_favorites_committees);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), DetailsCommitteesActivity.class);
                    intent.putExtra(ITEM_KEY, committeeList.get(i));
                    startActivity(intent);
                }
            });
        }

        return view;
    }

}
