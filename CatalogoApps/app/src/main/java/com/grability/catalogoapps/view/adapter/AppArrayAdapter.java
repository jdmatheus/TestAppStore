package com.grability.catalogoapps.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grability.catalogoapps.R;
import com.grability.catalogoapps.model.App;
import com.grability.catalogoapps.util.ImgUtil;
import com.grability.catalogoapps.util.Util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Julio on 19/01/2017.
 */

public class AppArrayAdapter extends ArrayAdapter<App> {

    private static Logger logger = Logger.getLogger(AppArrayAdapter.class);

    private static class ViewHolder {
        TextView tvName;
        ImageView ivIcon;
    }

    public AppArrayAdapter(Context context, List<App> apps) {
        super(context, -1, apps);
    }

    @Override
    public long getItemId(int position) {
        App app = getItem(position);
        if (app == null)
            return 0;
        return app.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        App app = getItem(position);

        ViewHolder viewHolder;

        // check for reusable ViewHolder from a ListView item that scrolled
        // offscreen; otherwise, create a new ViewHolder
        if (convertView == null) { // no reusable ViewHolder, so create one
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView =
                    inflater.inflate(R.layout.item_app, parent, false);
            viewHolder.tvName =
                    (TextView) convertView.findViewById(R.id.tvAppName);
            viewHolder.ivIcon =
                    (ImageView) convertView.findViewById(R.id.ivIcon);
            convertView.setTag(viewHolder);
        }
        else { // reuse existing ViewHolder stored as the list item's tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // settings
        String text = app.getName();
        if (text.length() > 9) {
            text = text.substring(0, 9);
        }
        viewHolder.tvName.setText(text);
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);

        // find img
        if (ImgUtil.bitmaps.containsKey(app.getId())) { // 1 in the map
            viewHolder.ivIcon.setImageBitmap(
                    ImgUtil.bitmaps.get(app.getId()));
        } else {
            String path = ImgUtil.getImgPaht(app.getId(), app.getImg3().getUrl());
            File f = new File(path);
            if (f.exists()) { // 2 in the file system cache
                Bitmap img = ImgUtil.readImg(path);
                ImgUtil.bitmaps.put(app.getId(), img);
                viewHolder.ivIcon.setImageBitmap(img);
            } else {
                // 3 in the internet
                if (Util.haveInternetConnection()) {
                    new LoadImageTask(viewHolder.ivIcon).execute(
                            new String[]{
                                    app.getImg3().getUrl(), Long.valueOf(app.getId()).toString()
                            });
                } else {
                    logger.warn("no internet and image is not on file system");
                }
            }
        }

        return convertView;
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView; // displays the thumbnail

        // store ImageView on which to set the downloaded Bitmap
        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        // load image; params[0] is the String URL representing the image
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL(params[0]); // create URL for image
                String id = params[1];

                // open an HttpURLConnection, get its InputStream
                // and download the image
                connection = (HttpURLConnection) url.openConnection();

                try {
                    InputStream inputStream = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    ImgUtil.bitmaps.put(Long.valueOf(params[1]), bitmap); // cache for later use
                    ImgUtil.saveImage(Long.valueOf(id), params[0]);
                } catch (Exception e) {
                    logger.error("doInBackground", e);
                }
            }
            catch (Exception e) {
                logger.error("doInBackground", e);
            }
            finally {
                connection.disconnect();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
