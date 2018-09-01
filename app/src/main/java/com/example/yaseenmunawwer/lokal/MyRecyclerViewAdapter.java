package com.example.yaseenmunawwer.lokal;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{
    ArrayList<Data> data;
    Context ctx;



    public MyRecyclerViewAdapter(Context ctx,ArrayList data) {
        this.data=data;
        this.ctx=ctx;




    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyRecyclerViewAdapter.MyViewHolder holder, final int position) {
        Glide.with(ctx).load((data.get(position).getPost_url())+"/download").into(holder.imgview);
        if(holder.btn.isEnabled()) {
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.pgrBarLoading.setVisibility(View.VISIBLE);
                    DownloadUrl task = new DownloadUrl();
                    task.downloadStarted(holder.pgrBarLoading);

                    if (checkSelfPermission(ctx, "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions((Activity) ctx, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 138);

                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,(data.get(position).getPost_url())+"/download");
                        //task.execute((data.get(position).getPost_url())+"/download");
                        task.setProgressBar(holder.pgrBar);
                        task.btnDisable(holder.btn);

                        return;
                    } else {
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,(data.get(position).getPost_url())+"/download");
                        //task.execute((data.get(position).getPost_url())+"/download");
                        task.setProgressBar(holder.pgrBar);
                        task.btnDisable(holder.btn);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgview;
        Button btn;
        ProgressBar pgrBar;
        ProgressBar pgrBarLoading;
        public MyViewHolder(View itemView) {
            super(itemView);
            btn = (Button)itemView.findViewById(R.id.btn);
            imgview=(ImageView)itemView.findViewById(R.id.img);
            pgrBar = (ProgressBar)itemView.findViewById(R.id.progress);
            pgrBarLoading = (ProgressBar)itemView.findViewById(R.id.loading);
            pgrBarLoading.setVisibility(View.GONE);
            pgrBar.setVisibility(View.GONE);


        }
    }
}