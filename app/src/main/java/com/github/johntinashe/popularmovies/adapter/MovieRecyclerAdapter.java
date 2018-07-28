package com.github.johntinashe.popularmovies.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.johntinashe.popularmovies.DetailActivity;
import com.github.johntinashe.popularmovies.R;
import com.github.johntinashe.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private static final String imageBaseURL = "http://image.tmdb.org/t/p/w342/";
    private final ArrayList<Movie> movies;
    private final Context mContext;


    public MovieRecyclerAdapter(ArrayList<Movie> movies, Context context) {
        this.movies = movies;
        this.mContext =context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item,null,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        final Movie movie = movies.get(position);
        holder.setPoster(movie.getPoster());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("movie_title",movie.getTitle());
                intent.putExtra("movie_backdrop",movie.getBackdrop());
                intent.putExtra("movie_date",movie.getDate());
                intent.putExtra("movie_plot",movie.getPlot());
                intent.putExtra("movie_poster",movie.getPoster());
                intent.putExtra("movie_vote",movie.getVote());
                intent.putExtra("movie_lang",movie.getLang());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        final ImageView poster;

         MovieViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.movie_poster);
        }

         void setPoster(String poster) {
            Picasso.get().load(imageBaseURL+poster).into(this.poster);
        }

    }
}
