package com.aluracurso.library.repository;

import com.aluracurso.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>  {
    Optional<Author> findByName(String name);


    //@Query("SELECT a FROM Author a WHERE a.birthDate <= :year AND (a.death_year IS NULL OR a.death_year >= :year)")

    //(sql): SELECT a.*
    //FROM Authors a
    //WHERE 1670 BETWEEN a.birth_date AND a.death_year;
    //busca que este dentro del rango
    @Query("SELECT a FROM Author a WHERE :year BETWEEN a.birthDate AND a.death_year")
    List<Author> findAliveAuthors(@Param("year") int year);




}
