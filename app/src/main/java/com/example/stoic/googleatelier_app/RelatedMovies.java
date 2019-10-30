package com.example.stoic.googleatelier_app;

public class RelatedMovies {
    private String name;
    private String photoBase64;
    private float rating;

    public String getName() {
        return name;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public float getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
