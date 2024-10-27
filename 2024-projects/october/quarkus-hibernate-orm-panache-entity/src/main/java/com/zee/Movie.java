package com.zee;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 27 Oct, 2024
 */


@Entity
public class Movie extends PanacheEntity {

    @Column(length = 100)
    public String title;

    @Column(length = 200)
    public String description;
    public String director;
    public String country;


}
