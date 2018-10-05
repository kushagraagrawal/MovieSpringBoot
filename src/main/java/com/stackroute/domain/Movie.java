package com.stackroute.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Movie {

    @Id
    private String id;
    private int movieRating;
    private String movieReview;
    private String movieName;

    public Movie() {
    }

    public Movie(String movieName, String id, int movieRating, String movieReview) {
        this.movieName = movieName;
        this.id = id;
        this.movieRating = movieRating;
        this.movieReview = movieReview;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(int movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieReview() {
        return movieReview;
    }

    public void setMovieReview(String movieReview) {
        this.movieReview = movieReview;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieRating=" + movieRating +
                ", movieReview='" + movieReview + '\'' +
                ", movieName='" + movieName + '\'' +
                '}';
    }
}
