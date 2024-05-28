package com.project.telegrambot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.project.telegrambot.service.TelegramBotService.ERROR_TEXT;


//import org.json.JSONObject;

@Service
@Slf4j
public class WeatherService {

    //final WeatherMain weatherMain = new WeatherMain();

    private static String LOCATION_URL = "http://dataservice.accuweather.com/locations/v1/cities/autocomplete";// weatherMain.getACCU_WEATHER_LOCATION_URL();
    private static String API_KEY = "Rjr1HRBdhAMmGhoPPD1V36xrmx30Cpjw"; ;//weatherMain.getACCU_WEATHER_API_KEY();
    private static String WEATHER_URL_NOW = "http://dataservice.accuweather.com/currentconditions/v1/{locationKey}" ;//weatherMain.getACCU_WEATHER_URL_NOW();

    private static String WEATHER_URL_DAILY = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/{locationKey}";//weatherMain.getACCU_WEATHER_URL_DAILY();

    private static String WEATHER_PASS = "yQtMv^]V,2:SL),!";

    private static String EMAIL = "kenderrisha1@gmail.com";

    private static final boolean offlineTestMode = true;




    public static JsonNode locationRequest(String cityName) {

        try {

            HttpResponse<JsonNode> response = Unirest.get(LOCATION_URL) //
                    .basicAuth(EMAIL, WEATHER_PASS)
                    .queryString("apikey", API_KEY)
                    .queryString("q", cityName)
                    .asJson();
//            HttpResponse<String> responseString = Unirest.get(LOCATION_URL)
//                    .basicAuth(EMAIL, WEATHER_PASS)
//                    .queryString("apikey", API_KEY)
//                    .queryString("q", cityName)
//                    .asString();
//            String string = responseString.getBody();
            String responseStatus = response.getStatusText();



//        HttpResponse<JsonNode> response = Unirest.get("http://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey=Rjr1HRBdhAMmGhoPPD1V36xrmx30Cpjw&q=Velten").asJson();
            String stringStatus = response.getStatusText();
            System.out.println(response);
            //       HttpResponse<String> responseString = Unirest.get("http://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey=Rjr1HRBdhAMmGhoPPD1V36xrmx30Cpjw&q=Velten").asString();
            //       String string = responseString.getBody();
  //          System.out.println(string);
            return response.getBody();

        } catch (NullPointerException e) {
            System.out.println("Null pointer exception error " +e.getMessage());
            throw e;
        }

    }


    public static JsonNode locationRequest() throws JsonProcessingException {
        String jsonString = "[{\"Version\":1,\"Key\":\"167930\",\"Type\":\"City\",\"Rank\":63,\"LocalizedName\":\"Velten\",\"Country\":{\"ID\":\"DE\",\"LocalizedName\":\"Germany\"},\"AdministrativeArea\":{\"ID\":\"BB\",\"LocalizedName\":\"Brandenburg\"}},{\"Version\":1,\"Key\":\"988012\",\"Type\":\"City\",\"Rank\":85,\"LocalizedName\":\"Veltenhof\",\"Country\":{\"ID\":\"DE\",\"LocalizedName\":\"Germany\"},\"AdministrativeArea\":{\"ID\":\"NI\",\"LocalizedName\":\"Lower Saxony\"}}]";

        JsonNode jsonNode = new JsonNode(jsonString); //Converting to JSONObject since it supports more functionalities
        return jsonNode;
    }



    static JSONArray getJSONArray(JsonNode jsonNode) throws Exception {


        return jsonNode.getArray();
    }



    static String getLocationKeyString(String cityName) throws Exception {
        if(offlineTestMode == true) {
            JSONObject location = getJSONArray(locationRequest()).getJSONObject(0);
            return location.getString("Key");
        }
        else {
            JSONObject location = getJSONArray(locationRequest(cityName)).getJSONObject(0);
            return location.getString("Key");
        }

    }
    static String getLocalisedNameString(String cityName) throws Exception {
        if(offlineTestMode == true){
            JSONObject location = getJSONArray(locationRequest()).getJSONObject(0);
            return location.getString("LocalizedName");
        } else {
            JSONObject location = getJSONArray(locationRequest(cityName)).getJSONObject(0);
            return location.getString("LocalizedName");
        }
    }

    private static JsonNode getCurrentWeatherObject(String locationKey) throws Exception {

//        HttpResponse<JsonNode> response = Unirest.get(WEATHER_URL_NOW).asJson();
//        HttpResponse<String> responseString = Unirest.get(WEATHER_URL_NOW).asString();
        HttpResponse<JsonNode> response = Unirest.get(WEATHER_URL_NOW)
                .basicAuth(EMAIL, WEATHER_PASS)
                .routeParam("locationKey", locationKey)
                .queryString("apikey", API_KEY)
                .asJson();
//        HttpResponse<String> responseString = Unirest.get(WEATHER_URL_NOW)
//                .basicAuth("kenderrisha1@gmail.com", WEATHER_PASS)
//                .routeParam("locationKey", locationKey)
//                .queryString("apikey", API_KEY)
//                .asString();
 //       String string = responseString.getBody();
        return response.getBody();
    }

