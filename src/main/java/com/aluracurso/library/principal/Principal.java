package com.aluracurso.library.principal;

import com.aluracurso.library.model.*;
import com.aluracurso.library.repository.AuthorRepository;
import com.aluracurso.library.repository.BookRepository;
import com.aluracurso.library.service.APIRequest;
import com.aluracurso.library.service.DataConverter;
import org.antlr.v4.runtime.InputMismatchException;

import java.util.*;

public class Principal {
    private Scanner reading = new Scanner(System.in);
    private APIRequest apiRequest = new APIRequest();
    private final String URL_BASE = "https://gutendex.com/books/";
    private DataConverter converter = new DataConverter();
    private BookRepository repositoryB;
    private AuthorRepository repositoryA;
    private List <Book> books = new ArrayList<>();
    private List <Author> authors = new ArrayList<>();
    private Optional<Book> searchedBook;

    //
    public  Principal(BookRepository bookRepository, AuthorRepository authorRepository){
        this.repositoryA = authorRepository;
        this.repositoryB = bookRepository;
    }

    public void showMenu(){
        var option=-1;
        while (option !=0){
            var menu = """
                     -------------------------------------------
                         Bienvenido a Literalura
                     -------------------------------------------
                    1 - Buscar libro por titulo en la API
                    2 - Buscar libro por titulo registrado
                    3 - Listar libros registrados
                    4 - Listar autores registrado
                    5 - Listar autores vivos de un determinando año
                    6 - listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            while (!reading.hasNextInt()){
                System.out.println("Formato invalido, ingrese una opción del menu");
                reading.nextLine();
            }
            option = reading.nextInt();
            reading.nextLine();
            switch (option){
                case 1:
                    searchBookWeb();
                    break;
                case 2:
                    searchBookByTitle();
                    break;
                case 3:
                    showBooksSaved();
                    break;
                case 4:
                    showAuthors();
                    break;
                case 5:
                    getAliveAuthors();
                    break;
                case 6:
                    showBookByLanguage();
                    break;
                default:
                    System.out.println("Opción invalida");
            }

        }



//        //Top 10 libros mas descargados
//        System.out.println("Top 10 libros mas descargados");
//        data.booksList().stream()
//                .sorted(Comparator.comparing(DataBooks::download_count).reversed())
//                .limit(10)
//                .map(b -> b.title().toUpperCase())
//                .forEach(System.out::println);
//
//
//
//
//            //estadisticas
//        DoubleSummaryStatistics sta = data.booksList().stream()
//                .filter(d -> d.download_count() > 0)
//                .collect(Collectors.summarizingDouble(DataBooks::download_count));
//        System.out.println("Cantidad media de descargas: " +sta.getAverage() +
//        " Cantidad máxima de descargas: " + sta.getMax() +
//        " Cantidad mínima de descargas: "+sta.getMin() +
//        " Cantidad de registros evaluados "+sta.getCount()
//        );



    }

    // busca libros en la api
    private Data getDataBook(String titleBook){
        var json = apiRequest.getData(URL_BASE+"?search="+titleBook.replace(" ", "+"));
        var data = converter.getData(json, Data.class);
        //System.out.println(data);
        return data;
    }


    private void searchBookWeb() {
        System.out.println("Ingrese el titulo del libro que desea buscar");
        var titleBook = reading.nextLine();
        Data data = getDataBook(titleBook);
        DataBook foundBook = null;
        Optional<DataBook> searchedBook = data.booksList().stream()
                .filter(b ->b.title().toUpperCase().contains(titleBook.toUpperCase()))
                .findFirst();
        if (searchedBook.isPresent()){
            foundBook = searchedBook.get();
            DataAuthor dataAuthor = foundBook.author().get(0);
            String authorName = dataAuthor.name();
            Optional <Author> isAuthor = repositoryA.findByName(authorName);
            Author author;
            if (isAuthor.isPresent()){
                author = isAuthor.get();
            }else {
                author = new Author();
                author.setName(dataAuthor.name());
                author.setBirthDate(dataAuthor.birthDate());
                author.setDeath_year(dataAuthor.death_year());
                repositoryA.save(author);

            }
            Book book = new Book(foundBook);
            book.setAuthor(author);
            repositoryB.save(book);
            System.out.println("Libro encontrado y registrado");
            System.out.println(book.toString());

        }else {
            System.out.println("Libro no encontrado");
        }

    }

    private void searchBookByTitle(){
        System.out.println("Ingresa el titulo del libro : ");
        var bookTitle = reading.nextLine();
        searchedBook = repositoryB.findByTitleContainsIgnoreCase(bookTitle);

        if (searchedBook.isPresent()){
            System.out.println("____________________LIBRO_______________\n" +searchedBook.get().toString());
        }else {
            System.out.println("Libro no se encontró");
        }

    }

    private void showBooksSaved(){
        books = repositoryB.findAll();
        if (books.isEmpty()){
            System.out.println("No hay libros registrados ");
        }else {
            books.stream()
                    .forEach(System.out::println);
        }
    }

    private  void showAuthors(){
        authors = repositoryA.findAll();
        if (authors.isEmpty()){
            System.out.println("No hay autores registrados");
        }else {
            authors.stream()
                    .forEach(System.out::println);
        }

    }

    private void getAliveAuthors(){
        System.out.println("Ingrese el año para buscar autores vivos registrados");
        try {
            Integer year = reading.nextInt();
            authors = repositoryA.findAliveAuthors(year);
            if (authors.isEmpty()) {
                System.out.println("No hay autores vivos en este año");
            } else {
                authors.stream()
                        .filter(author -> author != null)
                        .forEach(System.out::println);
            }
        }catch (InputMismatchException e){
            System.out.println("Ingrese un año correcto ");
            reading.nextLine();
        }
    }
    private List <Book>searchedBooksBylanguage (String language){
        var data = Language.fromString(language);
        System.out.println("Lenguaje buscado: "+ data);

        List <Book> booksByLanguage = repositoryB.findByLanguage(data);
        return booksByLanguage;

    }

    private void showBookByLanguage(){
        System.out.println("Selecciona el lenguaje a buscar: ");

        var option = -1;
        while (option !=0){
            var othersoptions ="""
                     -------------------------------------------
                         Otras Opciones de Literalura
                     -------------------------------------------
                    1 - Inglés(en)
                    2 - Español(es) 
                    3 - Francés (fr)
                    4 - Portugués
                    0 - Salir
                    """;
            System.out.println(othersoptions);
            while (!reading.hasNextInt()){
                System.out.println("Formato invalido, ingrese un numero");
                reading.nextLine();
            }
            option = reading.nextInt();
            reading.nextLine();

           switch (option){
               case 1:
                   List<Book> booksEn= searchedBooksBylanguage("[en]");
                   booksEn.forEach(System.out::println);
                   break;
               case 2:
                   List<Book> booksEs= searchedBooksBylanguage("[es]");
                   booksEs.forEach(System.out::println);
                   break;
               case 3:
                   List<Book> booksFr= searchedBooksBylanguage("[fr]");
                   booksFr.forEach(System.out::println);
                   break;
               case 4:
                   List<Book> booksPt= searchedBooksBylanguage("[pt]");
                   booksPt.forEach(System.out::println);
                   break;
               case 0:
                   return;
               default:
                   System.out.println("ningun lenguaje seleccionado");
           }

        }
    }



}
