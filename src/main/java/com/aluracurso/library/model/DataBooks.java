package com.aluracurso.library.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBooks(
        String title,
        @JsonAlias("authors") List<DataAuthor>author,
        List<String> languages,
        Double download_count
) {
}
