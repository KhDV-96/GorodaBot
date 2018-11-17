package com.onedayfirm.gorodabot.goroda.network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Request implements Closeable {

    private static final int TIMEOUT = 5000;

    private String url;
    private String userAgent;
    private HttpURLConnection connection;

    public Request(String url, String userAgent) {
        this(url);
        this.userAgent = userAgent;
    }

    public Request(String url) {
        this.url = url;
    }

    public String get(Map<String, String> params) throws RequestException {
        return get(buildParameters(params));
    }

    public String get(String params) throws RequestException {
        openConnection();
        write(params);
        return read();
    }

    public String get() throws RequestException {
        openConnection();
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
            if (!pair.getValue().isEmpty()) {
                builder.append("=");
                builder.append(URLEncoder.encode(pair.getValue(), StandardCharsets.UTF_8));
            }
        }
        return builder.toString();
    }

    private void openConnection() throws RequestException {
        try {
            var url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            if (userAgent != null)
                connection.setRequestProperty("User-Agent", userAgent);
        } catch (IOException exception) {
            throw new RequestException(exception);
        }
    }

    private void write(String data) throws RequestException {
        connection.setDoOutput(true);
        try (var out = new DataOutputStream(connection.getOutputStream())) {
            out.writeBytes(data);
            out.flush();
        } catch (IOException exception) {
            throw new RequestException(exception);
        }
    }

    private String read() throws RequestException {
        try {
            var in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            var builder = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                builder.append(inputLine);
            return builder.toString();
        } catch (IOException exception) {
            throw new RequestException(exception);
        }
    }
}
