package com.stackroute.repository;


import com.stackroute.domain.Movie;
//import com.stackroute.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


//This is integrated test we need DB server
@RunWith(SpringRunner.class)
@DataMongoTest
//@SpringBootTest
public class MovieRepositoryTest{

    @Autowired
    MovieRepository movieRepository;

    Movie movie;

    @Before
    public void setUp()
    {
        movie = new Movie();
        movie.setMovieName("Johnny");
        movie.setMovieReview("good movie");
        movie.setId("1");
        movie.setMovieRating(4);

    }

    @After
    public void tearDown(){

        movieRepository.deleteAll();
    }


    @Test
    public void testSaveUser(){
     movieRepository.save(movie);
     Movie fetchUser = movieRepository.findById(movie.getId()).get();
        Assert.assertEquals("1",fetchUser.getId());

    }

    @Test
    public void testSaveUserFailure(){
        Movie testUser = new Movie("POTC","2",3,"decent flick");
        movieRepository.save(movie);
        Movie fetchUser = movieRepository.findById(movie.getId()).get();
        Assert.assertNotSame(movie,fetchUser);
    }

    @Test
    public void testGetAllUser(){
        Movie u = new Movie("HarryPotter","3",4,"great movie");
        Movie u1 = new Movie("catwoman","4",1,"horrible");
        movieRepository.save(u);
        movieRepository.save(u1);

        List<Movie> list = movieRepository.findAll();
        Assert.assertEquals("HarryPotter",list.get(0).getMovieName());
    }

    @Test
    public void testGetAllUserFailure(){
        Movie m = new Movie("POTC", "2", 3, "decent flicj");
        movieRepository.save(m);
        List<Movie> list = movieRepository.findAll();
        Assert.assertNotEquals("Johnny", list.get(0).getMovieName());
    }

    @Test
    public void testFindByMovieName(){
        Movie u = new Movie("HarryPotter","3",4,"great movie");
        Movie u1 = new Movie("catwoman","4",1,"horrible");
        movieRepository.save(u);
        movieRepository.save(u1);
        List<Movie> list = movieRepository.findByMovieName("HarryPotter");
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testFindByMovieNameFailure(){
        Movie u = new Movie("HarryPotter","3",4,"great movie");
        Movie u1 = new Movie("catwoman","4",1,"horrible");
        movieRepository.save(u);
        movieRepository.save(u1);
        List<Movie> list = movieRepository.findByMovieName("POTC");
        Assert.assertNotEquals(2, list.size());
    }

}
