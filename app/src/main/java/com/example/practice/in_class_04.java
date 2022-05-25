package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class in_class_04 extends AppCompatActivity {

    private Button generateNumbers;
    private SeekBar complexitySeekBar;
    private TextView selectComplexityCountTextView, minTextViewResult,
    maxTextViewResult, avgTextViewResult;
    private ProgressBar progressBar;
    private Handler messageQueue;
    private ExecutorService threadPool;

    private int numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class04);
        setTitle("Number Generator");

        threadPool = Executors.newFixedThreadPool(2);

        generateNumbers = findViewById(R.id.generateNumberButton);
        complexitySeekBar = findViewById(R.id.selectComplexitySeekBar);
        selectComplexityCountTextView = findViewById(R.id.selectComplexityCountTextView);
        minTextViewResult = findViewById(R.id.minimumTextViewResult);
        maxTextViewResult = findViewById(R.id.maxTextViewResult);
        avgTextViewResult = findViewById(R.id.avgTextViewResult);
        progressBar = findViewById(R.id.progressBar);

        // keep progress bar invisible until button is clicked
        progressBar.setVisibility(View.INVISIBLE);

        // default # of numbers to be generated
        numbers = 8;

        // seek bar changes

        complexitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    numbers = progress;
                    selectComplexityCountTextView.setText(progress + " times");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // generate number button listener

        generateNumbers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (numbers == 0) {
                    Toast.makeText(in_class_04.this, "Please change slider to a value " +
                                    "greater than 0",
                            Toast.LENGTH_LONG).show();
                } else {
                    threadPool.execute(
                            new HeavyWork(numbers, messageQueue)
                    );
                }
            }
        });

        // handler queue callback

        messageQueue = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    // make progress bar visible when button is clicked (thread starts)
                    case HeavyWork.STATUS_START:
                        progressBar.setVisibility(View.VISIBLE);
                        break;

                    // resets progress bar to 0 and makes it invisible, gets parcelable data from
                    // background thread and sets min, max, and average text views
                    case HeavyWork.STATUS_END:
                        progressBar.setProgress(0);
                        progressBar.setVisibility(View.INVISIBLE);
                        Bundle receivedMinMaxAvgData = msg.getData();
                        MinMaxAvg values = receivedMinMaxAvgData
                                .getParcelable(HeavyWork.KEY_MINMAXAVG);
                        minTextViewResult
                                .setText(((double) Math.round(values.getMin() * 100) / 100) + "");
                        maxTextViewResult
                                .setText(((double) Math.round(values.getMax() * 100) / 100) + "");
                        avgTextViewResult
                                .setText(((double) Math.round(values.getAvg() * 100) / 100) + "");
                        break;

                    // tracks progress, updates progress bar as each number is generated
                    case HeavyWork.STATUS_PROGRESS:
                        Bundle receivedData = msg.getData();
                        double donePercent = receivedData.getDouble(HeavyWork.KEY_PROGRESS);
                        progressBar.setProgress((int) donePercent, false);
                }

                return false;
            }
        });
    }
}