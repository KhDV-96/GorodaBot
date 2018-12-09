package com.onedayfirm.gorodabot.mediawiki;

import com.onedayfirm.gorodabot.json.JsonParser;
import com.onedayfirm.gorodabot.json.ParseException;
import com.onedayfirm.gorodabot.network.Request;
import com.onedayfirm.gorodabot.network.RequestException;
import com.onedayfirm.gorodabot.utils.Configurations;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.regex.Pattern;

public class MediaWiki {

    private static final String API_URL = Configurations.getProperty("mediaWiki.apiURL");
    private static final String TEMPLATE = Configurations.getProperty("mediaWiki.searchTemplate");
    private static final Pattern SHORT_INFO_PATTERN =
            Pattern.compile(Configurations.getProperty("mediaWiki.shortInfoPattern"), Pattern.DOTALL);
    private static final Pattern PLANE_TEXT_PATTERN =
            Pattern.compile(Configurations.getProperty("mediaWiki.planeTextPattern"), Pattern.DOTALL);

    private Map<String, String> cachedQueries = new WeakHashMap<>();

    public String search(String query, String keyWord) {
        if (cachedQueries.containsKey(query)) {
            return cachedQueries.get(query);
        }
        try (var request = new Request(API_URL)) {
            var searchParams = getSearchParameters(String.format(TEMPLATE, query, keyWord));
            var response = request.get(searchParams);
            var pageId = extractPageId(response, query.toLowerCase());
            var json = request.get(getContentQueryParameters(pageId.toString()));
            var content = extractContent(json, pageId);
            cachedQueries.put(query, content);
            return content;
        } catch (NullPointerException | RequestException | ParseException exception) {
            return null;
        }
    }

    private static Map<String, String> getSearchParameters(String query) {
        return Map.of(
                "action", "query",
                "list", "search",
                "srsearch", query,
                "format", "json",
                "formatversion", "2"
        );
    }

    private static Long extractPageId(String json, String query) throws ParseException {
        return (Long) new JsonParser(json)
                .comeDown("query")
                .comeDown("search")
                .choose(entry -> ((String) entry.get("title")).toLowerCase().contains(query))
                .getValue("pageid");
    }

    private static Map<String, String> getContentQueryParameters(String pageId) {
        return Map.of(
                "action", "query",
                "prop", "extracts",
                "explaintext", "",
                "exintro", "",
                "pageids", pageId,
                "format", "json",
                "formatversion", "2"
        );
    }

    private static String extractContent(String json, Long pageId) throws ParseException {
        var htmlContent = (String) new JsonParser(json)
                .comeDown("query")
                .comeDown("pages")
                .choose(entry -> entry.get("pageid").equals(pageId))
                .getValue("extract");
        var matcher = SHORT_INFO_PATTERN.matcher(htmlContent);
        if (matcher.find())
            return PLANE_TEXT_PATTERN.matcher(matcher.group(1)).replaceAll("");
        else
            return null;
    }
}