package com.example.jsonlogictosql.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JsonLogicOperator {
    private String jsonOperator;
    private String sqlOperator;
    private JsonLogicOperatorType type;
}
