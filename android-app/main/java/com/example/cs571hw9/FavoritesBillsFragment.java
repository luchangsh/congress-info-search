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

import com.example.cs571hw9.model.Bill;
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
public class FavoritesBillsFragment extends Fragment {

    public static final String ITEM_KEY = "item_key_favorites_bill";

    private List<Bill> billList;

    public FavoritesBillsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites_bills, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences(MainActivity.MY_GLOBAL_PREFS, Context.MODE_PRIVATE);
        Set<String> favorBills = prefs.getStringSet(MainActivity.PREFS_BILLS, new HashSet<String>());

        if (favorBills.size() != 0) {
            String[] favorBillsArray = favorBills.toArray(new String[favorBills.size()]);

            Gson gson = new Gson();
            billList = new ArrayList<>();
            for (int i = 0; i < favorBillsArray.length; i++) {
                Bill obj = gson.fromJson(favorBillsArray[i], Bill.class);
                billList.add(obj);
            }

            Collections.sort(billList, new Comparator<Bill>() {
                @Override
                public int compare(Bill b1, Bill b2) {
                    return b2.getIntroducedOn().compareTo(b1.getIntroducedOn());
                }
            });

            BillsListAdapter adapter = new BillsListAdapter(
                    this.getActivity(), R.layout.list_item_bills, billList);
            ListView lv = (ListView) view.findViewById(R.id.listView_favorites_bills);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), DetailsBillsActivity.class);
                    intent.putExtra(ITEM_KEY, billList.get(i));
                    startActivity(intent);
                }
            });
        }

        return view;
    }

}
