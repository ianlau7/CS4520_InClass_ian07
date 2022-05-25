package com.example.practice;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;

// Parcelable object class that calculates and holds the minimum value, the maximum value, and the average
// value of a given array list of doubles

public class MinMaxAvg implements Parcelable {
    private double min;
    private double max;
    private double avg;

    public MinMaxAvg(ArrayList<Double> numbers) {
        this.min = Collections.min(numbers);
        this.max = Collections.max(numbers);

        double count = 0;
        for (int i = 0; i < numbers.size(); i++) {
            count = count + numbers.get(i);
        }

        this.avg = count / numbers.size();
    }

    protected MinMaxAvg(Parcel in) {
        min = in.readDouble();
        max = in.readDouble();
        avg = in.readDouble();
    }

    public static final Creator<MinMaxAvg> CREATOR = new Creator<MinMaxAvg>() {
        @Override
        public MinMaxAvg createFromParcel(Parcel in) {
            return new MinMaxAvg(in);
        }

        @Override
        public MinMaxAvg[] newArray(int size) {
            return new MinMaxAvg[size];
        }
    };

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public double getAvg() {
        return this.avg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(min);
        dest.writeDouble(max);
        dest.writeDouble(avg);
    }
}
