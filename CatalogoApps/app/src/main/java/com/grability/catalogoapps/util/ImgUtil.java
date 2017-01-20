package com.grability.catalogoapps.util;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.grability.catalogoapps.Catalogo;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Julio on 20/01/2017.
 */
public class ImgUtil {

    private static Logger logger = Logger.getLogger(ImgUtil.class);

    public static Map<Long, Bitmap> bitmaps = new HashMap<Long, Bitmap>();


    public static void saveImage(long appId, String url) {
        try {
            String pathOut = ImgUtil.getImgPaht(appId, url);
            Bitmap img = bitmaps.get(appId);
            if (img != null) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, os);
                byte[] byteArray = os.toByteArray();
                os.close();

                ImgUtil.saveImg(pathOut, byteArray);
            } else {
                logger.error("saveImage img is null");
            }
        } catch (Exception e) {
            logger.error("saveImage", e);
        }
    }

    public static String getImgPaht(long appId, String url) {
        File f = Catalogo.context.getCacheDir();
        if (ContextCompat.checkSelfPermission(Catalogo.context, PermissionsUtil.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(Catalogo.context, PermissionsUtil.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {
            File g = new File(f.getAbsolutePath() + File.separator + "imgs");
            if (!g.exists()) {
                g.mkdirs();
            }
            return g.getAbsolutePath() + File.separator + appId + ".png";
        }
        return null;
    }

    public static void saveImg(String pathOut, byte[] imgData) {
        if (ContextCompat.checkSelfPermission(Catalogo.context, PermissionsUtil.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(Catalogo.context, PermissionsUtil.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {

            FileOutputStream fos = null;
            try {
                File f = new File(pathOut);
                if (!f.exists()) {
                    f.createNewFile();
                }

                fos = new FileOutputStream(f);
                fos.write(imgData);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                logger.error("saveImg: " + pathOut, e);
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        logger.error("saveImg-finally", e);
                    }
                }
            }
        } else {
            logger.warn("Write ES permission denied!");
            Toast.makeText(Catalogo.context, "Write ES permission denied!", Toast.LENGTH_LONG).show();
        }
    }

    public static Bitmap readImg(String pathOut) {
        try {
            BitmapFactory.Options imgOpts = new BitmapFactory.Options();
            imgOpts.inDither = true;
            imgOpts.inScaled = false;
            imgOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            imgOpts.inPurgeable = true;
            return BitmapFactory.decodeFile(pathOut, imgOpts);
        } catch (Exception e) {
            logger.error("readImg: " + pathOut, e);
        }
        return null;
    }
}
