package com.onedayfirm.gorodabot.goroda;

import com.onedayfirm.gorodabot.json.JsonParser;
import com.onedayfirm.gorodabot.json.ParseException;
import com.onedayfirm.gorodabot.network.Request;
import com.onedayfirm.gorodabot.network.RequestException;
import com.onedayfirm.gorodabot.search.SearchService;
import com.onedayfirm.gorodabot.utils.Configurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class KladrRequester implements SearchService<Character, List<City>> {

    private static final String API_URL_TEMPLATE = Configurations.getProperty("kladrRequester.apiURLTemplate");
    private static final String USER_AGENT = Configurations.getProperty("kladrRequester.userAgent");
    private static final String TYPE = Configurations.getProperty("kladrRequester.type");
    private static final Logger LOGGER = LoggerFactory.getLogger(KladrRequester.class);

    @Override
    public List<City> search(Character query) {
        var letter = query.toString();
        LOGGER.debug("Requesting cities on letter '{}' from kladr", letter);
        try {
            var json = makeGetRequest(letter);
            return getNames(json, letter);
        } catch (NullPointerException | RequestException | ParseException exception) {
            LOGGER.error("Error of getting cities", exception);
            return null;
        }
    }

    private static String makeGetRequest(String letter) throws RequestException {
        var url = String.format(API_URL_TEMPLATE, letter);
        try (var request = new Request(url, USER_AGENT)) {
            return request.get();
        }
    }

    private static List<City> getNames(String json, String letter) throws ParseException {
        var names = new ArrayList<City>();
        new JsonParser(json)
                .comeDown("result")
                .forEach(entry -> {
                    var name = (String) entry.get("name");
                    var type = (String) entry.get("type");
                    if (isCorrectCity(letter, name, type)) {
                        names.add(new City(name));
                    }
                });
        return names.size() > 0 ? names : null;
    }

    private static boolean isCorrectCity(String letter, String name, String type) {
        name = name.toLowerCase();
        return TYPE.equals(type) && name.startsWith(letter) &&
                Character.isLetter(name.charAt(name.length() - 1));
    }
}
