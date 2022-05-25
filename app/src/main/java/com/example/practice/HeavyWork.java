package com.example.practice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Random;

public class HeavyWork implements Runnable {
    public static final int STATUS_START = 0x001;
    public static final int STATUS_END = 0x002;
    public static final int STATUS_PROGRESS = 0x003;
    public static final String KEY_PROGRESS = "KEY_PROGRESS";
    public static final String KEY_MINMAXAVG = "KEY_MINMAXAVG";

    static final int COUNT = 900000;

    private int numbers;
    private Handler messageQueue;

    public HeavyWork(int n, Handler messageQueue) {
        this.numbers = n;
        this.messageQueue = messageQueue;
    }

    static ArrayList<Double> getArrayNumbers(int n){
        ArrayList<Double> returnArray = new ArrayList<>();

        for (int i=0; i<n; i++){
            returnArray.add(getNumber());
        }

        return returnArray;
    }

    static double getNumber(){
        double num = 0;
        Random rand = new Random();
        for(int i=0;i<COUNT; i++){
            num = num + rand.nextDouble();
        }
        return num / ((double) COUNT);
    }

    @Override
    public void run() {
        Message startMessage = new Message();
        startMessage.what = STATUS_START;
        messageQueue.sendMessage(startMessage);

        ArrayList<Double> returnArray = new ArrayList<>();

        for (int i=0; i<numbers; i++){
            Message progressMessage = new Message();

            returnArray.add(getNumber());

            // send new doubles to increment progress bar by
            Bundle bundle = new Bundle();
            bundle.putDouble(KEY_PROGRESS, (100.0 / numbers) * i);
            progressMessage.what = STATUS_PROGRESS;
            progressMessage.setData(bundle);
            messageQueue.sendMessage(progressMessage);
        }

        // send parcelable number data (min, max, average)
        Message endMessage = new Message();
        MinMaxAvg obj = new MinMaxAvg(returnArray);
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_MINMAXAVG, obj);
        endMessage.what = STATUS_END;
        endMessage.setData(bundle);
        messageQueue.sendMessage(endMessage);



    }
}