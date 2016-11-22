package com.tecnocampus.webControllers;

import com.tecnocampus.useCases.WeatherService;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

/**
 * Created by santi on 22/11/16.
 */

@Controller
public class ForecastController {

    private final WeatherService weatherService = new WeatherService();

    @GetMapping("/forecast/{city}")
    public String weather(@PathVariable(value = "city") String city, Model model) throws IOException, ParseException {
        model.addAttribute("weather", weatherService.getWeather(city));
        return "forecast";
    }

}
