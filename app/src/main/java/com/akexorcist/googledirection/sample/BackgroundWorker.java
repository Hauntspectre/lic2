package com.akexorcist.googledirection.sample;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by octavian on 6/4/2018.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String> {

    Context context;

     BackgroundWorker (Context ctx){
        context=ctx;
    }

    @Override
    protected String doInBackground(String... params) {
         String type = params[0];
         String login_url = "http://192.168.1.106/android/login.php";
         if(type.equals("login")){
             try {
                 String user_name = params[1];
                 String pass= params[2];
                 URL url = new URL(login_url);
                 HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                 httpURLConnection.setRequestMethod("POST");
                 httpURLConnection.setDoOutput(true);
                 httpURLConnection.setDoInput(true);
                 OutputStream outputStream = httpURLConnection.getOutputStream();
                 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                 String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+" & "+
                         URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
                 bufferedWriter.write(post_data);
                 bufferedWriter.flush();
                 bufferedWriter.close();
                 outputStream.close();

                 InputStream inputStream = httpURLConnection.getInputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                 String result="";
                 String line="";
                 while((line = bufferedReader.readLine())!=null){
                     result +=line;
                 }
                 bufferedReader.close();
                 inputStream.close();
                 httpURLConnection.disconnect();

                 return result;

             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context,result,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
