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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cs571hw9.model.Legislator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesLegislatorsFragment extends Fragment {

    public static final String ITEM_KEY = "item_key_favorites_legislator";

    private View view;
    private List<Legislator> legislatorList;
    private ListView lv;
    private Map<String, Integer> mapIndex;


    public FavoritesLegislatorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favorites_legislators, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences(MainActivity.MY_GLOBAL_PREFS, Context.MODE_PRIVATE);
        Set<String> favorLegislators = prefs.getStringSet(MainActivity.PREFS_LEGISLATORS, new HashSet<String>());

        if (favorLegislators.size() != 0) {
            String[] favorLegislatorsArray = favorLegislators.toArray(new String[favorLegislators.size()]);

            Gson gson = new Gson();
            legislatorList = new ArrayList<>();
            for (int i = 0; i < favorLegislatorsArray.length; i++) {
                Legislator obj = gson.fromJson(favorLegislatorsArray[i], Legislator.class);
                legislatorList.add(obj);
            }

            Collections.sort(legislatorList, new Comparator<Legislator>() {
                @Override
                public int compare(Legislator l1, Legislator l2) {
                    return l1.getLastName().compareTo(l2.getLastName());
                }
            });

            LegislatorsListAdapter adapter = new LegislatorsListAdapter(
                    this.getActivity(), R.layout.list_item_legislators, legislatorList);
            lv = (ListView) view.findViewById(R.id.listView_favorites_legislators);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), DetailsLegislatorsActivity.class);
                    intent.putExtra(ITEM_KEY, legislatorList.get(i));
                    startActivity(intent);
                }
            });

            getIndexList(legislatorList);

            displayIndex();
        }

        return view;
    }

    private void getIndexList(List<Legislator> legislatorList) {
        mapIndex = new LinkedHashMap<>();
        for (int i = 0; i < legislatorList.size(); i++) {
            String legislatorLastName = legislatorList.get(i).getLastName();
            String index = legislatorLastName.substring(0, 1);
            if (mapIndex.get(index) == null) {
                mapIndex.put(index, i);
            }
        }
    }

    private void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) view.findViewById(R.id.side_index_favorites_legislators);
        TextView textView;
        List<String> indexList = new ArrayList<>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView selectedIndex = (TextView) view;
                    lv.setSelection(mapIndex.get(selectedIndex.getText()));
                }
            });
            indexLayout.addView(textView);
        }
    }

}
