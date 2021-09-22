package com.example.jsonlogictosql.controller;

import com.example.jsonlogictosql.helper.JsonLogicParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    private final JsonLogicParser jsonParser = new JsonLogicParser();

    @PostMapping("/")
    public ResponseEntity<Object> home(@RequestBody String requestBody) throws JsonProcessingException {
        String sql = jsonParser.stringToJsonElement(requestBody);
//        String sql = JacksonParser.parse(requestBody);
        return new ResponseEntity<>(sql, HttpStatus.OK);

    }
}
