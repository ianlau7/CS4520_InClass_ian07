package com.example.practice;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {

    String name, email, platform, mood;
    int avatarId;

    public Profile(String name, String email, String platform, String mood, int avatarId) {
        this.name = name;
        this.email = email;
        this.platform = platform;
        this.mood = mood;
        this.avatarId = avatarId;
    }

    protected Profile(Parcel in) {
        name = in.readString();
        email = in.readString();
        platform = in.readString();
        mood = in.readString();
        avatarId = in.readInt();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(platform);
        dest.writeString(mood);
        dest.writeInt(avatarId);
    }
}
