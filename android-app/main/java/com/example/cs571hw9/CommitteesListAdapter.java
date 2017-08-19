package com.example.cs571hw9;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cs571hw9.model.Committee;

import java.util.List;

public class CommitteesListAdapter extends ArrayAdapter {

    private List<Committee> committees;

    public CommitteesListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        committees = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_committees, parent, false);
        }

        Committee committee = committees.get(position);

        TextView tvCommId = (TextView) convertView.findViewById(R.id.textView_committees_Id);
        tvCommId.setText(committee.getCommitteeId());

        TextView tvCommName = (TextView) convertView.findViewById(R.id.textView_committees_name);
        tvCommName.setText(committee.getCommitteeName());

        TextView tvCommChamber = (TextView) convertView.findViewById(R.id.textView_committees_chamber);
        tvCommChamber.setText(MyUtility.capitalize(committee.getChamber()));

        return convertView;
    }
}
