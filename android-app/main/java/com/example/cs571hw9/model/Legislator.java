package com.example.cs571hw9.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Legislator implements Parcelable {

    private String bioguideId;
    private String firstName;
    private String lastName;
    private String party;
    private String stateName;
    private String district;
    private String facebookId;
    private String twitterId;
    private String website;
    private String title;
    private String ocEmail;
    private String chamber;
    private String phone;
    private String termStart;
    private String termEnd;
    private String office;
    private String state;
    private String fax;
    private String birthday;
    private Bitmap bitmap;

    public String getBioguideId() {
        return bioguideId;
    }

    public void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOcEmail() {
        return ocEmail;
    }

    public void setOcEmail(String ocEmail) {
        this.ocEmail = ocEmail;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTermStart() {
        return termStart;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public String getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bioguideId);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.party);
        dest.writeString(this.stateName);
        dest.writeString(this.district);
        dest.writeString(this.facebookId);
        dest.writeString(this.twitterId);
        dest.writeString(this.website);
        dest.writeString(this.title);
        dest.writeString(this.ocEmail);
        dest.writeString(this.chamber);
        dest.writeString(this.phone);
        dest.writeString(this.termStart);
        dest.writeString(this.termEnd);
        dest.writeString(this.office);
        dest.writeString(this.state);
        dest.writeString(this.fax);
        dest.writeString(this.birthday);
        dest.writeParcelable(this.bitmap, flags);
    }

    public Legislator() {
    }

    protected Legislator(Parcel in) {
        this.bioguideId = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.party = in.readString();
        this.stateName = in.readString();
        this.district = in.readString();
        this.facebookId = in.readString();
        this.twitterId = in.readString();
        this.website = in.readString();
        this.title = in.readString();
        this.ocEmail = in.readString();
        this.chamber = in.readString();
        this.phone = in.readString();
        this.termStart = in.readString();
        this.termEnd = in.readString();
        this.office = in.readString();
        this.state = in.readString();
        this.fax = in.readString();
        this.birthday = in.readString();
        this.bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Parcelable.Creator<Legislator> CREATOR = new Parcelable.Creator<Legislator>() {
        @Override
        public Legislator createFromParcel(Parcel source) {
            return new Legislator(source);
        }

        @Override
        public Legislator[] newArray(int size) {
            return new Legislator[size];
        }
    };
}
