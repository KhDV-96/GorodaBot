package com.onedayfirm.gorodabot.goroda;

import com.onedayfirm.gorodabot.json.JsonParser;
import com.onedayfirm.gorodabot.json.ParseException;
import com.onedayfirm.gorodabot.network.Request;
import com.onedayfirm.gorodabot.network.RequestException;
import com.onedayfirm.gorodabot.utils.Configurations;

import java.util.ArrayList;
import java.util.List;

class KladrRequester {

    private static final String API_URL_TEMPLATE = Configurations.getProperty("kladrRequester.apiURLTemplate");
    private static final String USER_AGENT = Configurations.getProperty("kladrRequester.userAgent");

    String makeGetRequest(char letter) {
        var url = String.format(API_URL_TEMPLATE, letter);
        try (var request = new Request(url, USER_AGENT)) {
            return request.get();
        } catch (RequestException exception) {
            return null;
        }
    }

    List<City> getNames(String json) {
        var names = new ArrayList<City>();
        try {
            new JsonParser(json)
                    .comeDown("result")
                    .forEach(entry -> {
                        var name = (String) entry.get("name");
                        var type = (String) entry.get("type");
                        if (type.equals("Город")) {
                            names.add(new City(name));
                        }
                    });
            return names;
        } catch (ParseException e) {
            return new ArrayList<>();
        }
    }
}
