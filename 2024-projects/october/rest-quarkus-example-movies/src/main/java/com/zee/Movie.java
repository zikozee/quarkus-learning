package com.zee;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 16 Oct, 2024
 */

@Schema(name = "Movie", description = "Movie representation")
@Getter
@Setter
public class Movie{
    private Long id;
    @Schema(required = true)
    private String title;
}
