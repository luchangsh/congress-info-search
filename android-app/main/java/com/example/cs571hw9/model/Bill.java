package com.example.cs571hw9.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Bill implements Parcelable {

    private String billId;
    private String title;
    private String introducedOn;
    private String billType;
    private String sponsorFirstName;
    private String sponsorLastName;
    private String sponsorTitle;
    private String chamber;
    private String status;
    private String congressUrl;
    private String versionName;
    private String pdfUrl;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroducedOn() {
        return introducedOn;
    }

    public void setIntroducedOn(String introducedOn) {
        this.introducedOn = introducedOn;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getSponsorFirstName() {
        return sponsorFirstName;
    }

    public void setSponsorFirstName(String sponsorFirstName) {
        this.sponsorFirstName = sponsorFirstName;
    }

    public String getSponsorLastName() {
        return sponsorLastName;
    }

    public void setSponsorLastName(String sponsorLastName) {
        this.sponsorLastName = sponsorLastName;
    }

    public String getSponsorTitle() {
        return sponsorTitle;
    }

    public void setSponsorTitle(String sponsorTitle) {
        this.sponsorTitle = sponsorTitle;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCongressUrl() {
        return congressUrl;
    }

    public void setCongressUrl(String congressUrl) {
        this.congressUrl = congressUrl;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.billId);
        dest.writeString(this.title);
        dest.writeString(this.introducedOn);
        dest.writeString(this.billType);
        dest.writeString(this.sponsorFirstName);
        dest.writeString(this.sponsorLastName);
        dest.writeString(this.sponsorTitle);
        dest.writeString(this.chamber);
        dest.writeString(this.status);
        dest.writeString(this.congressUrl);
        dest.writeString(this.versionName);
        dest.writeString(this.pdfUrl);
    }

    public Bill() {
    }

    protected Bill(Parcel in) {
        this.billId = in.readString();
        this.title = in.readString();
        this.introducedOn = in.readString();
        this.billType = in.readString();
        this.sponsorFirstName = in.readString();
        this.sponsorLastName = in.readString();
        this.sponsorTitle = in.readString();
        this.chamber = in.readString();
        this.status = in.readString();
        this.congressUrl = in.readString();
        this.versionName = in.readString();
        this.pdfUrl = in.readString();
    }

    public static final Parcelable.Creator<Bill> CREATOR = new Parcelable.Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel source) {
            return new Bill(source);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };
}
