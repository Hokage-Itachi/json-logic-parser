package com.example.jsonlogictosql.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String parse(String json) throws JsonProcessingException {
        String sql = "";

        JsonNode jsonNode = objectMapper.readTree(json);
        return sql;
    }
}
