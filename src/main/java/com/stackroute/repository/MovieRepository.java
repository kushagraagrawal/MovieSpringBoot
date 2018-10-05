package com.stackroute.repository;

import com.stackroute.domain.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends CrudRepository<Movie, String> {
    public List<Movie> findByMovieName(String movieName);

}
