package com.grability.catalogoapps;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.grability.catalogoapps.model.App;
import com.grability.catalogoapps.model.Category;
import com.grability.catalogoapps.model.Store;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Created by Julio on 17/01/2017.
 */
public class Catalogo extends Application {

    public static Context context = null;
    public static Store store;
    public static App selectedApp;
    public static Category selectedCat;

    private static Logger logger = null;
    public static final String LOG_FILE_PATH = Environment.getExternalStorageDirectory()
            + File.separator + "CatalogoApps" + File.separator + "log.txt";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initLog();
    }

    public static void initLog() {
        try {
            int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
                LogConfigurator logConfigurator = new LogConfigurator();
                logConfigurator.setFileName(LOG_FILE_PATH);

                logConfigurator.setRootLevel(Level.DEBUG);
                logConfigurator.setLevel("org.apache", Level.ERROR);
                logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
                logConfigurator.setMaxFileSize(1024 * 1024 * 2);
                logConfigurator.setImmediateFlush(true);
                logConfigurator.configure();

                logger = Logger.getLogger(Catalogo.class);
                logger.debug("CatalogoApps log created!");
            } else {
                Log.d("FILECREATION", "Permission denied");
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error: ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        logger.debug("newConfig.screenWidthDp: " + newConfig.screenWidthDp);
        logger.debug("newConfig.orientation: " + newConfig.orientation);
        logger.debug("newConfig.str: " + newConfig.toString());
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals( state ) ) {
            return true;
        }
        return false;
    }



}
