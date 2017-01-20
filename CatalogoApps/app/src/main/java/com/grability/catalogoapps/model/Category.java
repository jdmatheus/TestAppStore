package com.grability.catalogoapps.model;

/**
 * Created by Julio on 19/01/2017.
 */

public class Category implements Comparable {

    private long id;
    private String term;
    private String label;
    private String url;


    public Category() {
        super();
    }

    public Category(long id, String term, String label, String url) {
        this();
        setId(id);
        setTerm(term);
        setLabel(label);
        setUrl(url);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(id);
        sb.append(",");
        sb.append(term);
        sb.append(",");
        sb.append(label);
        sb.append(",");
        sb.append(url);
        sb.append("}");
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (o == null)
            return 0;
        return getLabel().compareTo(((Category) o).getLabel());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        Category cat = (Category) obj;
        return id == cat.getId();
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
