package org.max.seminar_3_HW;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.max.seminar_3.accuweather.location.Location;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetLocationTest extends AccuweatherAbstractTest {

    @Test
    void getLocation_autocomplete_returnSaintPetersburg() {

        List<Location> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/locations/v1/cities/autocomplete?q=Saint Petersburg")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".", Location.class);

        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals("Saint Petersburg", response.get(0).getLocalizedName());
        Assertions.assertEquals("Saint Petersburg", response.get(0).getAdministrativeArea().getLocalizedName());
    }

    @Test
    void getLocation_autocomplete_returnPetrozavodsk() {

        List<Location> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/locations/v1/cities/autocomplete?q=Petrozavodsk")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".", Location.class);

        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals("Petrozavodsk", response.get(0).getLocalizedName());
        Assertions.assertEquals("Kareliya", response.get(0).getAdministrativeArea().getLocalizedName());
    }
}
