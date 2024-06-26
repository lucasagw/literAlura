package br.com.alura.literAlura.util;

public interface IConvertsData {

    <T> T getData(String json, Class<T> clazz);
}