    public static JsonNode getCurrentWeatherObject() throws JsonProcessingException {
        String jsonString = "[{\"LocalObservationDateTime\":\"2024-05-13T13:12:00+02:00\",\"EpochTime\":1715598720,\"WeatherText\":\"Sunny\",\"WeatherIcon\":1,\"HasPrecipitation\":false,\"PrecipitationType\":null,\"IsDayTime\":true,\"Temperature\":{\"Metric\":{\"Value\":22.4,\"Unit\":\"C\",\"UnitType\":17},\"Imperial\":{\"Value\":72.0,\"Unit\":\"F\",\"UnitType\":18}},\"MobileLink\":\"http://www.accuweather.com/en/de/velten/16727/current-weather/167930?lang=en-us\",\"Link\":\"http://www.accuweather.com/en/de/velten/16727/current-weather/167930?lang=en-us\"}]";

        JsonNode jsonNode = new JsonNode(jsonString); //Converting to JSONObject since it supports more functionalities
        return jsonNode;
    }


    static JSONArray getCurrentWeatherObjectList(JsonNode jsonNode) throws Exception {
        return jsonNode.getArray();
    }



    static String getWeatherTextString(String locationKey) throws Exception {
        JSONObject currentWeather;
        if (offlineTestMode == true){
            currentWeather = getCurrentWeatherObjectList(getCurrentWeatherObject()).getJSONObject(0);
        } else {
            currentWeather = getCurrentWeatherObjectList(getCurrentWeatherObject(locationKey)).getJSONObject(0);
        }

        String weatherText;
        try{
            weatherText = currentWeather.getString("WeatherText");
        }
        catch (Exception e){
            weatherText = "Not available";
        }


        return weatherText;
    }
    static String getIsDayTime(String locationKey) throws Exception {
        JSONObject currentWeather;
        if (offlineTestMode == true){
            currentWeather = getCurrentWeatherObjectList(getCurrentWeatherObject()).getJSONObject(0);
        }else {
            currentWeather = getCurrentWeatherObjectList(getCurrentWeatherObject(locationKey)).getJSONObject(0);
        }

        String dayTime;
        //
        try {
            boolean isDayTime = currentWeather.getBoolean("IsDayTime");

            if (isDayTime) {
                dayTime = "Day";
            } else {
                dayTime = "Night";
            }
        }
        catch (Exception e) {
            dayTime = "Not available";
        }

        return dayTime;
    }

    static String getLink(String locationKey) throws Exception {
        if (offlineTestMode == true){
            JSONObject currentWeather = getCurrentWeatherObjectList(getCurrentWeatherObject()).getJSONObject(0);
            return currentWeather.getString("Link");
        } else {
            JSONObject currentWeather = getCurrentWeatherObjectList(getCurrentWeatherObject(locationKey)).getJSONObject(0);
            return currentWeather.getString("Link");
        }
    }

    static String getCurrentWeatherTemperature(String locationKey) throws Exception {
        JSONObject currentWeather;
        if(offlineTestMode == true){
            currentWeather = getCurrentWeatherObjectList(getCurrentWeatherObject()).getJSONObject(0);
        } else {
            currentWeather = getCurrentWeatherObjectList(getCurrentWeatherObject(locationKey)).getJSONObject(0);
        }

        JSONObject currentTemperature = currentWeather.getJSONObject("Temperature").getJSONObject("Metric");
        double tempValue;
        String tempUnit;
        String stringCurrentTemperature;
        try{
            tempValue = currentTemperature.getDouble("Value");
            tempUnit = currentTemperature.getString("Unit");
            stringCurrentTemperature = "Temperature now: " + tempValue +tempUnit;
        }
        catch (Exception e){
            stringCurrentTemperature = "Not available";
        }


        return stringCurrentTemperature;
    }

//    static Map JsonMapping() throws JsonProcessingException {
//        HttpResponse<String> responseString = Unirest.get("http://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey=Rjr1HRBdhAMmGhoPPD1V36xrmx30Cpjw&q=Velten").asString();
//        String jsonString = responseString.getBody();
//        Map<String, Object> locationMap = objectMapper.readValue(jsonString, new TypeReference<>(){});
//        return locationMap;
//    }

