package com.stackroute.service;


import com.stackroute.domain.Movie;
import com.stackroute.exception.MovieAlreadyExistsException;
import com.stackroute.exception.MovieNotFoundException;
import com.stackroute.repository.MovieRepository;
import com.stackroute.services.MovieServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    Movie movie;

    //Create a mock for UserRepository
    @Mock//test double
            MovieRepository movieRepository;

    //Inject the mocks as dependencies into UserServiceImpl
    @InjectMocks
    MovieServiceImpl movieService;
    List<Movie> list= null;


    @Before
    public void setUp() throws Exception{
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        movie = new Movie();
        movie.setMovieName("Johnny");
        movie.setMovieReview("good movie");
        movie.setId("1");
        movie.setMovieRating(4);
        list = new ArrayList<>();
        list.add(movie);


    }

    @Test
    public void saveUserTestSuccess() throws MovieAlreadyExistsException {

        when(movieRepository.save((Movie) any())).thenReturn(movie);
        Movie savedUser = movieService.saveMovie(movie);
        Assert.assertEquals(movie,savedUser);

        //verify here verifies that userRepository save method is only called once
        verify(movieRepository,times(1)).save(movie);
      
    }

    @Test(expected = MovieAlreadyExistsException.class)
    public void saveUserTestFailure() throws MovieAlreadyExistsException {
        when(movieRepository.save((Movie) any())).thenReturn(null);
        Movie savedUser = movieService.saveMovie(movie);
        System.out.println("Movie Already Exists" + savedUser);
        Assert.assertEquals(movie,savedUser);
//add verify
       /*doThrow(new UserAlreadyExistException()).when(userRepository).findById(eq(101));
       movieService.saveUser(movie);*/


    }

    @Test
    public void getAllUser(){

        movieRepository.save(movie);
        //stubbing the mock to return specific data
        when(movieRepository.findAll()).thenReturn(list);
        List<Movie> movieList = movieRepository.findAll();
        Assert.assertEquals(list,movieList);

        //add verify
    }

    @Test
    public void deleteMovie() throws MovieNotFoundException{
        //when(movieRepository.save((Movie)any())).thenReturn(movie);
        when(movieRepository.findById((String)any())).thenReturn(java.util.Optional.of((Movie) movie));
        //System.out.println("movieTitlle" + movie.getMovieTitle());
        boolean deletedMovie = movieService.deleteMovieById(movie.getId());
        //System.out.println("deletedUser" + deletedUser);
        Assert.assertEquals(true,deletedMovie);

    }

    @Test
    public void getMovieByMovieName(){
        when(movieRepository.findByMovieName((String)any())).thenReturn(list);
        List<Movie> movieList = movieRepository.findByMovieName(movie.getMovieName());
        Assert.assertEquals(list, movieList);
    }




}
