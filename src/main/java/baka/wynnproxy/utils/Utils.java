package baka.wynnproxy.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;

public class Utils {
    public static JsonObject httpGet(String url) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            return (JsonObject) JsonParser.parseString(client.send(HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build(), HttpResponse.BodyHandlers.ofString()).body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String sha256(String raw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
