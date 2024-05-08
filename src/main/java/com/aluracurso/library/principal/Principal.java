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
        var json = apiRequest.getData(URL_BASE);
        var data = converter.getData(json, Data.class);
        System.out.println(data);


        //Top 10 libros mas descargados
        System.out.println("Top 10 libros mas descargados");
        data.booksList().stream()
                .sorted(Comparator.comparing(DataBooks::download_count).reversed())
                .limit(10)
                .map(b -> b.title().toUpperCase())
                .forEach(System.out::println);


        //busqueda de libros por nombre
            System.out.println("Ingrese el titulo del libro que desea buscar");
            var titleBook = reading.nextLine();
            json = apiRequest.getData(URL_BASE+"?search="+titleBook.replace(" ", "+"));
            var searchData = converter.getData(json, Data.class);
            Optional<DataBooks> searchedBook = searchData.booksList().stream()
                    .filter(b ->b.title().toUpperCase().contains(titleBook.toUpperCase()))
                    .findFirst();
            if (searchedBook.isPresent()){
                System.out.println("Libro encontrado");
                System.out.println(searchedBook.get());
            }else {
                System.out.println("Libro no encontrado");
            }

            //estadisticas
        DoubleSummaryStatistics sta = data.booksList().stream()
                .filter(d -> d.download_count() > 0)
                .collect(Collectors.summarizingDouble(DataBooks::download_count));
        System.out.println("Cantidad media de descargas: " +sta.getAverage() +
        " Cantidad máxima de descargas: " + sta.getMax() +
        " Cantidad mínima de descargas: "+sta.getMin() +
        " Cantidad de registros evaluados "+sta.getCount()
        );

    }
}
