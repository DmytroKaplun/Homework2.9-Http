package org.example.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpStatusImageDownloader {
    private static final Logger logger = Logger.getLogger(HttpStatusImageDownloader.class.getName());
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    public static void downloadStatusImage(int code) {
        String imageLink = HttpStatusChecker.getStatusImage(code);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(imageLink))
                .GET().build();
        HttpResponse<byte[]> response = null;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response != null) {
                Path imagePath = Paths.get("images", code + ".jpg");
                Files.createDirectories(imagePath.getParent());
                Files.write(imagePath, response.body());
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            HttpResponse<byte[]> finalResponse = response;
            logger.log(Level.SEVERE, e,
                    () -> "Failed to download image, HTTP status code: " +
                            (finalResponse != null ? finalResponse.statusCode() : "unknown"));
        }
    }
    private  HttpStatusImageDownloader() {}
}
