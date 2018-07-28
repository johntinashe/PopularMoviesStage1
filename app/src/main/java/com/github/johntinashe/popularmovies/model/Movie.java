package com.github.johntinashe.popularmovies.model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

@SuppressWarnings("ALL")
public class Movie {

    private static final String imageBaseURL = "http://image.tmdb.org/t/p/w342/";
    private static final String imageBaseBackdrop = "http://image.tmdb.org/t/p/w780/";

    private String title;
    private String poster;
    private float vote;
    private String plot;
    private String backdrop;
    private String date;
    private int id;
    private String lang;


    public Movie() {
    }

    public Movie(String title, String poster, float vote, String plot, String backdrop, String date, int id, String lang) {
        this.title = title;
        this.poster = poster;
        this.vote = vote;
        this.plot = plot;
        this.backdrop = backdrop;
        this.date = date;
        this.id = id;
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public float getVote() {
        return vote;
    }

    public void setVote(float vote) {
        this.vote = vote;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @BindingAdapter({"android:poster"})
    public static void loadPoster(ImageView view, String imageUrl) {
        Picasso.get().load(imageBaseURL+imageUrl).into(view);
    }

    @BindingAdapter({"android:backdrop"})
    public static void loadBackdrop(ImageView view, String imageUrl) {
        Picasso.get().load(imageBaseBackdrop+imageUrl).into(view);
    }

    @BindingAdapter({"android:vote"})
    public static void setRatingBar(RatingBar ratingBar ,float value) {
        ratingBar.setRating((float) (value/2.0));
    }

    @BindingAdapter({"android:vote"})
    public static void setRating(TextView rating , float value) {
        rating.setText(String.format("%s", value));
    }




}
