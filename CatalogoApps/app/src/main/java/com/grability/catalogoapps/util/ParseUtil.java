package com.grability.catalogoapps.util;

import com.grability.catalogoapps.model.App;
import com.grability.catalogoapps.model.Category;
import com.grability.catalogoapps.model.Img;
import com.grability.catalogoapps.model.Store;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Julio on 19/01/2017.
 */

public class ParseUtil {


    public static Store parseStore(String response) throws Exception {
        Store store = new Store();
        JSONObject obj = new JSONObject(response);
        JSONObject feed = obj.getJSONObject("feed");
        JSONObject author = feed.getJSONObject("author");

        JSONObject name = author.getJSONObject("name");
        store.setName(name.getString("label"));

        JSONObject uri = author.getJSONObject("uri");
        store.setUri(uri.getString("label"));

        JSONArray apps = feed.getJSONArray("entry");

        for (int i=0; i < apps.length(); i++) {
            JSONObject im = apps.getJSONObject(i);
            JSONObject summary = im.getJSONObject("summary");
            JSONObject artist = im.getJSONObject("im:artist");
            JSONObject app = im.getJSONObject("im:name");
            JSONObject attributes = artist.getJSONObject("attributes");
            String artistHref = attributes.getString("href");
            String artistName = artist.getString("label");
            String appName = app.getString("label");

            // app
            App item = new App();

            // images
            JSONArray imgs = im.getJSONArray("im:image");
            for (int j=0; j < imgs.length(); j++) {
                JSONObject img = imgs.getJSONObject(j);
                attributes = img.getJSONObject("attributes");
                String imLabel = img.getString("label");
                int height = attributes.getInt("height");
                if (height == 75) {
                    Img img2 = new Img();
                    img2.setHeight(height);
                    img2.setUrl(imLabel);
                    item.setImg2(img2);
                } else {
                    if (height < 75) {
                        Img img1 = new Img();
                        img1.setHeight(height);
                        img1.setUrl(imLabel);
                        item.setImg1(img1);
                    } else  {
                        Img img3 = new Img();
                        img3.setHeight(height);
                        img3.setUrl(imLabel);
                        item.setImg3(img3);
                    }
                }
            }

            // price
            JSONObject price = im.getJSONObject("im:price");
            attributes = price.getJSONObject("attributes");
            double monto = attributes.getDouble("amount");
            String currency = attributes.getString("currency");

            // app-id
            JSONObject id = im.getJSONObject("id");
            attributes = id.getJSONObject("attributes");
            long appId = attributes.getLong("im:id");

            // title
            JSONObject title = im.getJSONObject("title");
            String appTitle = title.getString("label");

            // category
            JSONObject category = im.getJSONObject("category");
            attributes = category.getJSONObject("attributes");
            String scheme = attributes.getString("scheme");
            String term = attributes.getString("term");
            long idCat = attributes.getLong("im:id");
            String labelCat = attributes.getString("label");
            if (i == 0) {
                // default cat
                store.addCategory(new Category(0, "", "All", ""));
            }
            Category cat = new Category(idCat, term, labelCat, scheme);
            store.addCategory(cat);

            // rel-date
            JSONObject releaseDate = im.getJSONObject("im:releaseDate");
            attributes = releaseDate.getJSONObject("attributes");
            String relDate = attributes.getString("label");

            // app
            item.setName(appName);
            item.setId(appId);
            item.setArtistHref(artistHref);
            item.setCategoryId(idCat);
            item.setArtistName(artistName);
            item.setCurrency(currency);
            item.setPrecio(monto);
            item.setPrecioStr(monto + " " + currency);
            item.setLink("");
            item.setReleaseDate(relDate);
            item.setTitle(appTitle);
            item.setSummary(summary.getString("label"));
            store.addApp(item);
        }

        return store;
    }

}
