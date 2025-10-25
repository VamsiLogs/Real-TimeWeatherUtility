package org.example;

public class WeatherData {
    private double temp;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        if (temp < -273.15) {
            throw new IllegalArgumentException("Temperature cannot be below absolute zero.");
        }

        if (Double.isNaN(temp)) {
            throw new IllegalArgumentException("Temperature cannot be NaN.");
        }

        this.temp = temp-273.15; // Convert from Kelvin to Celsius
    }


}
