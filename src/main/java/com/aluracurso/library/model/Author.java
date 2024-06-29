package com.aluracurso.library.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Authors")
public class Author {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String birthDate;
    private String death_year;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> bookList = new ArrayList<>();

    public Author(){
    }
    public Author(DataAuthor dataAuthor) {
        this.name =  dataAuthor.name();
        this.birthDate = dataAuthor.birthDate();
        this.death_year = dataAuthor.death_year();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeath_year() {
        return death_year;
    }

    public void setDeath_year(String death_year) {
        this.death_year = death_year;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        bookList.forEach(b -> b.setAuthor(this));
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "-------------Autor-----------------\n" +
                " Nombre:" + name + '\n' +
                " Fecha de nacimiento:"  + birthDate + '\n' +
                " Fecha de fallecimiento: " + death_year + '\n' +
                "Libros: " + bookList.stream().map(b-> b.getTitle()).toList();
    }
}
