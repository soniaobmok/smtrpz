package com.concert.eurekaclient.Movie;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import org.springframework.data.jpa.repository.Modifying;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
    @Query("SELECT u FROM Movie u WHERE u.id = ?1")
    Movie find(Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Movie u SET u.title = :title, u.date = :date, u.company = :company, u.genre = :genre, u.duration = :duration, u.rating = :rating WHERE u.id = :id")
    Integer customUpdate(@Param("id") Integer id, @Param("title") String title,
                         @Param("date") Date date, @Param("genre") String genre,
                         @Param("company") String company, @Param("duration") Integer duration,
                         @Param("rating") Integer rating);
}
