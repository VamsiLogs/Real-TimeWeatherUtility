package org.example;

public interface IWeatherUtil {
    /*
    This method fetches current weather
     */
    void getCurrentWeather(double lat, double lon);

    /*
    This method fetches weather data for any timestamp
     from 1st January 1979 till 4 days ahead forecast
     */
    void getWeatherDataForTimestamp(double lat, double lon, long unixTimestamp);

    /*
    This method fetches a human-readable weather summary
     for today and tomorrow's forecast.
     */
    void getWeatherOverview(double lat, double lon);



}
