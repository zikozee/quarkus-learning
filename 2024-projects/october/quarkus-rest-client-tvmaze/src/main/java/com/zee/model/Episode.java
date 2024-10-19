package com.zee.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 19 Oct, 2024
 */

@Getter
@Setter
public class Episode {

    private Long id;
    private String name;
    private Long season;
    private String summary;

}
