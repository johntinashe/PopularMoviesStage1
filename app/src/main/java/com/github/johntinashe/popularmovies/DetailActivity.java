package com.github.johntinashe.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.johntinashe.popularmovies.databinding.ActivityDetailBinding;
import com.github.johntinashe.popularmovies.model.Movie;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        if (getSupportActionBar() != null) getSupportActionBar().hide();
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Intent i = getIntent();
        if (i == null) {
            error();
        }else {

            Movie movie = new Movie();
            movie.setBackdrop(i.getStringExtra("movie_backdrop"));
            movie.setDate(i.getStringExtra("movie_date"));
            movie.setVote(i.getFloatExtra("movie_vote",0));
            movie.setTitle(i.getStringExtra("movie_title"));
            movie.setPlot(i.getStringExtra("movie_plot"));
            movie.setPoster(i.getStringExtra("movie_poster"));
            movie.setLang(i.getStringExtra("movie_lang"));
            binding.setMovie(movie);
        }

    }

    private void error() {
        finish();
        Toast.makeText(this, R.string.error_no_movie, Toast.LENGTH_SHORT).show();
    }

}
