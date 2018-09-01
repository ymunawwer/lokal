package com.example.yaseenmunawwer.lokal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import com.android.volley.RequestQueue;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity    {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String JsonURL ="https://picsum.photos/list";
    private ArrayList<Data> data;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        loadJSON();

    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<List<Data>> call = request.getJSON();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, retrofit2.Response<List<Data>> response) {
                Log.d("data", "onResponse: "+response);
                Log.d("data", "onResponse: "+response.body());
                List<Data> jsonResponse = response.body();
                Log.d("data", "onResponse: "+jsonResponse.get(0));
              data = new ArrayList<>(20);
              data= (ArrayList<Data>) response.body();//Arrays.asList(jsonResponse.getAndroid));
                Log.d("data", "class: "+data.get(700).toString());
                Log.d("data", "size: "+data.size());
                Log.d("data", "Title: "+data.get(0).getFilename());
                mAdapter = new MyRecyclerViewAdapter(getBaseContext(),data);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }



}