    public static String sendCurrentWeatherText(String locationKey) throws Exception {


        return "Weather now: \n"
                + "Day time: " + getIsDayTime(locationKey) + "\n"
                + "Weather: " + getWeatherTextString(locationKey) +"\n"
                + getCurrentWeatherTemperature(locationKey) + "\n"
                + getLink(locationKey);
    }


    //прогноз погоды

    private static JsonNode getDailyWeatherRequest(String locationKey) throws Exception {

        HttpResponse<JsonNode> response = Unirest.get(WEATHER_URL_DAILY)
                .basicAuth(EMAIL, WEATHER_PASS)
                .routeParam("locationKey", locationKey)
                .queryString("apikey", API_KEY)
                .queryString("metric", true)
                .asJson();
//        HttpResponse<String> responseString = Unirest.get(WEATHER_URL_DAILY)
//                .basicAuth(EMAIL, WEATHER_PASS)
//                .routeParam("locationKey", locationKey)
//                .queryString("apikey", API_KEY)
//                .asString();
//        String string = responseString.getBody();
        return response.getBody();

    }

    private static JsonNode getDailyWeatherRequest() throws Exception {
        String jsonString = "{\"Headline\": {\"EffectiveDate\": \"2024-05-14T14:00:00+02:00\",\"EffectiveEpochDate\": 1715688000,\"Severity\": 7,\"Text\": \"Very warm from Tuesday afternoon to Thursday afternoon\",\"Category\": \"heat\",\"EndDate\": \"2024-05-16T20:00:00+02:00\",\"EndEpochDate\": 1715882400,\"MobileLink\": \"http://www.accuweather.com/en/de/velten/16727/daily-weather-forecast/167930?unit=c&lang=en-us\",\"Link\": \"http://www.accuweather.com/en/de/velten/16727/daily-weather-forecast/167930?unit=c&lang=en-us\"},\"DailyForecasts\": [{\"Date\": \"2024-05-14T07:00:00+02:00\",\"EpochDate\": 1715662800,\"Temperature\": {\"Minimum\": {\"Value\": 12.8,\"Unit\": \"C\",\"UnitType\": 17},\"Maximum\": {\"Value\": 25.8,\"Unit\": \"C\",\"UnitType\": 17}},\"Day\": {\"Icon\": 1,\"IconPhrase\": \"Sunny\",\"HasPrecipitation\": false},\"Night\": {\"Icon\": 33,\"IconPhrase\": \"Clear\",\"HasPrecipitation\": false},\"Sources\": [\"AccuWeather\"],\"MobileLink\": \"http://www.accuweather.com/en/de/velten/16727/daily-weather-forecast/167930?day=1&unit=c&lang=en-us\",\"Link\": \"http://www.accuweather.com/en/de/velten/16727/daily-weather-forecast/167930?day=1&unit=c&lang=en-us\"}]}";
        return new JsonNode(jsonString);
    }


    static JSONObject getDailyWeatherObject(JsonNode jsonNode) throws Exception {
        return jsonNode.getObject();
    }

    static JSONArray getDailyWeatherObjectList(JSONObject jsonObject, String arrayName) throws Exception {
        return jsonObject.getJSONArray(arrayName);
    }

    static String getDailyWeatherHeadlineString(String locationKey) throws Exception {
        JSONObject dailyWeatherHeadline;
        if (offlineTestMode == true){
            dailyWeatherHeadline = getDailyWeatherObject(getDailyWeatherRequest()).getJSONObject("Headline");
        } else {
            dailyWeatherHeadline = getDailyWeatherObject(getDailyWeatherRequest(locationKey)).getJSONObject("Headline");
        }
        String effectiveDate = dailyWeatherHeadline.getString("EffectiveDate");
        String weatherText = dailyWeatherHeadline.getString("Text");
        String weatherCategory = dailyWeatherHeadline.getString("Category");
        String endDate;
        try {
            endDate = dailyWeatherHeadline.getString("EndDate");
        }
        catch (Exception e) {
            endDate = "Data unavailable";
        }

        String link;
        try{


            link = dailyWeatherHeadline.getString("Link");
        }
        catch (Exception e){
            link = "Not available";
        }
        return "Date and time: from " + effectiveDate + " to " + endDate + ".  \n Weather: " + weatherCategory + ". " +
                weatherText + ". \n" + "Link: " + link;
    }


    static String getDailyWeatherForecastStringDate(String locationKey) throws Exception {
        JSONObject dailyWeatherForecast;
        if (offlineTestMode == true){
            dailyWeatherForecast = getDailyWeatherObjectList(getDailyWeatherObject(getDailyWeatherRequest()),"DailyForecasts").getJSONObject(0);
        } else {
            dailyWeatherForecast = getDailyWeatherObjectList(getDailyWeatherObject(getDailyWeatherRequest(locationKey)),"DailyForecasts").getJSONObject(0);
        }
        return dailyWeatherForecast.getString("Date");

    }




