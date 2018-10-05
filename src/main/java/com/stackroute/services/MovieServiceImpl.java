package com.stackroute.services;


import com.stackroute.domain.Movie;
import com.stackroute.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {



    @Autowired
    MovieRepository movieRepository;

    public Movie saveMovie(Movie movie){
        //Movie x = movieRepository.save(movie);
        return movieRepository.save(movie);
    }
    @Override
    public List<Movie> getAllMovies(){
        return (List<Movie>) movieRepository.findAll();
    }

    @Override
    public void deleteMovieById(String movieId) {
        movieRepository.deleteById(movieId);
    }

    @Override
    public Movie updateMovie(String movieId, Movie movie) {
        Optional<Movie> movieretrieved = movieRepository.findById(movieId);
        Movie storedMovie = (Movie) movieretrieved.get();
        storedMovie.setMovieName(movie.getMovieName());
        storedMovie.setMovieRating(movie.getMogvieRatin());
        storedMovie.setMovieReview(movie.getMovieReview());
        return movieRepository.save(storedMovie);
    }

    @Override
    public Optional<Movie> getMovieById(String movieId) {
        return movieRepository.findById(movieId);
    }

    @Override
    public List<Movie> getMovieByMovieName(String movieName) {
        return movieRepository.findByMovieName(movieName);
    }

}
