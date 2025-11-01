package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    static void main() {
        // POJO Class for temp
        WeatherResponse weatherResponse = new WeatherResponse();

        // dotenv to manage configs
        Dotenv dotenv = Dotenv.load();

        // lat and lon as input instead of city name
        double lat = 37.1289771, lon = -84.0832646;
        String url = dotenv.get("BASE_URL")+ "?lat=" + lat + "&lon=" + lon + "&appid="
                + dotenv.get("OPEN_WEATHER_API_KEY") + "&units=metric";

        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Jackson Tree Model instead of using POJO for full json structure
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            weatherResponse.setTemp(root.path("main").path("temp").asDouble());

            System.out.println("Weather Report");
            System.out.println("Current Temperature: " + String.format("%.2f", weatherResponse.getTemp()) + " °C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
