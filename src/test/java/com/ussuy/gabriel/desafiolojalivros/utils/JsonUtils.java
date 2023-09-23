package com.ussuy.gabriel.desafiolojalivros.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    public static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
