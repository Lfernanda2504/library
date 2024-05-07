package com.aluracurso.library.service;

public interface IDataConverter {
    <T> T getData(String json, Class<T> tClass);
}
