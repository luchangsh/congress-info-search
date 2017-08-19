package com.example.cs571hw9.parser;

import com.example.cs571hw9.model.Legislator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LegislatorJSONParser {

    public static List<Legislator> parseFeed(String content) {

        try {
            JSONObject jsonObject = new JSONObject(content);
            JSONArray arr = jsonObject.getJSONArray("results");
            List<Legislator> legislatorList = new ArrayList<>();

            for (int i = 0; i < arr.length(); i++) {

                JSONObject obj = arr.getJSONObject(i);
                Legislator legislator = new Legislator();

                legislator.setBioguideId(obj.isNull("bioguide_id") ? "N.A" : obj.getString("bioguide_id"));
                legislator.setFirstName(obj.isNull("first_name") ? "N.A" : obj.getString("first_name"));
                legislator.setLastName(obj.isNull("last_name") ? "N.A" : obj.getString("last_name"));
                legislator.setParty(obj.isNull("party") ? "N.A" : obj.getString("party"));
                legislator.setStateName(obj.isNull("state_name") ? "N.A" : obj.getString("state_name"));
                legislator.setDistrict(obj.isNull("district") ? "0" : obj.getString("district"));
                legislator.setFacebookId(obj.isNull("facebook_id") ? "N.A" : obj.getString("facebook_id"));
                legislator.setTwitterId(obj.isNull("twitter_id") ? "N.A" : obj.getString("twitter_id"));
                legislator.setWebsite(obj.isNull("website") ? "N.A" : obj.getString("website"));
                legislator.setTitle(obj.isNull("title") ? "N.A" : obj.getString("title"));
                legislator.setOcEmail(obj.isNull("oc_email") ? "N.A" : obj.getString("oc_email"));
                legislator.setChamber(obj.isNull("chamber") ? "N.A" : obj.getString("chamber"));
                legislator.setPhone(obj.isNull("phone") ? "N.A" : obj.getString("phone"));
                legislator.setTermStart(obj.isNull("term_start") ? "N.A" : obj.getString("term_start"));
                legislator.setTermEnd(obj.isNull("term_end") ? "N.A" : obj.getString("term_end"));
                legislator.setOffice(obj.isNull("office") ? "N.A" : obj.getString("office"));
                legislator.setState(obj.isNull("state") ? "N.A" : obj.getString("state"));
                legislator.setFax(obj.isNull("fax") ? "N.A" : obj.getString("fax"));
                legislator.setBirthday(obj.isNull("birthday") ? "N.A" : obj.getString("birthday"));

                legislatorList.add(legislator);
            }

            return legislatorList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

}
