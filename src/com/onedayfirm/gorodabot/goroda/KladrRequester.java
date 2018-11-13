package com.onedayfirm.gorodabot.goroda;

import com.onedayfirm.gorodabot.json.JsonParser;
import com.onedayfirm.gorodabot.json.ParseException;
import com.onedayfirm.gorodabot.network.Request;
import com.onedayfirm.gorodabot.network.RequestException;

import java.util.ArrayList;
import java.util.List;


public class KladrRequester {

    private final String USER_AGENT = "Mozilla/5.0";

    String makeGetRequest(char letter) {

        var url = "http://kladr-api.ru/api.php?query=" + letter + "&contentType=city&withParent=0&typeCode=1";

        try (var request = new Request(url, USER_AGENT)) {
            return request.get("");
        } catch (NullPointerException | RequestException exception) {
            return null;
        }
    }

    List<City> getNames(String json) {
        var names = new ArrayList();
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
        } catch (ParseException e) {
            return names;
        }
        return names;
    }
}
