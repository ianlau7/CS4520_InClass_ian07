package com.example.practice;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Keep
public class in_class_05 extends AppCompatActivity {

    EditText keywordSearch;
    Button go;
    ImageView next, prev, image;
    ProgressBar progressBar;
    TextView loading;

    OkHttpClient client = new OkHttpClient();
    static String[] listOfKeywords;

    static String[] imageURLs;
    int imageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class05);
        setTitle("Image Search");

        keywordSearch = findViewById(R.id.SearchKeywordEditText);
        go = findViewById(R.id.GoButton);
        next = findViewById(R.id.NextButtonImageView);
        prev = findViewById(R.id.PreviousButtonImageView);
        image = findViewById(R.id.imageDisplayImageView);
        progressBar = findViewById(R.id.progressBarImage);
        loading = findViewById(R.id.LoadingTextView);

        next.setVisibility(View.INVISIBLE);
        prev.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.INVISIBLE);

        if (!checkConnection()) {
            Toast.makeText(in_class_05.this, "No internet connection. Please connect to" +
                            " a network and try again.",
                    Toast.LENGTH_LONG).show();
        } else {
            // GET keyword API
            Request keywordRequest = new Request.Builder()
                    .url("http://dev.sakibnm.space/apis/images/keywords")
                    .build();

            client.newCall(keywordRequest).enqueue(new Callback() {
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        String keywords = responseBody.string();
                        in_class_05.listOfKeywords = keywords.split(",");
                    }
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    in_class_05.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(in_class_05.this, "no keywords",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

        // Go button listener
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                image.setImageResource(R.drawable.white_box);

                // if EditText is empty
                if (keywordSearch.getText().length() == 0) {
                    Toast.makeText(in_class_05.this, "Please enter a keyword",
                            Toast.LENGTH_LONG).show();

                // if the text from the editText is not a keyword from the keyword API
                } else if(!Arrays.asList(listOfKeywords).contains(keywordSearch.getText().toString())) {

                    Toast.makeText(in_class_05.this, "keyword invalid; please" +
                                    " try again.",
                            Toast.LENGTH_LONG).show();

                    next.setVisibility(View.INVISIBLE);
                    prev.setVisibility(View.INVISIBLE);

                } else {

                    progressBar.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.VISIBLE);

                    if (!checkConnection()) {
                        Toast.makeText(in_class_05.this, "No internet connection." +
                                        " Please connect to a network and try again.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        // GET image API
                        HttpUrl url = HttpUrl.parse("http://dev.sakibnm.space/apis/images/retrieve")
                                .newBuilder()
                                .addQueryParameter("keyword", keywordSearch.getText().toString())
                                .build();

                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response)
                                    throws IOException {
                                if (response.isSuccessful()) {
                                    ResponseBody responseBody = response.body();
                                    String imageurls = responseBody.string();

                                    // if the API returns no URLs
                                    if (imageurls.isEmpty()) {
                                        in_class_05.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                next.setVisibility(View.INVISIBLE);
                                                prev.setVisibility(View.INVISIBLE);
                                                progressBar.setVisibility(View.INVISIBLE);
                                                loading.setVisibility(View.INVISIBLE);

                                                Toast.makeText(in_class_05.this,
                                                        "No Images Found.",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    } else {

                                        in_class_05.imageURLs = imageurls.split("\\R");

                                        // if there is more than 1 URL
                                        if (imageURLs.length > 1) {
                                            in_class_05.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    next.setVisibility(View.VISIBLE);
                                                    prev.setVisibility(View.VISIBLE);
                                                }
                                            });

                                        }

                                        in_class_05.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Picasso.get().load(imageURLs[0])
                                                        .networkPolicy(NetworkPolicy.NO_CACHE)
                                                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                                                        .into(image);

                                                progressBar.setVisibility(View.INVISIBLE);
                                                loading.setVisibility(View.INVISIBLE);
                                            }
                                        });

                                        imageIndex = 0;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                in_class_05.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(in_class_05.this, "keyword invalid;" +
                                                        " please try again.",
                                                Toast.LENGTH_LONG).show();

                                        next.setVisibility(View.INVISIBLE);
                                        prev.setVisibility(View.INVISIBLE);
                                        image.setImageResource(R.drawable.white_box);
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                loading.setText("Loading next...");
                loading.setVisibility(View.VISIBLE);

                if (imageIndex == imageURLs.length - 1) {
                    Picasso.get().load(imageURLs[0])
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(image);
                    imageIndex = 0;
                } else {
                    Picasso.get().load(imageURLs[imageIndex + 1])
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(image);
                    imageIndex++;
                }

                progressBar.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.INVISIBLE);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                loading.setText("Loading previous...");
                loading.setVisibility(View.VISIBLE);

                if (imageIndex == 0) {
                    Picasso.get().load(imageURLs[imageURLs.length - 1])
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(image);
                    imageIndex = imageURLs.length - 1;
                } else {
                    Picasso.get().load(imageURLs[imageIndex - 1])
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(image);
                    imageIndex--;
                }

                progressBar.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }

    // check for an internet connection
    public boolean checkConnection() {
        ConnectivityManager m = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = m.getActiveNetworkInfo();

        if (activeNetwork != null || activeNetwork.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}