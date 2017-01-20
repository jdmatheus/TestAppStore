package com.grability.catalogoapps.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Julio on 19/01/2017.
 */
public class Store {

    private String name;
    private String uri;

    private List<Category> categories;
    private List<App> apps;


    public Store() {
        categories = new ArrayList<Category>();
        apps = new ArrayList<App>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("categorias:");
        for (Category cat:
             getCategoriesOrderByLabel()) {
            sb.append("cat-" + cat.getLabel());
            sb.append("\n");
        }
        sb.append("apps-size:");
        sb.append(apps.size());
        sb.append("\n");
        sb.append("apps:");
        sb.append("\n");
        for (App app:
                getAppsOrderByLabel()) {
            sb.append("app-" + app.getName() + " CAT-" + app.getCategoryId());
            sb.append("\n");
        }

        return sb.toString();
    }

    public List<App> getApps() {
        return apps;
    }

    public List<App> getAppsOrderByLabel() {
        Collections.sort(apps);
        return apps;
    }

    public List<App> getAppsByCategoryOrderByLabel(long categoryId) {
        List<App> lst = new ArrayList<App>();
        for (App app: apps) {
            if (categoryId == app.getCategoryId()) {
                lst.add(app);
            }
        }
        Collections.sort(lst);
        return lst;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }

    public void addApp(App app) {
        if (!apps.contains(app)) {
            apps.add(app);
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Category> getCategoriesOrderByLabel() {
        Collections.sort(categories);
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    public int getAppsCount() {
        if (apps == null) {
            return 0;
        }
        return apps.size();
    }

    public int getCategoriesCount() {
        if (categories == null) {
            return 0;
        }
        return categories.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
