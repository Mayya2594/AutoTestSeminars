package org.max.seminar_3_HW;

import io.restassured.http.Method;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.max.seminar_3.accuweather.AccuweatherAbstractTest;

import static io.restassured.RestAssured.given;

public class GetWeatherFifteenDayTest extends AccuweatherAbstractTest {

    @ParameterizedTest
    @ValueSource(ints = {295212, 293141, 293687})
    void get_fifteen_day_return_401(int locationKey) {

        given()
                .queryParam("apikey", getApiKey())
                .pathParam("version", "v1")
                .pathParam("location", locationKey)
                .when()
                .request(Method.GET,getBaseUrl()+"/forecasts/{version}/daily/15day/{location}")
                .then()
                .statusCode(401);
    }
}
