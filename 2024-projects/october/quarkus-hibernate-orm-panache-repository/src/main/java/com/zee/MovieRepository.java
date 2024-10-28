package com.zee;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 28 Oct, 2024
 */

@ApplicationScoped
public class MovieRepository implements PanacheRepository<Movie> {


    public List<Movie> findByCountry(String country) {
        return list("SELECT m FROM Movie m WHERE m.country = ?1 ORDER BY country DESC", country);
    }

    public int updateMovie(Movie movie, long id) {
        return update("UPDATE Movie m set m.title=?1, m.description=?2, m.director=?3, m.country=?4 " +
                "where id=?5", movie.getTitle(), movie.getDescription(), movie.getDirector(), movie.getCountry(), id);
    }
}
