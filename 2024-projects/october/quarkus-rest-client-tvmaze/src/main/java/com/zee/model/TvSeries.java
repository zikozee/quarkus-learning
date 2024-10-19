package com.zee.model;

import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Set;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 19 Oct, 2024
 */

@Getter
@Setter
public class TvSeries {

    private Long id;
    private String name;
    private URL url;
    private String summary;
    private String language;
    private Set<String> genres;
    private URL officialSite;
}
