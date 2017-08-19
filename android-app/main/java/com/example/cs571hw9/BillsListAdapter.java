package com.example.cs571hw9;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cs571hw9.model.Bill;

import java.util.List;

public class BillsListAdapter extends ArrayAdapter {

    private List<Bill> bills;

    public BillsListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        bills = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_bills, parent, false);
        }

        Bill bill = bills.get(position);

        TextView tvBillId = (TextView) convertView.findViewById(R.id.textView_bills_Id);
        tvBillId.setText(bill.getBillId().toUpperCase());

        TextView tvBillTitle = (TextView) convertView.findViewById(R.id.textView_bills_short_title);
        tvBillTitle.setText(bill.getTitle());

        TextView tvBillIntro = (TextView) convertView.findViewById(R.id.textView_bills_introduced_on);
        tvBillIntro.setText(MyUtility.formatDate(bill.getIntroducedOn()));

        return convertView;
    }
}
