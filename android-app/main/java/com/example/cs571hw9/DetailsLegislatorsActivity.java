package com.example.cs571hw9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs571hw9.model.Legislator;
import com.google.gson.Gson;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DetailsLegislatorsActivity extends AppCompatActivity {

    private static String facebookBaseUrl = "https://www.facebook.com/";
    private static String twitterBaseUrl = "https://twitter.com/";

    private TextView tvParty, tvName, tvEmail, tvChamber, tvContact, tvStartTerm, tvEndTerm,
            tvOffice, tvState, tvFax, tvBirthday;

    private ImageView ivFavorite, ivFacebook, ivTwitter, ivWebsite, ivPhoto, ivParty;

    private Legislator legislator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_legislators);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras().getParcelable(LegislatorsStateFragment.ITEM_KEY) != null) {
            legislator = getIntent().getExtras().getParcelable(LegislatorsStateFragment.ITEM_KEY);
        } else if (getIntent().getExtras().getParcelable(LegislatorsHouseFragment.ITEM_KEY) != null) {
            legislator = getIntent().getExtras().getParcelable(LegislatorsHouseFragment.ITEM_KEY);
        } else if (getIntent().getExtras().getParcelable(LegislatorsSenateFragment.ITEM_KEY) != null) {
            legislator = getIntent().getExtras().getParcelable(LegislatorsSenateFragment.ITEM_KEY);
        } else if (getIntent().getExtras().getParcelable(FavoritesLegislatorsFragment.ITEM_KEY) != null) {
            legislator = getIntent().getExtras().getParcelable(FavoritesLegislatorsFragment.ITEM_KEY);
        } else {
            Toast.makeText(this, "No data!", Toast.LENGTH_LONG).show();
            return;
        }

        tvParty = (TextView) findViewById(R.id.textView_info_legislator_party);
        tvName = (TextView) findViewById(R.id.textView_info_legislator_name);
        tvEmail = (TextView) findViewById(R.id.textView_info_legislator_email);
        tvChamber = (TextView) findViewById(R.id.textView_info_legislator_chamber);
        tvContact = (TextView) findViewById(R.id.textView_info_legislator_contact);
        tvStartTerm = (TextView) findViewById(R.id.textView_info_legislator_start_term);
        tvEndTerm = (TextView) findViewById(R.id.textView_info_legislator_end_term);
        tvOffice = (TextView) findViewById(R.id.textView_info_legislator_office);
        tvState = (TextView) findViewById(R.id.textView_info_legislator_state);
        tvFax = (TextView) findViewById(R.id.textView_info_legislator_fax);
        tvBirthday = (TextView) findViewById(R.id.textView_info_legislator_birthday);

        switch (legislator.getParty()) {
            case "R":
                tvParty.setText("Repulican");
                break;
            case "D":
                tvParty.setText("Democrat");
                break;
            case "I":
                tvParty.setText("Independent");
                break;
        }

        tvName.setText(legislator.getTitle() + ". " + legislator.getLastName() + ", " + legislator.getFirstName());
        tvEmail.setText(legislator.getOcEmail());
        tvChamber.setText(MyUtility.capitalize(legislator.getChamber()));
        tvContact.setText(legislator.getPhone());
        tvStartTerm.setText(MyUtility.formatDate(legislator.getTermStart()));
        tvEndTerm.setText(MyUtility.formatDate(legislator.getTermEnd()));
        tvOffice.setText(legislator.getOffice());
        tvState.setText(legislator.getState());
        tvFax.setText(legislator.getFax());
        tvBirthday.setText(MyUtility.formatDate(legislator.getBirthday()));

        ProgressBar pbTerm= (ProgressBar) findViewById(R.id.progressBar_info_legislator_term);
        TextView tvTerm = (TextView) findViewById(R.id.textView_info_legislator_term);
        int termPercent = calculateTerm(legislator.getTermStart(), legislator.getTermEnd());
        pbTerm.setProgress(termPercent);
        tvTerm.setText(termPercent + "%");

        ivPhoto = (ImageView) findViewById(R.id.imageView_info_legislator_photo);
        ivPhoto.setImageBitmap(legislator.getBitmap());

        ivParty = (ImageView) findViewById(R.id.imageView_info_legislator_party);
        switch (legislator.getParty()) {
            case "R":
                ivParty.setImageResource(R.drawable.republican);
                break;
            case "D":
                ivParty.setImageResource(R.drawable.democrate);
                break;
            case "I":
                ivParty.setImageResource(R.drawable.independent);
                break;
        }

        ivFacebook = (ImageView) findViewById(R.id.imageView_info_legislator_facebook);
        ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String facebookId = legislator.getFacebookId();
                if (facebookId.equals("N.A")) {
                    Toast.makeText(view.getContext(), "Facebook page not found!", Toast.LENGTH_SHORT).show();
                } else {
                    String url = facebookBaseUrl + facebookId;
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (webIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(webIntent);
                    }
                }
            }
        });

        ivTwitter = (ImageView) findViewById(R.id.imageView_info_legislator_twitter);
        ivTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String twitterId = legislator.getTwitterId();
                if (twitterId.equals("N.A")) {
                    Toast.makeText(view.getContext(), "Twitter page not found!", Toast.LENGTH_SHORT).show();
                } else {
                    String url = twitterBaseUrl + twitterId;
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (webIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(webIntent);
                    }
                }
            }
        });

        ivWebsite = (ImageView) findViewById(R.id.imageView_info_legislator_website);
        ivWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String webUrl = legislator.getWebsite();
                if (webUrl.equals("N.A")) {
                    Toast.makeText(view.getContext(), "Website page not found!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                    if (webIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(webIntent);
                    }
                }
            }
        });

        ivFavorite = (ImageView) findViewById(R.id.imageView_info_legislator_favorite);
        if (isInFavorites(legislator.getBioguideId())) {
            ivFavorite.setImageResource(R.drawable.star);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_action_star_open);
        }
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInFavorites(legislator.getBioguideId())) {
                    removeFromFavorite();
                } else {
                    addIntoFavorite();
                }
            }
        });

    }

    private int calculateTerm(String termStart, String termEnd) {
        if (termStart.equals("N.A") || termEnd.equals("N.A")) return 0;
        try {
            Date dateStart = DateUtils.parseDate(termStart, "yyyy-MM-dd");
            Date dateEnd = DateUtils.parseDate(termEnd, "yyyy-MM-dd");
            Date dateNow = new Date();
            return (int) (((dateNow.getTime() - dateStart.getTime()) / (double)(dateEnd.getTime() - dateStart.getTime())) * 100);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void addIntoFavorite() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);
        Set<String> favorLegislators = prefs.getStringSet(MainActivity.PREFS_LEGISLATORS, new HashSet<String>());
        Set<String> favorLegislatorsId = prefs.getStringSet(MainActivity.PREFS_LEGISLATORS_ID, new HashSet<String>());

        legislator.setBitmap(null); //not save photo to favorite legislator

        Gson gson = new Gson();
        String legislatorJSON = gson.toJson(legislator);
        String legislatorId = legislator.getBioguideId();

        favorLegislators.add(legislatorJSON);
        favorLegislatorsId.add(legislatorId);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(MainActivity.PREFS_LEGISLATORS, favorLegislators);
        editor.putStringSet(MainActivity.PREFS_LEGISLATORS_ID, favorLegislatorsId);
        editor.apply();

        ivFavorite.setImageResource(R.drawable.star);
    }

    private void removeFromFavorite() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);
        Set<String> favorLegislators = prefs.getStringSet(MainActivity.PREFS_LEGISLATORS, new HashSet<String>());
        Set<String> favorLegislatorsId = prefs.getStringSet(MainActivity.PREFS_LEGISLATORS_ID, new HashSet<String>());

        legislator.setBitmap(null); //not save photo to favorite legislator

        Gson gson = new Gson();
        String legislatorJSON = gson.toJson(legislator);
        String legislatorId = legislator.getBioguideId();

        favorLegislators.remove(legislatorJSON);
        favorLegislatorsId.remove(legislatorId);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(MainActivity.PREFS_LEGISLATORS, favorLegislators);
        editor.putStringSet(MainActivity.PREFS_LEGISLATORS_ID, favorLegislatorsId);
        editor.apply();

        ivFavorite.setImageResource(R.drawable.ic_action_star_open);
    }

    private boolean isInFavorites(String id) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);
        Set<String> favorLegislatorsId = prefs.getStringSet(MainActivity.PREFS_LEGISLATORS_ID, new HashSet<String>());
        return favorLegislatorsId.contains(id);
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
