package com.onedayfirm.gorodabot.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class JsonParser {

    private Object currentObject;

    public JsonParser(String json) throws ParseException {
        try {
            currentObject = new JSONParser().parse(json);
        } catch (org.json.simple.parser.ParseException exception) {
            throw new ParseException(exception.getMessage());
        }
    }

    public JsonParser comeDown(String property) throws ParseException {
        var object = ((JSONObject) currentObject).get(property);
        if (object == null)
            throw new ParseException("The key '" + property + "' isn't in the json");
        else
            currentObject = object;
        return this;
    }

    public void forEach(Consumer<Map> consumer) throws ParseException {
        try {
            for (var obj : (JSONArray) currentObject)
                consumer.accept((JSONObject) obj);
        } catch (ClassCastException exception) {
            throw new ParseException("JSONObject cannot be cast to JSONArray");
        }
    }

    public JsonParser choose(Predicate<Map> selector) throws ParseException {
        try {
            for (var obj : (JSONArray) currentObject) {
                if (selector.test((JSONObject) obj)) {
                    currentObject = obj;
                    break;
                }
            }
        } catch (ClassCastException exception) {
            throw new ParseException("JSONObject cannot be cast to JSONArray");
        }
        return this;
    }

    public Object getValue(String key) throws ParseException {
        try {
            return ((JSONObject) currentObject).get(key);
        } catch (ClassCastException exception) {
            throw new ParseException("JSONArray cannot be cast to JSONObject");
        }
    }
}
