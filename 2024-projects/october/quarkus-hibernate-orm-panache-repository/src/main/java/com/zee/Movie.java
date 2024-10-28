package com.zee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 28 Oct, 2024
 */

@Getter
@Setter
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 200)
    private String description;
    private String director;
    @Column(nullable = false, length = 100)
    private String country;
    @Version //Don't use
    private long version=0L;
}
