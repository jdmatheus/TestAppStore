package com.grability.catalogoapps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.grability.catalogoapps.view.adapter.AppArrayAdapter;

import org.apache.log4j.Logger;

public class AppActivity extends BaseActivity
        implements AdapterView.OnItemClickListener {

    private static Logger logger = Logger.getLogger(AppActivity.class);

    private GridView gvApps = null;
    private AppArrayAdapter appArrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        initGui();
    }

    private void initGui() {
        try {
            gvApps = (GridView) findViewById(R.id.gvApps);
            if (Catalogo.selectedCat == null
                    || Catalogo.selectedCat.getId() == 0) {
                appArrAdapter = new AppArrayAdapter(this, Catalogo.store.getAppsOrderByLabel());
            } else {
                appArrAdapter = new AppArrayAdapter(this, Catalogo.store.getAppsByCategoryOrderByLabel(
                        Catalogo.selectedCat.getId()));
            }
            gvApps.setAdapter(appArrAdapter);
            gvApps.setOnItemClickListener(this);
        } catch (Exception e) {
            logger.error("initGui", e);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        try {
            if (Catalogo.selectedCat == null
                    || Catalogo.selectedCat.getId() == 0) {
                Catalogo.selectedApp = Catalogo.store.getAppsOrderByLabel().get(pos);
            } else {
                Catalogo.selectedApp = Catalogo.store.getAppsByCategoryOrderByLabel(Catalogo.selectedCat.getId()).get(pos);
            }
            openApp();
        } catch (Exception e) {
            logger.error("onItemClick", e);
        }
    }

    private void openApp() {
        logger.debug("openApp");
        Intent i = new Intent(this, ItemDetailActivity.class);
        startActivity(i);
    }
}
