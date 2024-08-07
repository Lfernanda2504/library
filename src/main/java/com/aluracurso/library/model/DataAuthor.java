package com.aluracurso.library.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataAuthor(
        String name,
        @JsonAlias("birth_year")  Integer birthDate,
        @JsonAlias("death_year") Integer death_year



) {

}
