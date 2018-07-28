package com.github.johntinashe.popularmovies.utils;

import com.github.johntinashe.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetMoviesFromJson {

    public static ArrayList<Movie> getMovies(String jsonString) {
        ArrayList<Movie> list;
        try {
            JSONObject popularMoviesObject = new JSONObject(jsonString);
            JSONArray results = popularMoviesObject.getJSONArray("results");

            int len = results != null ? results.length() : 0;
            list = new ArrayList<>();
            for (int i =0 ; i<len ; i++) {
                JSONObject movieObject = results.getJSONObject(i);
                list.add(getMovie(movieObject));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Movie getMovie(JSONObject object) {
        Movie movie;

        if (object != null) {
            movie = new Movie();
            try {
                movie.setPlot(object.getString("overview"));
                movie.setPoster(object.getString("poster_path"));
                movie.setTitle(object.getString("title"));
                movie.setVote((float) object.getDouble("vote_average"));
                movie.setDate(object.getString("release_date"));
                movie.setBackdrop(object.getString("backdrop_path"));
                movie.setId(object.getInt("id"));
                movie.setLang(object.getString("original_language"));
                return movie;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
