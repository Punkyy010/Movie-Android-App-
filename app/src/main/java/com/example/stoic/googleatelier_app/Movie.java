package com.example.stoic.googleatelier_app;

import java.util.ArrayList;

public class Movie {

    private int ID;
    private String name;
    private float rating;
    private String genre;
    private int year;
    private String shortDesc;
    private String actors;
    private String photoBase64;
    private ArrayList<RelatedMovies> relatedMoviesArrayList;
    private String checked;

    public Movie(){

    }

    public Movie(String name, float rating, String genre, String shortDesc,String actors, String photoBase64, ArrayList<RelatedMovies> relatedMoviesArrayList,String checked){
        this.name = name;
        this.rating = rating;
        this.genre = genre;
        this.actors = actors;
        this.shortDesc = shortDesc;
        this.photoBase64 = photoBase64;
        this.relatedMoviesArrayList = relatedMoviesArrayList;
        this.checked = checked;
    }

    public void setID(int id){
        ID = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChecked(String check){this.checked = check;}

    public void setYear(int y){
        year = y;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public void setRelatedMoviesArrayList(ArrayList<RelatedMovies> relatedMoviesArrayList) {
        this.relatedMoviesArrayList = relatedMoviesArrayList;
    }

    public void setActors(String act){this.actors = act;}

    public int getID(){
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getChecked(){return checked;}

    public int getYear(){
        return year;
    }

    public float getRating() {
        return rating;
    }

    public String getActors(){return actors;}

    public String getGenre() {
        return genre;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public ArrayList<RelatedMovies> getRelatedMoviesArrayList() {
        return relatedMoviesArrayList;
    }
}
