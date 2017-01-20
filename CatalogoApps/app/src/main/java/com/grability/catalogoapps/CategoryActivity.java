package com.grability.catalogoapps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.grability.catalogoapps.view.adapter.CategoryArrayAdapter;

import org.apache.log4j.Logger;

public class CategoryActivity extends BaseActivity
            implements AdapterView.OnItemClickListener {

    private static Logger logger = Logger.getLogger(CategoryActivity.class);

    private ListView lvCats = null;
    private CategoryArrayAdapter catArrAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initGui();
    }

    private void initGui() {
        try {
            lvCats = (ListView) findViewById(R.id.lvCategories);
            catArrAdapter = new CategoryArrayAdapter(this, Catalogo.store.getCategoriesOrderByLabel());
            lvCats.setAdapter(catArrAdapter);
            lvCats.setOnItemClickListener(this);
        } catch (Exception e) {
            logger.error("initGui", e);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        try {
            Catalogo.selectedCat = Catalogo.store.getCategoriesOrderByLabel().get(pos);
            openApps();
        } catch (Exception e) {
            logger.error("onItemClick", e);
        }
    }

    private void openApps() {
        logger.debug("openApps");
        Intent i = new Intent(this, AppActivity.class);
        startActivity(i);
    }
}
