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
import android.widget.ListView;
import android.widget.Toast;

import com.example.cs571hw9.model.Committee;
import com.example.cs571hw9.parser.CommitteeJSONParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommitteesHouseFragment extends Fragment {

    public static final String ITEM_KEY = "item_key_committee_house";

    private View view;
    private List<Committee> committeeList;

    public CommitteesHouseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_committees_house, container, false);

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
        p.setParams("api", "committees");

        MyTask task = new MyTask();
        task.execute(p);
    }

    protected void updateDisplay() {
        CommitteesListAdapter adapter = new CommitteesListAdapter(
                this.getActivity(), R.layout.list_item_committees, committeeList);
        ListView lv = (ListView) view.findViewById(R.id.listView_committees_house);
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

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private class MyTask extends AsyncTask<RequestPackage, String, List<Committee>> {

        @Override
        protected List<Committee> doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            committeeList = CommitteeJSONParser.parseFeed(content);

            committeeList = filterHouse(committeeList);

            Collections.sort(committeeList, new Comparator<Committee>() {
                @Override
                public int compare(Committee c1, Committee c2) {
                    return c1.getCommitteeName().compareTo(c2.getCommitteeName());
                }
            });

            return committeeList;
        }

        @Override
        protected void onPostExecute(List<Committee> result) {
            if (result == null) {
                Toast.makeText(getActivity(), "Can't connect to web service", Toast.LENGTH_LONG).show();
                return;
            }
            updateDisplay();
        }

        private List<Committee> filterHouse(List<Committee> committeeList) {
            if (committeeList == null) return null;
            List<Committee> result = new ArrayList<>();
            for (Committee committee : committeeList) {
                if (committee.getChamber().equals("house")) {
                    result.add(committee);
                }
            }
            return result;
        }
    }
}
