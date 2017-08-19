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

import com.example.cs571hw9.model.Bill;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class DetailsBillsActivity extends AppCompatActivity {

    private TextView tvId, tvTitle, tvType, tvSponsor, tvChamber, tvStatus, tvIntroOn,
            tvCongressUrl, tvVersionStatus, tvBillUrl;

    private ImageView ivFavorite;

    private Bill bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_bills);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras().getParcelable(BillsActiveFragment.ITEM_KEY) != null) {
            bill = getIntent().getExtras().getParcelable(BillsActiveFragment.ITEM_KEY);
        } else if (getIntent().getExtras().getParcelable(BillsNewFragment.ITEM_KEY) != null) {
            bill = getIntent().getExtras().getParcelable(BillsNewFragment.ITEM_KEY);
        } else if (getIntent().getExtras().getParcelable(FavoritesBillsFragment.ITEM_KEY) != null) {
            bill = getIntent().getExtras().getParcelable(FavoritesBillsFragment.ITEM_KEY);
        } else {
            Toast.makeText(this, "No data!", Toast.LENGTH_LONG).show();
            return;
        }

        tvId = (TextView) findViewById(R.id.textView_info_bill_id);
        tvTitle = (TextView) findViewById(R.id.textView_info_bill_title);
        tvType = (TextView) findViewById(R.id.textView_info_bill_type);
        tvSponsor = (TextView) findViewById(R.id.textView_info_bill_sponsor);
        tvChamber = (TextView) findViewById(R.id.textView_info_bill_chamber);
        tvStatus = (TextView) findViewById(R.id.textView_info_bill_status);
        tvIntroOn = (TextView) findViewById(R.id.textView_info_bill_intro_on);
        tvCongressUrl = (TextView) findViewById(R.id.textView_info_bill_congress_url);
        tvVersionStatus = (TextView) findViewById(R.id.textView_info_bill_version_status);
        tvBillUrl = (TextView) findViewById(R.id.textView_info_bill_url);

        tvId.setText(bill.getBillId().toUpperCase());
        tvTitle.setText(bill.getTitle());
        tvType.setText(bill.getBillType().toUpperCase());
        tvSponsor.setText(bill.getSponsorTitle() + ". " + bill.getSponsorLastName() + ", " + bill.getSponsorFirstName());
        tvChamber.setText(MyUtility.capitalize(bill.getChamber()));

        switch (bill.getStatus()) {
            case "true":
                tvStatus.setText("Active");
                break;
            case "false":
                tvStatus.setText("New");
                break;
            case "N.A":
                tvStatus.setText("N.A");
                break;
        }
        tvIntroOn.setText(MyUtility.formatDate(bill.getIntroducedOn()));
        tvCongressUrl.setText(bill.getCongressUrl());
        tvVersionStatus.setText(bill.getVersionName());
        tvBillUrl.setText(bill.getPdfUrl());

        ivFavorite = (ImageView) findViewById(R.id.imageView_info_bill_favorite);
        if (isInFavorites(bill.getBillId())) {
            ivFavorite.setImageResource(R.drawable.star);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_action_star_open);
        }
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInFavorites(bill.getBillId())) {
                    removeFromFavorite();
                } else {
                    addIntoFavorite();
                }
            }
        });

    }
    private void addIntoFavorite() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);
        Set<String> favorBills = prefs.getStringSet(MainActivity.PREFS_BILLS, new HashSet<String>());
        Set<String> favorBillsId = prefs.getStringSet(MainActivity.PREFS_BILLS_ID, new HashSet<String>());

        Gson gson = new Gson();
        String billJSON = gson.toJson(bill);
        String billId = bill.getBillId();

        favorBills.add(billJSON);
        favorBillsId.add(billId);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(MainActivity.PREFS_BILLS, favorBills);
        editor.putStringSet(MainActivity.PREFS_BILLS_ID, favorBillsId);
        editor.apply();

        ivFavorite.setImageResource(R.drawable.star);
    }

    private void removeFromFavorite() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);
        Set<String> favorBills = prefs.getStringSet(MainActivity.PREFS_BILLS, new HashSet<String>());
        Set<String> favorBillsId = prefs.getStringSet(MainActivity.PREFS_BILLS_ID, new HashSet<String>());

        Gson gson = new Gson();
        String billJSON = gson.toJson(bill);
        String billId = bill.getBillId();

        favorBills.remove(billJSON);
        favorBillsId.remove(billId);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(MainActivity.PREFS_BILLS, favorBills);
        editor.putStringSet(MainActivity.PREFS_BILLS_ID, favorBillsId);
        editor.apply();

        ivFavorite.setImageResource(R.drawable.ic_action_star_open);
    }

    private boolean isInFavorites(String id) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);
        Set<String> favorBillsId = prefs.getStringSet(MainActivity.PREFS_BILLS_ID, new HashSet<String>());
        return favorBillsId.contains(id);
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