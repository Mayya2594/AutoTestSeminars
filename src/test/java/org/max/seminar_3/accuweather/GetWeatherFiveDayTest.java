package org.max.seminar_3.accuweather;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.max.seminar_3.accuweather.weather.DailyForecast;
import org.max.seminar_3.accuweather.weather.Weather;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetWeatherFiveDayTest extends AccuweatherAbstractTest {

    @Test
    void getWeatherFiveDay_shouldReturn() {

        Weather response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/5day/290396")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .response()
                .body().as(Weather.class);

        Assertions.assertEquals(5,response.getDailyForecasts().size());
        Assertions.assertEquals("F", response.getDailyForecasts().get(0).getTemperature().getMaximum().getUnit());
        Assertions.assertNotNull(response.getHeadline());
    }

    @Test
    void gedForecastList() {

        List<DailyForecast> dailyForecasts = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/5day/290396")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .response()
                .body()
                .jsonPath().getList("DailyForecasts", DailyForecast.class);

        Assertions.assertEquals(5, dailyForecasts.size());
        Assertions.assertEquals("F", dailyForecasts.get(0).getTemperature().getMaximum().getUnit());
    }

    @Test
    void getString() {
        String response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/5day/290396")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .response()
                .body().asString();

        Assertions.assertTrue(response.contains("DailyForecasts"));
        Assertions.assertTrue(response.contains("Headline"));
    }
}
