package com.example.stoic.googleatelier_app;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stoic.googleatelier_app.net.MovieService;
import com.example.stoic.googleatelier_app.net.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
   // private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Movie> moviesArrayList;
    private boolean mTwoPane;
    private FloatingActionButton add_movie_bt;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        setTitle("All Movies");

        dl = findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.open_drawer, R.string.close_drawer);

        dl.addDrawerListener(t);
        t.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.allmovies:
                        startActivity(new Intent(MoviesActivity.this, MoviesActivity.class));
                        break;
                    case R.id.unseenmovies:
                        startActivity(new Intent(MoviesActivity.this, UnseenActivity.class));
                       break;
                    case R.id.seenmovies:
                        startActivity(new Intent(MoviesActivity.this, SeenActivity.class));
                       break;
                    default:
                        return true;
                }


                return true;

            }
        });



        // DummyContent dummyContent = new DummyContent();
        //  moviesArrayList = dummyContent.getMoviesList();
        recyclerView = findViewById(R.id.rv_movies);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);


        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        add_movie_bt = findViewById(R.id.add_movie_btn);
        add_movie_bt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MoviesActivity.this, AddMovieActivity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {
        super.onResume();
        loadMovies();
    }

    private void populateMovies(final ArrayList<Movie> movies) {
        moviesArrayList = movies;
        mAdapter = new MoviesAdapter(moviesArrayList, new AdapterItemClickListener() {
            public void onItemClick(View view, int position) {
               onClicked(view,position);

            }
        });
      recyclerView.setAdapter(mAdapter);
    }

    private void onClicked(View view, int position){
        Movie clickedMovie = moviesArrayList.get(position);
        Bundle arguments = new Bundle();
        arguments.putString(Constants.MOVIE_NAME, clickedMovie.getName());
        arguments.putString(Constants.MOVIE_GENRE, clickedMovie.getGenre());
        arguments.putFloat(Constants.MOVIE_RATING, clickedMovie.getRating());
        arguments.putString(Constants.MOVIE_DESCRIPTION, clickedMovie.getShortDesc());
        arguments.putString(Constants.MOVIE_PHOTO, clickedMovie.getPhotoBase64());
        arguments.putString(Constants.MOVIE_ACTORS, clickedMovie.getActors());
        if (mTwoPane) {
            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        } else {
            Context context = view.getContext();
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtras(arguments);
            context.startActivity(intent);
        }
    }


    private void loadMovies(){
       // MovieService service = RetrofitClient.getRetrofitInstance().create(MovieService.class); //se conecteaza la url-ul specificat
        myDBHandler dbHandler = new myDBHandler(this, null, null, 1);
        List<Movie> movies = dbHandler.getMovies();
        populateMovies((ArrayList<Movie>) movies);
    }
}
