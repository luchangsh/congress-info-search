package com.example.cs571hw9.parser;


import com.example.cs571hw9.model.Bill;
import com.example.cs571hw9.model.Committee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommitteeJSONParser {

    public static List<Committee> parseFeed(String content) {

        try {
            JSONObject jsonObject = new JSONObject(content);
            JSONArray arr = jsonObject.getJSONArray("results");
            List<Committee> committeeList = new ArrayList<>();

            for (int i = 0; i < arr.length(); i++) {

                JSONObject obj = arr.getJSONObject(i);
                Committee committee = new Committee();

                committee.setCommitteeId(obj.isNull("committee_id") ? "N.A" : obj.getString("committee_id"));
                committee.setCommitteeName(obj.isNull("name") ? "N.A" : obj.getString("name"));
                committee.setChamber(obj.isNull("chamber") ? "N.A" : obj.getString("chamber"));
                committee.setParentCommittee(obj.isNull("parent_committee_id") ? "" : obj.getString("parent_committee_id"));
                committee.setContact(obj.isNull("phone") ? "N.A" : obj.getString("phone"));
                committee.setOffice(obj.isNull("office") ? "N.A" : obj.getString("office"));

                committeeList.add(committee);
            }

            return committeeList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}
