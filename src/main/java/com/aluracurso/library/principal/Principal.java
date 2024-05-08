package com.aluracurso.library.principal;

import com.aluracurso.library.model.Data;
import com.aluracurso.library.service.APIRequest;
import com.aluracurso.library.service.DataConverter;

import java.util.Scanner;

public class Principal {
   //private Scanner reading = new Scanner(System.in);
    private APIRequest apiRequest = new APIRequest();
    private final String URL_BASE = "https://gutendex.com/books/";
    private DataConverter converter = new DataConverter();

    public void showMenu(){
//        System.out.println("Ingrese el titulo del libro que desea buscar");
//        var titleBook = reading.nextLine();

        var json = apiRequest.getData(URL_BASE);
        var data = converter.getData(json, Data.class);
        System.out.println(data);


    }
}
