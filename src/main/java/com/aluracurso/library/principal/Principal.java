package com.aluracurso.library.principal;

import com.aluracurso.library.model.Data;
import com.aluracurso.library.model.DataBooks;
import com.aluracurso.library.service.APIRequest;
import com.aluracurso.library.service.DataConverter;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner reading = new Scanner(System.in);
    private APIRequest apiRequest = new APIRequest();
    private final String URL_BASE = "https://gutendex.com/books/";
    private DataConverter converter = new DataConverter();


    public void showMenu(){
        var option=-1;
        while (option !=0){
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrado
                    4 - Listar autores vivos de un determinando año
                    5 - listar libros por idima
                    0 - Salir
                    """;
            System.out.println(menu);
            option = reading.nextInt();
            reading.nextLine();
            switch (option){
                case 1:
                    getDataBook();
                    break;
                case 2:
                    System.out.println("opcion 2");
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
    private Data getDataBook(){
        System.out.println("Ingrese el titulo del libro que desea buscar");
        var titleBook = reading.nextLine();
        var json = apiRequest.getData(URL_BASE+"?search="+titleBook.replace(" ", "+"));
        var data = converter.getData(json, Data.class);

        Optional<DataBooks> searchedBook = data.booksList().stream()
                .filter(b ->b.title().toUpperCase().contains(titleBook.toUpperCase()))
                .findFirst();
        if (searchedBook.isPresent()){
            //System.out.println("Libro encontrado");
            System.out.println(searchedBook.get());
        }else {
            System.out.println("Libro no encontrado");
        }
        return data;

    }
}
