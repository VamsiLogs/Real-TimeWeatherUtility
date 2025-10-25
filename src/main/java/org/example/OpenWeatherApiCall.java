package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenWeatherApiCall implements IWeatherUtil{
    WeatherData weatherData = new WeatherData();

    @Override
    public void getCurrentWeather(double lat, double lon) {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("BASE_URL")+ "?lat=" + lat + "&lon=" + lon + "&appid=" + dotenv.get("OPEN_WEATHER_API_KEY");
        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            parseWithJacksonTree(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getWeatherDataForTimestamp(double lat, double lon, long unixTimestamp) {
        System.out.println("Fetching weather data for timestamp is not supported in the free tier of OpenWeather API.");
    }

    @Override
    public void getWeatherOverview(double lat, double lon) {
        if (weatherData.getTemp() == 0 && weatherData.getDescription() == null) {
            getCurrentWeather(lat, lon);
        }
        System.out.println("Weather Overview");
        System.out.println("Temperature: " + String.format("%.2f", weatherData.getTemp()) + " °C");
        System.out.println("Description: " + weatherData.getDescription());
    }

    private void parseWithJacksonTree(String responseBody) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseBody);
        weatherData.setTemp(root.path("main").path("temp").asDouble());
        weatherData.setDescription(root.path("weather").get(0).path("description").asText());
    }

}