    static JSONObject getDailyWeatherForecastTemperature(String locationKey) throws Exception {
        if (offlineTestMode == true){
            return getDailyWeatherObjectList(getDailyWeatherObject(getDailyWeatherRequest()),"DailyForecasts").getJSONObject(0).getJSONObject("Temperature");
        } else {
            return getDailyWeatherObjectList(getDailyWeatherObject(getDailyWeatherRequest(locationKey)),"DailyForecasts").getJSONObject(0).getJSONObject("Temperature");
        }

    }

    static JSONObject getDailyWeatherTempMin(String locationKey) throws Exception {
        return getDailyWeatherForecastTemperature(locationKey).getJSONObject("Minimum");
    }

    static double getDailyWeatherTempMinValue(String locationKey) throws Exception {
        return getDailyWeatherTempMin(locationKey).getDouble("Value");
    }

    static String getDailyWeatherTempMinUnit(String locationKey) throws Exception {
        return getDailyWeatherTempMin(locationKey).getString("Unit");
    }

    static JSONObject getDailyWeatherTempMax(String locationKey) throws Exception {
        return getDailyWeatherForecastTemperature(locationKey).getJSONObject("Maximum");
    }


    static double getDailyWeatherTempMaxValue(String locationKey) throws Exception {
        return getDailyWeatherTempMax(locationKey).getDouble("Value");
    }

    static String getDailyWeatherTempMaxUnit(String locationKey) throws Exception {
        return getDailyWeatherTempMax(locationKey).getString("Unit");
    }

    static JSONObject getDailyWeatherDay(String locationKey) throws Exception {
        JSONObject dailyWeatherForecast;
        if (offlineTestMode == true){
            dailyWeatherForecast = getDailyWeatherObjectList(getDailyWeatherObject(getDailyWeatherRequest()),"DailyForecasts").getJSONObject(0);
        } else {
            dailyWeatherForecast = getDailyWeatherObjectList(getDailyWeatherObject(getDailyWeatherRequest(locationKey)),"DailyForecasts").getJSONObject(0);
        }

        return dailyWeatherForecast.getJSONObject("Day");
    }


    static String getDailyWeatherDayPhrase(String locationKey) throws Exception {
        return getDailyWeatherDay(locationKey).getString("IconPhrase");
    }

    static JSONObject getDailyWeatherNight(String locationKey) throws Exception {
        JSONObject dailyWeatherForecast;
        if (offlineTestMode == true){
            dailyWeatherForecast = getDailyWeatherObjectList(getDailyWeatherObject(getDailyWeatherRequest()),"DailyForecasts").getJSONObject(0);
        } else {
            dailyWeatherForecast = getDailyWeatherObjectList(getDailyWeatherObject(getDailyWeatherRequest(locationKey)),"DailyForecasts").getJSONObject(0);
        }

        return dailyWeatherForecast.getJSONObject("Night");
    }


    static String getDailyWeatherNightPhrase(String locationKey) throws Exception {
        return getDailyWeatherNight(locationKey).getString("IconPhrase");
    }


    static String sendWeatherForecast(String locationKey) throws Exception {

        return "Weather forecast for " + getDailyWeatherForecastStringDate(locationKey) + " :\n"
                + "Minimal temperature: " + getDailyWeatherTempMinValue(locationKey) + getDailyWeatherTempMinUnit(locationKey) + "\n"
                + "Maximal temperature: " + getDailyWeatherTempMaxValue(locationKey) + getDailyWeatherTempMaxUnit(locationKey) + "\n"
                + "Day: " + getDailyWeatherDayPhrase(locationKey) + "\n"
                + "Night: " + getDailyWeatherNightPhrase(locationKey ) + "\n"
                + getDailyWeatherHeadlineString(locationKey);
    }




    /**
     * Send weather in city to chat


     */
   static String sendCurrentWeather(String cityName) {

       String weatherText = "Current weather data unavailable";
        try {

            String  locationKey = getLocationKeyString(cityName);
            weatherText = sendCurrentWeatherText(locationKey);

            System.out.println(weatherText);

           return weatherText;
        } catch(Exception e) {
            log.error(ERROR_TEXT + e.getMessage());

        }
       return weatherText;
   }




  static   String sendDailyWeather(String cityName) {

      String weatherText = "Weather forecast data unavailable";
        try {


              String  locationKey = getLocationKeyString(cityName);

              weatherText = sendWeatherForecast(locationKey);

            System.out.println(weatherText);
                return weatherText;

        } catch(Exception e) {
            log.error(ERROR_TEXT + e.getMessage());

        }
      return weatherText;
  }





}


