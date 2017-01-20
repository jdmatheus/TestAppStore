package com.grability.catalogoapps;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.grability.catalogoapps.util.ParseUtil;
import com.grability.catalogoapps.util.Util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IntroActivity extends BaseActivity {

    private static Logger logger = Logger.getLogger(IntroActivity.class);

    public static final String QS = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";
    public static final String STORE_FN = "store";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.debug("IntroActivity");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        logger.debug("onPostResume");
        runTask();
    }

    private void openCats() {
        logger.debug("openCats");
        Intent i = new Intent(this, CategoryActivity.class);
        startActivity(i);
        finish();
    }


    // ================================================================== TASK  ================================================ //

    protected void runTask() {
        try {
            logger.debug("runTask");
            GetAppsDataTask task = new GetAppsDataTask();
            ConnectivityManager cm =
                    (ConnectivityManager) Catalogo.context.getSystemService(Context.CONNECTIVITY_SERVICE);
            logger.debug("ConnectivityManager: " + cm);
            if (Util.haveInternetConnection(cm)) {
                URL url = new URL(QS);
                task.execute(url);
            } else {
                Toast.makeText(this, "Working offline!", Toast.LENGTH_LONG).show();
                task.execute();
            }
        } catch (Exception e) {
            logger.error("runTask", e);
        }
    }

    private class GetAppsDataTask
            extends AsyncTask<URL, Void, Integer> {

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                logger.debug("onPreExecute");
                pd = new ProgressDialog(IntroActivity.this);
                logger.debug("pd: " + pd);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setCancelable(false);
                pd.setIndeterminate(true);
                pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pd.show();
            } catch (Exception e) {
                logger.error("onPreExecute", e);
            }
        }

        @Override
        protected Integer doInBackground(URL... params) {
            HttpURLConnection connection = null;

            try {
                logger.debug("params: " + params);
                if (params.length == 0) { // from file
                    // read file
                    FileInputStream fis = openFileInput(STORE_FN);
                    byte[] input = new byte[fis.available()];
                    while (fis.read(input) != -1) {}
                    fis.close();
                    String data = new String(input);
                    Catalogo.store = ParseUtil.parseStore(data);
                    logger.debug("DATA: " + data.length());
                    return Catalogo.store.getAppsCount();
                } else { // from internet
                    connection = (HttpURLConnection) params[0].openConnection();
                    int response = connection.getResponseCode();
                    logger.debug("response: " + response);

                    if (response == HttpURLConnection.HTTP_OK) {
                        StringBuilder builder = new StringBuilder();

                        BufferedReader reader = null;
                        try {
                            reader = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream()));
                            logger.debug("reader: " + reader);
                            String line;

                            while ((line = reader.readLine()) != null) {
                                builder.append(line);
                            }
                            logger.debug("closing-reader: " + reader);
                            reader.close();
                            reader = null;

                        } catch (IOException e) {
                            logger.error("doInBackground", e);
                        } finally {
                            logger.debug("closing-reader: " + reader);
                            if (reader != null) {
                                reader.close();
                            }
                        }

                        FileOutputStream fos = openFileOutput(STORE_FN, Context.MODE_PRIVATE);
                        fos.write(builder.toString().getBytes());
                        fos.close();
                        Catalogo.store = ParseUtil.parseStore(builder.toString());
                        return Catalogo.store.getAppsCount();
                    } else {
                        logger.warn("service bad response: " + response);
                    }
                }
            } catch (Exception e) {
                logger.error("doInBackground", e);
            } finally {
                logger.debug("closing-conn: " + connection);
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer count) {
            try {
                if (pd != null) {
                    pd.dismiss();
                    pd = null;
                }
                logger.debug("count: " + count);
                openCats();
            } catch (Exception e) {
                logger.error("onPostExecute", e);
            }
        }
    }

}
