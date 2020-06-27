package com.bricemi.resumeapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;


public class Mapper {

    public static <T> T convert(String string, Class<T> pojo) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(string, pojo);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
