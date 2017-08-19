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

import com.example.cs571hw9.model.Bill;
import com.example.cs571hw9.parser.BillJSONParser;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillsNewFragment extends Fragment {

    public static final String ITEM_KEY = "item_key_bill_new";

    private View view;
    private List<Bill> billList;

    public BillsNewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bills_new, container, false);

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
        p.setParams("api", "bills");
        p.setParams("history_active", "false");

        MyTask task = new MyTask();
        task.execute(p);
    }

    protected void updateDisplay() {
        BillsListAdapter adapter = new BillsListAdapter(
                this.getActivity(), R.layout.list_item_bills, billList);
        ListView lv = (ListView) view.findViewById(R.id.listView_bills_new);
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

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private class MyTask extends AsyncTask<RequestPackage, String, List<Bill>> {

        @Override
        protected List<Bill> doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            billList = BillJSONParser.parseFeed(content);

            Collections.sort(billList, new Comparator<Bill>() {
                @Override
                public int compare(Bill b1, Bill b2) {
                    return b2.getIntroducedOn().compareTo(b1.getIntroducedOn());
                }
            });

            return billList;
        }

        @Override
        protected void onPostExecute(List<Bill> result) {
            if (result == null) {
                Toast.makeText(getActivity(), "Can't connect to web service", Toast.LENGTH_LONG).show();
                return;
            }
            updateDisplay();
        }
    }


}
