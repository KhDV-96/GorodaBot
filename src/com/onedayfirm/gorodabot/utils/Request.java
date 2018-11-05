package com.onedayfirm.gorodabot.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Request implements Closeable {

    private static final int TIMEOUT = 5000;

    private String url;
    private HttpsURLConnection connection;

    public Request(String url) {
        this.url = url;
    }

    public String get(Map<String, String> params) throws IOException {
        return get(buildParameters(params));
    }

    public String get(String params) throws IOException {
        openConnection();
        write(params);
        return read();
    }

    public void close() {
        connection.disconnect();
    }

    private String buildParameters(Map<String, String> params) {
        var builder = new StringBuilder();
        var first = true;
        for (var pair : params.entrySet()) {
            if (first)
                first = false;
            else
                builder.append('&');
            builder.append(URLEncoder.encode(pair.getKey(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(pair.getValue(), StandardCharsets.UTF_8));
        }
        return builder.toString();
    }

    private void openConnection() throws IOException {
        var url = new URL(this.url);
        connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(TIMEOUT);
        connection.setReadTimeout(TIMEOUT);
    }

    private void write(String data) throws IOException {
        connection.setDoOutput(true);
        try (var out = new DataOutputStream(connection.getOutputStream())) {
            out.writeBytes(data);
            out.flush();
        }
    }

    private String read() throws IOException {
        var in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        var builder = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            builder.append(inputLine);
        return builder.toString();
    }
}
