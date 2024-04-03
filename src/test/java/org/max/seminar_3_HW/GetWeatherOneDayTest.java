package org.max.seminar_3_HW;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.max.seminar_3.accuweather.AccuweatherAbstractTest;
import org.max.seminar_3.accuweather.weather.DailyForecast;
import org.max.seminar_3.accuweather.weather.Weather;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetWeatherOneDayTest extends AccuweatherAbstractTest {

    @Test
    void getWeatherOneDay_shouldReturn() {

        Weather response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/1day/295212")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .response()
                .body().as(Weather.class);

        Assertions.assertEquals(1,response.getDailyForecasts().size());
        Assertions.assertEquals("F", response.getDailyForecasts().get(0)
                .getTemperature().getMaximum().getUnit());
        Assertions.assertEquals("F", response.getDailyForecasts().get(0)
                .getTemperature().getMinimum().getUnit());
        Assertions.assertEquals(18, response.getDailyForecasts()
                .get(0).getTemperature().getMaximum().getUnitType());
        Assertions.assertEquals(18, response.getDailyForecasts()
                .get(0).getTemperature().getMinimum().getUnitType());
        Assertions.assertEquals("AccuWeather", response.getDailyForecasts().get(0).getSources().get(0));
        Assertions.assertNotNull(response.getHeadline());
        Assertions.assertNotNull(response.getDailyForecasts());
    }

    @Test
    void gedForecastList() {

        List<DailyForecast> dailyForecasts = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/1day/295212")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .response()
                .body()
                .jsonPath().getList("DailyForecasts", DailyForecast.class);

        Assertions.assertEquals(1, dailyForecasts.size());
        Assertions.assertEquals("F", dailyForecasts.get(0).getTemperature().getMaximum().getUnit());
        Assertions.assertEquals("F", dailyForecasts.get(0).getTemperature().getMinimum().getUnit());
        Assertions.assertEquals("AccuWeather", dailyForecasts.get(0).getSources().get(0));
    }

    @Test
    void getString() {
        String response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/1day/295212")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .response()
                .body().asString();

        Assertions.assertTrue(response.contains("Headline"));
        Assertions.assertTrue(response.contains("DailyForecasts"));
        Assertions.assertTrue(response.contains("Date"));
        Assertions.assertTrue(response.contains("EpochDate"));
        Assertions.assertTrue(response.contains("Temperature"));
        Assertions.assertTrue(response.contains("Day"));
        Assertions.assertTrue(response.contains("Night"));
        Assertions.assertTrue(response.contains("Sources"));
        Assertions.assertTrue(response.contains("MobileLink"));
        Assertions.assertTrue(response.contains("Link"));
    }
}
