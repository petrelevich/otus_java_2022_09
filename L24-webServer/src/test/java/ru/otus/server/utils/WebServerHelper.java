package ru.otus.server.utils;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class WebServerHelper {

    private static final String COOKIE_NAME_JSESSIONID = "JSESSIONID";
    public static final String COOKIE_HEADER = "Cookie";


    private WebServerHelper() {
    }

    public static String buildUrl(String host, String path, String ...pathParams) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(host);
        stringBuilder.append(path);
        Optional.ofNullable(pathParams).ifPresent(paramsMap -> Arrays.stream(paramsMap).forEach(p -> stringBuilder.append("/").append(p)));
        return stringBuilder.toString();
    }

    public static HttpCookie login(String url, String login, String password) throws Exception {
        HttpClient http = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL)).build();

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody())
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .uri(URI.create(String.format("%s?login=%s&password=%s", url, login, password)))
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            List<HttpCookie> cookies = http.cookieHandler()
                    .map(h -> (CookieManager)h)
                    .map(m -> m.getCookieStore().getCookies()).orElseGet(ArrayList::new);
            return cookies.stream().filter(c -> c.getName().equalsIgnoreCase(COOKIE_NAME_JSESSIONID)).findFirst().orElse(null);
        }
        return null;
    }

}
