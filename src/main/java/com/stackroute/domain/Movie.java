package com.stackroute.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document

public class Movie {

    @Id
    //@Column(name = "id")
    private String id;

    //@Column(name = "movieRating")
    @NotNull
    private int movieRating;

    //@Column(name = "movieReview")
    @NotNull
    @Size(min = 9, message = "review cannot be empty and should have atleast 9 characters")
    private String movieReview;

    //@Column(name = "movieName")
    @NotNull
    @Size(min=9, message = "movieName cannot be null and should have atleast 9 characters")
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
