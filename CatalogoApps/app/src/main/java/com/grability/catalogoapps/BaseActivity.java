package com.grability.catalogoapps;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.grability.catalogoapps.exceptions.AppUncaughtException;

import org.apache.log4j.Logger;

/**
 * Created by Julio on 17/01/2017.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static Logger logger = Logger.getLogger(BaseActivity.class);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            logger.debug("onCreate");
            Thread.setDefaultUncaughtExceptionHandler(new AppUncaughtException());
            boolean isTablet = getResources().getBoolean(R.bool.isTablet);
            logger.debug("isTablet: " + isTablet);
            if (isTablet) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } catch (Exception e) {
            logger.error("onCreate", e);
        }
    }



}
