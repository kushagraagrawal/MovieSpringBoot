package com.stackroute.controller;

import com.stackroute.Exception.EmptyDBException;
import com.stackroute.Exception.MovieAlreadyExistsException;
import com.stackroute.Exception.MovieNotFoundException;
import com.stackroute.domain.Movie;
import com.stackroute.repository.MovieRepository;
import com.stackroute.services.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieServiceImpl movieServiceImpl;

    @PostMapping() // mpas the json in the payload to the object
    public ResponseEntity<?> saveMovie(@RequestBody Movie movie){
        ResponseEntity responseEntity;
        try {
            if(movieRepository.findById(movie.getId()).isPresent()){
                throw new MovieAlreadyExistsException("Movie Already Exists");
            }

            Movie movieThatWasSaved = movieServiceImpl.saveMovie(movie);
            responseEntity = new ResponseEntity<Movie>(movieThatWasSaved, HttpStatus.CREATED);

        }catch (MovieAlreadyExistsException e){
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateMovie(@PathVariable("id") String id, @RequestBody Movie movie){
        ResponseEntity responseEntity;
        try {
            if(!movieRepository.findById(id).isPresent())
                throw new MovieNotFoundException("ID not present");

            Movie movieThatWasUpdated = movieServiceImpl.updateMovie(id, movie);
            responseEntity =  new ResponseEntity<Movie>(movieThatWasUpdated, HttpStatus.OK);
        }catch (MovieNotFoundException e){
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
        }

        return responseEntity;
    }

    @GetMapping()
    public ResponseEntity<?> getAllMovies(){
        List<Movie> movieList = movieServiceImpl.getAllMovies();
        ResponseEntity responseEntity;
        try {
            if(movieList.size() ==0)
                throw new EmptyDBException("DB is empty");

            responseEntity = new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
        }catch (EmptyDBException e){
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
        }
        return responseEntity;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMovieById(@PathVariable("id") @RequestBody  String id){
        try{
            if(!movieRepository.findById(id).isPresent())
                throw new MovieNotFoundException("id not found");
            movieServiceImpl.deleteMovieById(id);
            List<Movie> movieList = movieServiceImpl.getAllMovies();
            return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
        }catch (MovieNotFoundException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("{movieName}")
    public ResponseEntity<?> searchMovieByName(@PathVariable("movieName")  String movieName){
        List<Movie> moviesByName = movieServiceImpl.findByName(movieName);
        try{
            if(moviesByName.size() == 0)
                throw new MovieNotFoundException("the movie don't exist nibba");

            return new ResponseEntity<List<Movie>>(moviesByName, HttpStatus.OK);
        }catch (MovieNotFoundException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
        }

    }
}
