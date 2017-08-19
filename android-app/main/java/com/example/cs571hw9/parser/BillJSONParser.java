package com.example.cs571hw9.parser;

import com.example.cs571hw9.model.Bill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BillJSONParser {

    public static List<Bill> parseFeed(String content) {

        try {
            JSONObject jsonObject = new JSONObject(content);
            JSONArray arr = jsonObject.getJSONArray("results");
            List<Bill> billList = new ArrayList<>();

            for (int i = 0; i < arr.length(); i++) {

                JSONObject obj = arr.getJSONObject(i);
                Bill bill = new Bill();

                bill.setBillId(obj.isNull("bill_id") ? "N.A" : obj.getString("bill_id"));
                bill.setTitle(
                        obj.isNull("short_title") ? (obj.isNull("official_title") ? "N.A" : obj.getString("official_title")) : obj.getString("short_title"));
                bill.setIntroducedOn(obj.isNull("introduced_on") ? "N.A" : obj.getString("introduced_on"));
                bill.setBillType(obj.isNull("bill_type") ? "N.A" : obj.getString("bill_type"));
                bill.setSponsorFirstName(
                        obj.getJSONObject("sponsor").isNull("first_name") ? "N.A" : obj.getJSONObject("sponsor").getString("first_name"));
                bill.setSponsorLastName(
                        obj.getJSONObject("sponsor").isNull("last_name") ? "N.A" : obj.getJSONObject("sponsor").getString("last_name"));
                bill.setSponsorTitle(
                        obj.getJSONObject("sponsor").isNull("title") ? "N.A" : obj.getJSONObject("sponsor").getString("title"));
                bill.setChamber(obj.isNull("chamber") ? "N.A" : obj.getString("chamber"));
                bill.setStatus(
                        obj.getJSONObject("history").isNull("active") ? "N.A" : obj.getJSONObject("history").getString("active"));
                bill.setCongressUrl(
                        obj.getJSONObject("urls").isNull("congress") ? "N.A" : obj.getJSONObject("urls").getString("congress"));
                bill.setVersionName(
                        obj.getJSONObject("last_version").isNull("version_name") ? "N.A" : obj.getJSONObject("last_version").getString("version_name"));
                bill.setPdfUrl(
                        obj.getJSONObject("last_version").getJSONObject("urls").isNull("pdf") ?
                                "N.A" : obj.getJSONObject("last_version").getJSONObject("urls").getString("pdf"));

                billList.add(bill);
            }

            return billList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}
