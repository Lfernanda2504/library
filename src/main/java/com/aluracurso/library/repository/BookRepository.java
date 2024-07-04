package com.aluracurso.library.repository;

import com.aluracurso.library.LibraryApplication;
import com.aluracurso.library.model.Book;
import com.aluracurso.library.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleContainsIgnoreCase(String titleBook);

    List<Book> findByLanguage(Language language);
}
