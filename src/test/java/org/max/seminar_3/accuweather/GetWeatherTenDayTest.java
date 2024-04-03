package org.max.seminar_3.accuweather;

import io.restassured.http.Method;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

public class GetWeatherTenDayTest extends AccuweatherAbstractTest{

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, -3, 15})
    void get_ten_day_return_401(int locationKey) {

        given()
                .queryParam("apikey", getApiKey())
                .pathParam("version", "v1")
                .pathParam("location", locationKey)
                .when()
                .request(Method.GET,getBaseUrl()+"/forecasts/{version}/daily/10day/{location}")
                .then()
                .statusCode(401);
    }
}
