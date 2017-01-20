package com.grability.catalogoapps.model;

/**
 * Created by Julio on 19/01/2017.
 */
public class App implements Comparable {

    private long id;
    private long categoryId;
    private String name;
    private String title;
    private String summary;
    private String releaseDate;
    private String link;

    private double precio;
    private String currency;
    private String precioStr;

    private String artistName;
    private String artistHref;

    private Img img1;
    private Img img2;
    private Img img3;


    @Override
    public int compareTo(Object o) {
        if (o == null)
            return 0;
        return getName().compareTo(((App) o).getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        App app = (App) obj;
        return id == app.getId();
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrecioStr() {
        return precioStr;
    }

    public void setPrecioStr(String precioStr) {
        this.precioStr = precioStr;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistHref() {
        return artistHref;
    }

    public void setArtistHref(String artistHref) {
        this.artistHref = artistHref;
    }

    public Img getImg1() {
        return img1;
    }

    public void setImg1(Img img1) {
        this.img1 = img1;
    }

    public Img getImg2() {
        return img2;
    }

    public void setImg2(Img img2) {
        this.img2 = img2;
    }

    public Img getImg3() {
        return img3;
    }

    public void setImg3(Img img3) {
        this.img3 = img3;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
