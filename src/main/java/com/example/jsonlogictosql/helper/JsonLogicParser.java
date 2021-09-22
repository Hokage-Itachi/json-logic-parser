package com.example.jsonlogictosql.helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.*;

public class JsonLogicParser {
    private static List<JsonLogicOperator> operators;
    private static final Stack<String> keysStack = new Stack<>();
    private static final Queue<JsonElement> jsonElementQueue = new LinkedList<>();

    public String stringToJsonElement(String json) {
        String condition = "";
        JsonElement root = JsonParser.parseString(json);

        condition = parseJsonElement(root, condition);
        return condition;
    }

    public String parseJsonElement(JsonElement root, String condition) {
        // Handle null
        if (root == null || root.isJsonNull()) {
            return condition;
        }
        // Handle json object
        if (root.isJsonObject()) {
            String operator = root.getAsJsonObject().keySet().toArray()[0].toString();
            JsonLogicOperator jsonLogicOperator = getJsonOperator(operator);
            if (jsonLogicOperator != null && jsonLogicOperator.getType() == JsonLogicOperatorType.LOGIC) { // Handle logic operators
                condition += "(";
                keysStack.push(operator);
                JsonElement children = root.getAsJsonObject().get(operator);
                return parseJsonElement(children, condition);
            } else if (jsonLogicOperator != null && jsonLogicOperator.getType() == JsonLogicOperatorType.COMPARE) { // Handle compare operators
                JsonArray jsonArray = root.getAsJsonObject().get(operator).getAsJsonArray();
                String key = jsonArray.get(0).getAsJsonObject().get("var").toString();
                String value = jsonArray.get(1).getAsString();
                condition += key + operator + value + " " + keysStack.peek().toUpperCase() + " ";
                return parseJsonElement(jsonElementQueue.poll(), condition);
            }

        } else if (root.isJsonArray()) { // Handle json array
            JsonArray jsonArray = root.getAsJsonArray();
            for (JsonElement e : jsonArray) {
                jsonElementQueue.add(e);
            }
            return parseJsonElement(jsonElementQueue.poll(), condition);
        }
        return condition;

    }


    private static JsonLogicOperator getJsonOperator(String jsonOperator) {
        for (JsonLogicOperator s : getListOperator()) {
            if (s.getJsonOperator().equals(jsonOperator)) {
                return s;
            }
        }
        return null;
    }

    private static List<JsonLogicOperator> getListOperator() {
        if (operators == null || operators.isEmpty()) {
            initOperatorList();
        }
        return operators;
    }


    private static void initOperatorList() {
        operators = new ArrayList<>();

        operators.add(new JsonLogicOperator("and", "AND", JsonLogicOperatorType.LOGIC));
        operators.add(new JsonLogicOperator("or", "OR", JsonLogicOperatorType.LOGIC));
        operators.add(new JsonLogicOperator("not", "NOT", JsonLogicOperatorType.LOGIC));
        operators.add(new JsonLogicOperator("==", "=", JsonLogicOperatorType.COMPARE));
        operators.add(new JsonLogicOperator("!=", "<>", JsonLogicOperatorType.COMPARE));
        operators.add(new JsonLogicOperator("!", "IS EMPTY", JsonLogicOperatorType.COMPARE));
        operators.add(new JsonLogicOperator("!!", "IS NOT EMPTY", JsonLogicOperatorType.COMPARE));
        operators.add(new JsonLogicOperator("in", "LIKE", JsonLogicOperatorType.COMPARE));
        operators.add(new JsonLogicOperator("<", "<", JsonLogicOperatorType.COMPARE));
        operators.add(new JsonLogicOperator("<=", "<=", JsonLogicOperatorType.COMPARE));
        operators.add(new JsonLogicOperator(">", ">", JsonLogicOperatorType.COMPARE));
        operators.add(new JsonLogicOperator(">=", ">=", JsonLogicOperatorType.COMPARE));
    }
}