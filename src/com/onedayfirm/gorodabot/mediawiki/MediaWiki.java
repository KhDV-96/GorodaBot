package com.onedayfirm.gorodabot.mediawiki;

import com.onedayfirm.gorodabot.json.JsonParser;
import com.onedayfirm.gorodabot.json.ParseException;
import com.onedayfirm.gorodabot.network.Request;
import com.onedayfirm.gorodabot.network.RequestException;

import java.util.Map;
import java.util.regex.Pattern;

public class MediaWiki {

    private static final String API_URL = "https://ru.wikipedia.org/w/api.php";
    private static final String SEARCH_TEMPLATE_1 = "%1$s %2$s|%2$s";
    private static final String SEARCH_TEMPLATE_2 = "%s|%s";
    private static final Pattern SHORT_INFO_PATTERN = Pattern.compile("<p>(.+?)\\s</p>\\n\\n", Pattern.DOTALL);
    private static final Pattern PLANE_TEXT_PATTERN = Pattern.compile("<[^>]*>(\\s*<[^>]*>)*", Pattern.DOTALL);

    public String search(String query, String keyWord) {
        var searchQuery1 = String.format(SEARCH_TEMPLATE_1, query, keyWord);
        var searchQuery2 = String.format(SEARCH_TEMPLATE_2, query, keyWord);
        try (var request = new Request(API_URL)) {
            var first = request.get(getSearchParameters(searchQuery1));
            var second = request.get(getSearchParameters(searchQuery2));
            var pageId = chooseRelevantPage(first, second, query);

            var json = request.get(getContentQueryParameters(pageId.toString()));
            return extractContent(json, pageId);
        } catch (NullPointerException | RequestException | ParseException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();
            return null;
        }
    }

    private Map<String, String> getSearchParameters(String query) {
        return Map.of(
                "action", "query",
                "list", "search",
                "srsearch", query,
                "format", "json",
                "formatversion", "2"
        );
    }

    private Long chooseRelevantPage(String first, String second, String query) throws ParseException {
        var parser1 = parseSearchResponse(first, query);
        var parser2 = parseSearchResponse(second, query);
        if ((Long) parser1.getValue("wordcount") >= (Long) parser2.getValue("wordcount"))
            return (Long) parser1.getValue("pageid");
        else
            return (Long) parser2.getValue("pageid");
    }

    private JsonParser parseSearchResponse(String json, String query) throws ParseException {
        return new JsonParser(json)
                .comeDown("query")
                .comeDown("search")
                .choose(entry -> ((String) entry.get("title")).toLowerCase().contains(query));
    }

    private Map<String, String> getContentQueryParameters(String pageId) {
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

    private String extractContent(String json, Long pageId) throws ParseException {
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
