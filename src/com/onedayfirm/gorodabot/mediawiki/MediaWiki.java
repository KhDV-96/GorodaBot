package com.onedayfirm.gorodabot.mediawiki;

import com.onedayfirm.gorodabot.json.JsonParser;
import com.onedayfirm.gorodabot.json.ParseException;
import com.onedayfirm.gorodabot.utils.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MediaWiki {

    private static final String API_URL = "https://ru.wikipedia.org/w/api.php";

    public Long search(String query, String keyWord) {
        var params = getSearchParameters(query);
        try (var request = new Request(API_URL)) {
            var json = request.get(params);
            return (Long) new JsonParser(json)
                    .comeDown("query")
                    .comeDown("search")
                    .chooseFirst("snippet", value -> ((String) value).contains(keyWord))
                    .getValue("pageid");
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public String getContent(Long pageId) {
        var params = getContentQueryParameters(pageId.toString());
        try (var request = new Request(API_URL)) {
            var json = request.get(params);
            return (String) new JsonParser(json)
                    .comeDown("parse")
                    .getValue("text");
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private Map<String, String> getSearchParameters(String query) {
        return new HashMap<>() {
            {
                put("action", "query");
                put("list", "search");
                put("srsearch", query);
                put("format", "json");
                put("formatversion", "2");
            }
        };
    }

    private Map<String, String> getContentQueryParameters(String pageId) {
        return new HashMap<>() {
            {
                put("action", "parse");
                put("pageid", pageId);
                put("format", "json");
                put("formatversion", "2");
            }
        };
    }
}
