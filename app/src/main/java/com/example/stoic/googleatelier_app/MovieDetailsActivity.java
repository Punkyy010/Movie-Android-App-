package com.example.stoic.googleatelier_app;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    private FloatingActionButton add_movie_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "On create called");
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(bundle);
            System.out.println(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
        }else{
            Toast.makeText(this,"no bundle was received",Toast.LENGTH_LONG).show();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MoviesActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}
