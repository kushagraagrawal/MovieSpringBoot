package com.stackroute.services;


import com.stackroute.domain.Movie;
import com.stackroute.exception.MovieAlreadyExistsException;
import com.stackroute.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class MovieServiceImpl implements MovieService {



    @Autowired
    MovieRepository movieRepository;

    public Movie saveMovie(Movie movie) throws MovieAlreadyExistsException{
        //Movie x = movieRepository.save(movie);
        if(movieRepository.existsById(movie.getId())){
            throw new MovieAlreadyExistsException("Movie Already Exists");
        }
        Movie saveMovie = movieRepository.save(movie);
        if(saveMovie == null){
            throw new MovieAlreadyExistsException("Movie Already Exists");
        }
        return saveMovie;
    }
    @Override
    public List<Movie> getAllMovies(){
        return (List<Movie>) movieRepository.findAll();
    }

    @Override
    public boolean deleteMovieById(String movieId) {
        movieRepository.deleteById(movieId);
        return true;
    }

    @Override
    public Movie updateMovie(String movieId, Movie movie) {
        Optional<Movie> movieretrieved = movieRepository.findById(movieId);
        Movie storedMovie = (Movie) movieretrieved.get();
        storedMovie.setMovieName(movie.getMovieName());
        storedMovie.setMovieRating(movie.getMovieRating());
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
