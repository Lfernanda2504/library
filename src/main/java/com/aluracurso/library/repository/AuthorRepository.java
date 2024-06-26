package com.aluracurso.library.repository;

import com.aluracurso.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long>  {
    Author findByName(String nombre);
}
