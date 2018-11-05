package com.onedayfirm.gorodabot.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.function.Function;

public class JsonParser {

    private Object currentObject;

    public JsonParser(String json) throws ParseException {
        try {
            currentObject = new JSONParser().parse(json);
        } catch (org.json.simple.parser.ParseException exception) {
            throw new ParseException(exception.getMessage());
        }
    }

    public JsonParser comeDown(String property) {
        currentObject = ((JSONObject) currentObject).get(property);
        return this;
    }

    public JsonParser chooseFirst(String property, Function<Object, Boolean> selector) {
        for (var obj : (JSONArray) currentObject) {
            var entry = (JSONObject) obj;
            if (selector.apply(entry.get(property))) {
                currentObject = entry;
                break;
            }
        }
        return this;
    }

    public Object getValue(String key) {
        return ((JSONObject) currentObject).get(key);
    }
}
