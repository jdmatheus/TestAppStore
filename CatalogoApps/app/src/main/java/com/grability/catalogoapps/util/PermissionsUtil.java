package com.grability.catalogoapps.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;


public class PermissionsUtil{

    public static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;


    public static boolean areAllPermissionsGranted(Context context){
        boolean allGranted = true;
        String[] permissionsNeededList = new String[]{
                WRITE_EXTERNAL_STORAGE,
                READ_EXTERNAL_STORAGE,
        };

        for (String permission : permissionsNeededList){
            if(!isPermissionGranted(context, permission)){
                allGranted = false;
            }
        }

        return allGranted;
    }

    public static boolean isPermissionGranted(Context context, String permission){
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }

}
