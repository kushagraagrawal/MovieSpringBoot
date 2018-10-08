package com.stackroute.services;

import com.stackroute.domain.Movie;
import com.stackroute.repository.MovieRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieService{
    public Movie saveMovie(Movie movie);
    public List<Movie> getAllMovies();
    public boolean deleteMovieById(String movieId);
    public Movie updateMovie(String movieId, Movie movie);
    public Optional<Movie> getMovieById(String movieId);//return Movie object
    public List<Movie> getMovieByMovieName(String movieName);
}
