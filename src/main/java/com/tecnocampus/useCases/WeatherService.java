package com.tecnocampus.useCases;

import com.tecnocampus.Forecast.WeatherForecastResponse;
import org.json.JSONObject;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by santi on 22/11/16.
 */
@Service
public class WeatherService {

    private final Pattern pattern = Pattern.compile("^*[^0-9/]*$");

    public WeatherForecastResponse getWeather(String city) throws IOException, ParseException {
        if (!validate(city)) {
            throw new ParseException(0, city);
        }
        return getWeatherFromJson(getJsonFromServer(city));
    }

    private Boolean validate(String city) {
        Matcher matcher = pattern.matcher(city);
        return matcher.matches();
    }

    private WeatherForecastResponse getWeatherFromJson(String json) throws ParseException {
        WeatherForecastResponse weatherForecastResponse = new WeatherForecastResponse(new JSONObject(json));
        return weatherForecastResponse;
    }

    private String getJsonFromServer(String city) throws IOException {

        String result = "";

        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q="
                + city
                + "&units=metric"
                + "&lang=es"
                + "&APPID=18941878d8bee31166d6201ef9886fb2");
        URLConnection urlConnection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            result += result.concat(inputLine);
        }
        in.close();
        return result;
    }

}
