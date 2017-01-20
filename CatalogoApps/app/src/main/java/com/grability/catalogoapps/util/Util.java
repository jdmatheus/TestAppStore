package com.grability.catalogoapps.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.grability.catalogoapps.Catalogo;

import org.apache.log4j.Logger;

/**
 * Created by Julio on 20/01/2017.
 */

public class Util {

    private static Logger logger = Logger.getLogger(Util.class);

    public static boolean haveInternetConnection() {
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) Catalogo.context.getSystemService(Context.CONNECTIVITY_SERVICE);

            return haveInternetConnection(cm);
        } catch (Exception e) {
            logger.error("haveInternetConnection", e);
        }
        return false;
    }

    public static boolean haveInternetConnection(ConnectivityManager cm) {
        try {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork == null) {
                return false;
            }
            return activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            logger.error("haveInternetConnection", e);
        }
        return false;
    }

}
