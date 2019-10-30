package com.example.stoic.googleatelier_app.net;

import com.example.stoic.googleatelier_app.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface MovieService {
    @GET("/movies")
    Call<List<Movie>> getMovies();

}
