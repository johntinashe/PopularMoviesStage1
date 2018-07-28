package com.github.johntinashe.popularmovies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.johntinashe.popularmovies.adapter.MovieRecyclerAdapter;
import com.github.johntinashe.popularmovies.model.Movie;
import com.github.johntinashe.popularmovies.utils.GetMoviesFromJson;
import com.github.johntinashe.popularmovies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String API_KEY="REPLACE WITH YOUR tmdb API KEY";
    private static final String MY_PREFERENCES= "MY_PREF";
    private SharedPreferences mSharedPreferences;

    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.recycler_view_movie_list) RecyclerView mRecyclerView;
    @BindView(R.id.no_results_tv) TextView mNoResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mSharedPreferences = getApplicationContext().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        mRecyclerView.setHasFixedSize(true);
        getData();
    }

    private void getData() {
        String key = getOrderValue();
        if (key.equals(getString(R.string.default_value))) {
            setTitle(getString(R.string.app_name));
        }else {
            setTitle(getString(R.string.top_rated));
        }
        String url = "http://api.themoviedb.org/3/movie/" +key+ "?api_key="+API_KEY;
        new GetMoviesAsyncTask().execute(url);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.sort_type_key))) getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }


    @SuppressLint("StaticFieldLeak")
    class GetMoviesAsyncTask extends AsyncTask<String,Void,ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            mNoResults.setVisibility(View.INVISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            URL url;
            try {
                url = new URL(strings[0]);
                String stringJSON = NetworkUtils.getResponseFromHttpUrl(url);
                ArrayList<Movie> list = GetMoviesFromJson.getMovies(stringJSON);
                if(list != null) return  list;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

         @Override
         protected void onPostExecute(ArrayList<Movie> movies) {
             super.onPostExecute(movies);
             MovieRecyclerAdapter  adapter = new MovieRecyclerAdapter(movies,getApplicationContext());
             mRecyclerView.setAdapter(adapter);
             progressBar.setVisibility(View.INVISIBLE);
             if (movies == null) {
                 mNoResults.setVisibility(View.VISIBLE);
             } else {
                 mNoResults.setVisibility(View.INVISIBLE);
             }
         }
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.menu_main, menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh :
                getData();
                break;
            case R.id.action_popular:
                setCriteria(getString(R.string.sort_by_popularity));
                break;
            case R.id.action_top_rated:
                setCriteria(getString(R.string.sort_by_rating));
        }
         return super.onOptionsItemSelected(item);
    }

    public String getOrderValue() {
        String keyForOrder = getString(R.string.sort_type_key);
        String defaultValue = getString(R.string.default_value);
        return mSharedPreferences.getString(keyForOrder, defaultValue);
    }

    public void setCriteria(String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(getString(R.string.sort_type_key),value);
        editor.apply();
    }

}