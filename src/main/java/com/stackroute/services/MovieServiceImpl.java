package com.stackroute.services;


import com.stackroute.domain.Movie;
import com.stackroute.exception.EmptyDBException;
import com.stackroute.exception.MovieAlreadyExistsException;
import com.stackroute.exception.MovieNotFoundException;
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
    public List<Movie> getAllMovies() throws EmptyDBException {
        if(movieRepository.findAll().size() == 0)
            throw new EmptyDBException("DB is empty");
        return (List<Movie>) movieRepository.findAll();
    }

    @Override
    public boolean deleteMovieById(String movieId) throws MovieNotFoundException {
        if(!movieRepository.findById(movieId).isPresent())
            throw new MovieNotFoundException("movie not found");
        movieRepository.deleteById(movieId);
        return true;
    }

    @Override
    public Movie updateMovie(String movieId, Movie movie) throws MovieNotFoundException {
        if(!movieRepository.findById(movieId).isPresent())
            throw new MovieNotFoundException("Movie Not Found");

        Movie movieretrieved = movieRepository.findByid(movieId);
        Movie storedMovie = (Movie) movieretrieved;
        storedMovie.setMovieName(movie.getMovieName());
        storedMovie.setMovieRating(movie.getMovieRating());
        storedMovie.setMovieReview(movie.getMovieReview());
        return movieRepository.save(storedMovie);
    }

    @Override
    public Movie getMovieById(String movieId) throws MovieAlreadyExistsException, MovieNotFoundException {
        if(movieRepository.findById(movieId).isPresent())
            throw new MovieAlreadyExistsException("Movie already exists");
        if(!movieRepository.findById(movieId).isPresent())
            throw new MovieNotFoundException("Movie not found");
        return movieRepository.findByid(movieId);
    }

    @Override
    public List<Movie> getMovieByMovieName(String movieName) throws MovieNotFoundException {
        if(movieRepository.findByMovieName(movieName).size() ==0)
            throw new MovieNotFoundException("movie not found");
        return movieRepository.findByMovieName(movieName);
    }

}
