package com.example.cs571hw9;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs571hw9.model.Legislator;
import com.example.cs571hw9.parser.LegislatorJSONParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class LegislatorsSenateFragment extends Fragment {

    public static final String ITEM_KEY = "item_key_legislator_senate";

    private View view;
    private List<Legislator> legislatorList;
    private ListView lv;
    private Map<String, Integer> mapIndex;


    public LegislatorsSenateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_legislators_senate, container, false);

        if (isOnline()) {
            requestData(MainActivity.BASE_URL);
        } else {
            Toast.makeText(this.getActivity(), "Network isn't available", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void requestData(String uri) {

        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(uri);
        p.setParams("api", "legislators");

        MyTask task = new MyTask();
        task.execute(p);
    }

    protected void updateDisplay() {
        LegislatorsListAdapter adapter = new LegislatorsListAdapter(
                this.getActivity(), R.layout.list_item_legislators, legislatorList);
        lv = (ListView) view.findViewById(R.id.listView_legislator_senate);
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

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
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
        LinearLayout indexLayout = (LinearLayout) view.findViewById(R.id.side_index_legislator_senate);
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

    private class MyTask extends AsyncTask<RequestPackage, String, List<Legislator>> {

        @Override
        protected List<Legislator> doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            legislatorList = LegislatorJSONParser.parseFeed(content);

            legislatorList = filterSenate(legislatorList);

            Collections.sort(legislatorList, new Comparator<Legislator>() {
                @Override
                public int compare(Legislator l1, Legislator l2) {
                    return l1.getLastName().compareTo(l2.getLastName());
                }
            });

            return legislatorList;
        }

        @Override
        protected void onPostExecute(List<Legislator> result) {
            if (result == null) {
                Toast.makeText(getActivity(), "Can't connect to web service", Toast.LENGTH_LONG).show();
                return;
            }
            updateDisplay();
        }

        private List<Legislator> filterSenate(List<Legislator> legislatorList) {
            if (legislatorList == null) return null;
            List<Legislator> result = new ArrayList<>();
            for (Legislator legislator : legislatorList) {
                if (legislator.getChamber().equals("senate")) {
                    result.add(legislator);
                }
            }
            return result;
        }

    }

}
