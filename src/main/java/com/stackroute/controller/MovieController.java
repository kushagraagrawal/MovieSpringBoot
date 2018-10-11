package com.stackroute.controller;

import com.stackroute.exception.EmptyDBException;
import com.stackroute.exception.MovieAlreadyExistsException;
import com.stackroute.exception.MovieNotFoundException;
import com.stackroute.domain.Movie;
import com.stackroute.repository.MovieRepository;
import com.stackroute.services.MovieService;
import com.stackroute.services.MovieServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "MovieCruiser", description = "operations related to movieapp")
@RequestMapping("/api/v1/movie")
public class MovieController {


    private MovieService movieService;
    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping() // maps the json in the payload to the object
    public ResponseEntity<?> saveMovie(@Valid @RequestBody Movie movie){
        ResponseEntity responseEntity;
        try {
            Movie movieThatWasSaved = movieService.saveMovie(movie);
            responseEntity = new ResponseEntity<Movie>(movieThatWasSaved, HttpStatus.CREATED);

        }catch (MovieAlreadyExistsException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateMovie(@PathVariable("id") String id,@Valid @RequestBody Movie movie){
        ResponseEntity responseEntity;
        try {

            Movie movieThatWasUpdated = movieService.updateMovie(id, movie);
            System.out.println(id);
            System.out.println(movie);
            System.out.println("test");
            responseEntity =  new ResponseEntity<Movie>(movieThatWasUpdated, HttpStatus.OK);
        }catch (MovieNotFoundException e){
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND); //check
        }
        return responseEntity;
    }

    @GetMapping()
    public ResponseEntity<?> getAllMovies(){

        ResponseEntity responseEntity;
        try {
            List<Movie> movieList = movieService.getAllMovies();
            responseEntity = new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
        }catch (EmptyDBException e){
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);//check
        }
        return responseEntity;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMovieById(@Valid @PathVariable("id") @RequestBody  String id){
        try{
            System.out.println(id);
            System.out.println(movieService.getMovieById(id));
            movieService.deleteMovieById(id);
            List<Movie> movieList = movieService.getAllMovies();
//            if(movieList.size() ==0)
//                throw new EmptyDBException("movie DB is empty");

            return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
        }catch (MovieNotFoundException e){
            System.out.println("hehehe");
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);//check
        }catch (EmptyDBException e){
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.NO_CONTENT);
        }catch (MovieAlreadyExistsException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("{movieName}")
    public ResponseEntity<?> searchMovieByName(@Valid @PathVariable("movieName")  String movieName){

        try{
            List<Movie> moviesByName = movieService.getMovieByMovieName(movieName);
            return new ResponseEntity<List<Movie>>(moviesByName, HttpStatus.OK);
        }catch (MovieNotFoundException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);//check
        }

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex){
        return ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
