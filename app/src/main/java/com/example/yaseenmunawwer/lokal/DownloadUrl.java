package com.example.yaseenmunawwer.lokal;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.Timestamp;
import java.sql.Time;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;


public class DownloadUrl extends AsyncTask<String, Integer, String> implements UIUpdate{
    ProgressBar bar;
    ProgressBar loadingbar;
    Button btn;
    static int i =0;
    private double per;;
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {

            String root = (Environment.getExternalStorageDirectory() + "/Picsum/").toString();
            File f=new File(root);
            if(!(f.isDirectory())||!(f.exists())){
                f.mkdir();

            }

            Log.d("Url", root.toString());

            System.out.println("Downloading");
            URL url = new URL(f_url[0]);

            URLConnection conection = url.openConnection();
            conection.connect();

            int lenghtOfFile = conection.getContentLength();
            Log.d("length",Integer.toString(lenghtOfFile));

            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();

            File fileName = new File(root + "/download"+ts+".jpg");


            OutputStream output = new FileOutputStream(fileName);
            byte data[] = new byte[1024];

            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                Log.d("data", data.toString());
                Thread.sleep(50);
                per= ((double)total/lenghtOfFile)*100;


                publishProgress((int)per);


                output.write(data, 0, count);

            }


            output.flush();


            output.close();
            input.close();
            System.out.println(fileName.getPath());

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String file_url) {
        i++;
        if (this.bar != null) {
            loadingbar.setVisibility(View.GONE);
        }
        System.out.println("Downloaded");
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.bar = progressBar;
    }

    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        System.out.println(String.valueOf(values[0])+"%");
        if(this.btn!=null&&values[0]==100) {
            loadingbar.setVisibility(View.GONE);
            this.btn.setEnabled(false);
            this.btn.invalidate();
            this.btn.setVisibility(View.GONE);

        }
        if (this.bar != null) {
            loadingbar.setVisibility(View.GONE);
            this.bar.setVisibility(View.VISIBLE);
            System.out.println(String.valueOf(values[0])+"%");

            bar.setProgress(values[0]);
        }
    }

    @Override
    public void btnDisable(View v) {
        Log.d("View", "btnDisable: "+v);
        this.btn=(Button)v;

    }

    @Override
    public void downloadStarted(View v) {
        this.loadingbar = (ProgressBar)v;

    }


}
