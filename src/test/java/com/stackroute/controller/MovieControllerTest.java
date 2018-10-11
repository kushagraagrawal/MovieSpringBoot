package com.stackroute.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.domain.Movie;

import com.stackroute.exception.EmptyDBException;
import com.stackroute.exception.MovieAlreadyExistsException;
//import com.stackroute.exception.UserAlreadyExistException;
//import com.stackroute.service.UserService;
import com.stackroute.exception.MovieNotFoundException;
import com.stackroute.services.MovieService;
import com.stackroute.services.MovieServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//find out difference between @Mock and @MockBean

@RunWith(SpringRunner.class)
@WebMvcTest
public class MovieControllerTest {


    @Autowired
    private MockMvc mockMvc;
    private Movie movie;

    @MockBean
    private MovieServiceImpl movieService;

    @InjectMocks
    private MovieController movieController;

    private List<Movie> list =null;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        movie = new Movie();
        movie.setMovieName("Johnnyenglish");
        movie.setMovieReview("good movie");
        movie.setMovieRating(5);
        movie.setId("1");
        list = new ArrayList();

        list.add(movie);
    }

    @Test
    public void saveMovie() throws Exception {
        when(movieService.saveMovie(any())).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());


    }
      @Test
     public void saveMovieFailure() throws Exception {
        when(movieService.saveMovie(any())).thenThrow(MovieAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie")
        .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllMovies() throws Exception {
        when(movieService.getAllMovies()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie")
        .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void getAllMoviesFailure() throws Exception{
        when(movieService.getAllMovies()).thenThrow(EmptyDBException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie")
        .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void searchMovieByName() throws Exception{
        when(movieService.getMovieByMovieName((String)any())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/Johnny")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void searchMovieByNameFailure() throws Exception{
        when(movieService.getMovieByMovieName((String)any())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/Johnny")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateMovie() throws Exception{
        when(movieService.updateMovie(eq(movie.getId()), (Movie)any())).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/movie/1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateMovieFailure() throws Exception{
        when(movieService.updateMovie(eq(movie.getId()), (Movie)any())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/movie/1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteMovie() throws Exception {
        //movieService.saveMovie(movie);
        when(movieService.deleteMovieById((String)any())).thenReturn(true);
        System.out.println(list);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movie/1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteMovieFailure() throws Exception{
        when(movieService.deleteMovieById((String)any())).thenThrow(MovieNotFoundException.class);
        System.out.println(list);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movie/1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }




    private static String asJsonString(final Object obj)
    {
        try{
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }










}
