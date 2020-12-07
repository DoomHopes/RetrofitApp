package com.doomhopes.retrofitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.doomhopes.retrofitapp.models.Articles;
import com.doomhopes.retrofitapp.models.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    final static String API_KEY = "7267b90112544bbc9212f981b3715b31";
    List<Articles> articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //xml
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        //retrofit
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseModel> call = apiService.getLatestNews("techcrunch",API_KEY);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body().getStatus().equals("ok")) {
                    articleList = response.body().getArticles();
                }
                else {
                    Log.e("out", "Failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("out", t.toString());
            }
        });
    }

    public void onClick(View view) {
        for(Articles a: articleList){
            String text = a.getTitle() + "\n" + a.getAuthor() + "\n" + a.getDescription() + "\n" + a.getPublishedAt()
                    + "\n" + a.getUrl();
            textView.setText(text);
        }
    }
}