package org.example;

public class Main {
    static void main() {
        OpenWeatherApiCall apiCall = new OpenWeatherApiCall();
        double lat = 37.7749;
        double lon = -122.4194;
        apiCall.getWeatherOverview(lat, lon);
    }
}
