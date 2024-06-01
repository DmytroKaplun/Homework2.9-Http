package org.example.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpStatusChecker {
    private static final Logger logger = Logger.getLogger(HttpStatusChecker.class.getName());
    private static final String BASE_URL = "https://http.cat/";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static String getStatusImage(int code) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET().build();
        try {
            HttpResponse<Void> response = CLIENT.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() == 404) {
                throw new IOException("Image not found for status code: " + code);
            }
            if (response.statusCode() >= 300) {
                throw new IOException("Unexpected response code: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE, e, () -> "Error occurred while fetching image for status code: " + code);
        }
        return String.format("https://http.cat/%s.jpg", code);
    }

    public static boolean isStatusValid(int code) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET().build();
        try {
            HttpResponse<Void> response = CLIENT.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() >= 300) {
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    private HttpStatusChecker() {}

}
