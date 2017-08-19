package com.example.cs571hw9;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs571hw9.model.Committee;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class DetailsCommitteesActivity extends AppCompatActivity {

    private TextView tvId, tvName, tvChamber, tvParent, tvContact, tvOffice;

    private ImageView ivFavorite, ivChamber;

    private  Committee committee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_committees);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras().getParcelable(CommitteesHouseFragment.ITEM_KEY) != null) {
            committee = getIntent().getExtras().getParcelable(CommitteesHouseFragment.ITEM_KEY);
        } else if (getIntent().getExtras().getParcelable(CommitteesSenateFragment.ITEM_KEY) != null) {
            committee = getIntent().getExtras().getParcelable(CommitteesSenateFragment.ITEM_KEY);
        } else if (getIntent().getExtras().getParcelable(CommitteesJointFragment.ITEM_KEY) != null) {
            committee = getIntent().getExtras().getParcelable(CommitteesJointFragment.ITEM_KEY);
        } else if (getIntent().getExtras().getParcelable(FavoritesCommitteesFragment.ITEM_KEY) != null) {
            committee = getIntent().getExtras().getParcelable(FavoritesCommitteesFragment.ITEM_KEY);
        } else {
            Toast.makeText(this, "No data!", Toast.LENGTH_LONG).show();
            return;
        }

        tvId = (TextView) findViewById(R.id.textView_info_committee_id);
        tvName = (TextView) findViewById(R.id.textView_info_committee_name);
        tvChamber = (TextView) findViewById(R.id.textView_info_committee_chamber);
        tvParent = (TextView) findViewById(R.id.textView_info_committee_parent_comm);
        tvContact = (TextView) findViewById(R.id.textView_info_committee_contact);
        tvOffice = (TextView) findViewById(R.id.textView_info_committee_office);

        tvId.setText(committee.getCommitteeId());
        tvName.setText(committee.getCommitteeName());
        tvChamber.setText(MyUtility.capitalize(committee.getChamber()));
        tvParent.setText(committee.getParentCommittee());
        tvContact.setText(committee.getContact());
        tvOffice.setText(committee.getOffice());

        ivChamber = (ImageView) findViewById(R.id.imageView_info_committee_chamber);
        if (committee.getChamber().equals("house")) {
            ivChamber.setImageResource(R.drawable.house);
        } else {
            ivChamber.setImageResource(R.drawable.senate);
        }

        ivFavorite = (ImageView) findViewById(R.id.imageView_info_committee_favorite);
        if (isInFavorites(committee.getCommitteeId())) {
            ivFavorite.setImageResource(R.drawable.star);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_action_star_open);
        }
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInFavorites(committee.getCommitteeId())) {
                    removeFromFavorite();
                } else {
                    addIntoFavorite();
                }
            }
        });

    }
    private void addIntoFavorite() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);
        Set<String> favorCommittees = prefs.getStringSet(MainActivity.PREFS_COMMITTEES, new HashSet<String>());
        Set<String> favorCommitteesId = prefs.getStringSet(MainActivity.PREFS_COMMITTEES_ID, new HashSet<String>());

        Gson gson = new Gson();
        String CommitteeJSON = gson.toJson(committee);
        String CommitteeId = committee.getCommitteeId();

        favorCommittees.add(CommitteeJSON);
        favorCommitteesId.add(CommitteeId);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(MainActivity.PREFS_COMMITTEES, favorCommittees);
        editor.putStringSet(MainActivity.PREFS_COMMITTEES_ID, favorCommitteesId);
        editor.apply();

        ivFavorite.setImageResource(R.drawable.star);
    }

    private void removeFromFavorite() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);
        Set<String> favorCommittees = prefs.getStringSet(MainActivity.PREFS_COMMITTEES, new HashSet<String>());
        Set<String> favorCommitteesId = prefs.getStringSet(MainActivity.PREFS_COMMITTEES_ID, new HashSet<String>());

        Gson gson = new Gson();
        String CommitteeJSON = gson.toJson(committee);
        String CommitteeId = committee.getCommitteeId();

        favorCommittees.remove(CommitteeJSON);
        favorCommitteesId.remove(CommitteeId);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(MainActivity.PREFS_COMMITTEES, favorCommittees);
        editor.putStringSet(MainActivity.PREFS_COMMITTEES_ID, favorCommitteesId);
        editor.apply();

        ivFavorite.setImageResource(R.drawable.ic_action_star_open);
    }

    private boolean isInFavorites(String id) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);
        Set<String> favorCommitteesId = prefs.getStringSet(MainActivity.PREFS_COMMITTEES_ID, new HashSet<String>());
        return favorCommitteesId.contains(id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }

}
