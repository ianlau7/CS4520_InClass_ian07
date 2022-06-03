package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class in_class_06 extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();

    RadioGroup countries, categories;

    // USA is us, Hong Kong is hk, Canada is ca, Great Britain is gb, and Singapore is sg
    RadioButton usa, hong_kong, canada, great_britain, singapore;
    RadioButton business, entertainment, general, health, science, sports, technology;

    Button getNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class06);
        setTitle("News Search");

        countries = findViewById(R.id.countriesRadioGroup);
        categories = findViewById(R.id.categoriesRadioGroup);
        usa = findViewById(R.id.usaRadioButton);
        hong_kong = findViewById(R.id.hongKongRadioButton);
        canada = findViewById(R.id.canadaRadioButton);
        great_britain = findViewById(R.id.greatBritainRadioButton);
        singapore = findViewById(R.id.singaporeRadioButton);
        business = findViewById(R.id.businessRadioButton);
        entertainment = findViewById(R.id.entertainmentRadioButton);
        general = findViewById(R.id.generalRadioButton);
        health = findViewById(R.id.healthRadioButton);
        science = findViewById(R.id.scienceRadioButton);
        sports = findViewById(R.id.sportsRadioButton);
        technology = findViewById(R.id.techRadioButton);
        getNews = findViewById(R.id.findNewsButton);

        getNews.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // if the user has not selected a category and/or a country
                if (countries.getCheckedRadioButtonId() == -1 &&
                        categories.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(in_class_06.this,
                            "Please select a country and/or a category.",
                            Toast.LENGTH_LONG).show();
                } else {

                    HttpUrl url;

                    if (countries.getCheckedRadioButtonId() == -1) {

                        int checkedButtonId = categories.getCheckedRadioButtonId();
                        RadioButton checked = findViewById(checkedButtonId);
                        String p = checked.getText().toString().toLowerCase();

                        url = HttpUrl.parse("https://newsapi.org/v2/top-headlines")
                                .newBuilder()
                                .addQueryParameter("category", p)
                                .addQueryParameter("apiKey", "36b9a3ed1b704c71b38075642119529e")
                                .build();

                    } else if (categories.getCheckedRadioButtonId() == -1) {
                        int checkedButtonId = countries.getCheckedRadioButtonId();
                        RadioButton checked = findViewById(checkedButtonId);
                        String p = checked.getText().toString();

                        String countryId = getCountryCode(p);

                        url = HttpUrl.parse("https://newsapi.org/v2/top-headlines")
                                .newBuilder()
                                .addQueryParameter("country", countryId)
                                .addQueryParameter("apiKey", "36b9a3ed1b704c71b38075642119529e")
                                .build();

                    } else {
                        int checkedButtonId = countries.getCheckedRadioButtonId();
                        RadioButton checked = findViewById(checkedButtonId);
                        String p = checked.getText().toString();

                        String countryId = getCountryCode(p);

                        int checkedButtonId1 = categories.getCheckedRadioButtonId();
                        RadioButton checked1 = findViewById(checkedButtonId1);
                        String p1 = checked1.getText().toString().toLowerCase();

                        url = HttpUrl.parse("https://newsapi.org/v2/top-headlines")
                                .newBuilder()
                                .addQueryParameter("country", countryId)
                                .addQueryParameter("category", p1)
                                .addQueryParameter("apiKey", "36b9a3ed1b704c71b38075642119529e")
                                .build();

                    }

                    Request newsRequest = new Request.Builder()
                            .url(url)
                            .build();

                    client.newCall(newsRequest).enqueue(new Callback() {

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                            if (response.isSuccessful()) {
                                Gson gsonData = new Gson();
                                Articles articles = gsonData.fromJson(response.body().charStream(), Articles.class);
                                Log.d("demo", articles.toString());

                                in_class_06.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(in_class_06.this, newsDisplay.class);
                                        intent.putParcelableArrayListExtra("articles", articles.getArticles());
                                        startActivity(intent);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            in_class_06.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(in_class_06.this, "Unable to retrieve " +
                                                    "data",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }

            }
        });
    }

    String getCountryCode(String country) {
        if (country.equals("United States")) {
            return "us";
        } else if (country.equals("Hong Kong")) {
            return "hk";
        } else if (country.equals("Great Britain")) {
            return "gb";
        } else if (country.equals("Canada")) {
            return "ca";
        } else if (country.equals("Singapore")) {
            return "sg";
        } else {
            return "";
        }
    }
}