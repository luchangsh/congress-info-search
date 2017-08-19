package com.example.cs571hw9.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Committee implements Parcelable {

    private String committeeId;
    private String committeeName;
    private String chamber;
    private String parentCommittee;
    private String contact;
    private String office;

    public String getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(String committeeId) {
        this.committeeId = committeeId;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getParentCommittee() {
        return parentCommittee;
    }

    public void setParentCommittee(String parentCommittee) {
        this.parentCommittee = parentCommittee;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.committeeId);
        dest.writeString(this.committeeName);
        dest.writeString(this.chamber);
        dest.writeString(this.parentCommittee);
        dest.writeString(this.contact);
        dest.writeString(this.office);
    }

    public Committee() {
    }

    protected Committee(Parcel in) {
        this.committeeId = in.readString();
        this.committeeName = in.readString();
        this.chamber = in.readString();
        this.parentCommittee = in.readString();
        this.contact = in.readString();
        this.office = in.readString();
    }

    public static final Parcelable.Creator<Committee> CREATOR = new Parcelable.Creator<Committee>() {
        @Override
        public Committee createFromParcel(Parcel source) {
            return new Committee(source);
        }

        @Override
        public Committee[] newArray(int size) {
            return new Committee[size];
        }
    };
}
