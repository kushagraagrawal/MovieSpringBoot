package com.stackroute.services;

import com.stackroute.domain.Movie;
import com.stackroute.exception.EmptyDBException;
import com.stackroute.exception.MovieAlreadyExistsException;
import com.stackroute.exception.MovieNotFoundException;
import com.stackroute.repository.MovieRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieService{
    public Movie saveMovie(Movie movie) throws MovieAlreadyExistsException;
    public List<Movie> getAllMovies() throws EmptyDBException;
    public boolean deleteMovieById(String movieId) throws MovieNotFoundException;
    public Movie updateMovie(String movieId, Movie movie) throws MovieNotFoundException;
    public Movie getMovieById(String movieId) throws MovieAlreadyExistsException, MovieNotFoundException;//return Movie object
    public List<Movie> getMovieByMovieName(String movieName) throws MovieNotFoundException;
}
