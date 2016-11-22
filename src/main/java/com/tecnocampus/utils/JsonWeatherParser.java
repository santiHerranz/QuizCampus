package com.tecnocampus.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.domain.Weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JsonWeatherParser {

    private String jsonToParsing;

    public void setJsonToParsing(String jsonToParsing) {
        this.jsonToParsing = jsonToParsing;
    }

    public Weather getWeather() {

        Weather weather = new Weather();

        weather = getWeatherForeCast().get(0);

/*
        JSONObject mainArray = (JSONObject) mainJsonObj.get("main");
        JSONObject windArray = (JSONObject) mainJsonObj.get("wind");
        JSONObject countryObject = (JSONObject) mainJsonObj.get("sys");

        JSONArray weatherArray = (JSONArray) mainJsonObj.get("weather");
        JSONObject descriptionObject = (JSONObject) weatherArray.get(0);

        weather.setCity(getCityDescription(mainJsonObj, countryObject));
        weather.setDescription(String.valueOf(
                descriptionObject.get("description")));
        weather.setTemperature(getTemperatureDescription(mainArray));
        weather.setWind(getWindDescription(windArray));
*/

        return weather;
    }

    private List<Weather> getWeatherForeCast() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(jsonToParsing);
            Iterator<JsonNode> nodes = root.path("nodes").elements();

            //convert Iterator to Stream
            Iterable<JsonNode> iterable = () -> nodes;
            Stream<JsonNode> nodesStream = StreamSupport.stream(iterable.spliterator(), false);

            //get stations
            return nodesStream
                    .filter(n -> n.path("city").asBoolean())
                    .map(n -> jsonToStation(mapper, n))
                    .collect(Collectors.toList());


        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Weather>();
        }
    }

    private Weather jsonToStation(ObjectMapper mapper, JsonNode node) {
        try {
            System.out.println(node.path("nom"));
            return mapper.treeToValue(node, Weather.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Weather();
        }
    }

/*
    private String getCityDescription(JSONObject jsonObject,
                                      JSONObject countryObject) {
        return String.valueOf(jsonObject.get("name"))
                .concat(", ")
                .concat(String.valueOf(countryObject.get("country")));
    }

    private String getTemperatureDescription(JSONObject jsonObject) {
        return String.valueOf(new KelvinToCelsiusConverter(
                (Double) (jsonObject.get("temp"))).getCelsiusString());
    }

    private String getWindDescription(JSONObject jsonObject) {
        String degreesWindString = String.valueOf(jsonObject.get("deg"));
        return DegreesToDirectionConverter.convert(degreesWindString)
                .concat(", ")
                .concat(String.valueOf(jsonObject.get("speed")))
                .concat(" m/s");
    }
    */
}
