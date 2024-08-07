package com.aluracurso.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    //(nullable): para que no sea nulo
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Enumerated(EnumType.STRING)
    private Language language;
    private Double download_count;

    public Book(){}

    public Book(DataBook dataBook) {
        this.title = dataBook.title();
        this.author = new Author(dataBook.author().get(0));
        this.language = Language.fromString(dataBook.languages().toString().split(",")[0].trim());
        this.download_count = dataBook.download_count();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Double getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Double download_count) {
        this.download_count = download_count;
    }

    @Override
    public String toString() {
        return "___________________________________\n" +
                "Titulo: " + title + '\n' +
                "Autor: " + author.getName() + '\n' +
                "Idioma: " + language + '\n' +
                "numero de descargas: " + download_count +'\n'+
                "_____________________________________";
    }
}
