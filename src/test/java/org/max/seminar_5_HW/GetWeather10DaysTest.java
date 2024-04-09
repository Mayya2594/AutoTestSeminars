package org.max.seminar_5_HW;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetWeather10DaysTest extends AbstractTest {

    @Test
    void get_shouldReturn401() throws IOException, URISyntaxException {
        //given
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/295212"))
                .withQueryParam("apiKey", notMatching("82c9229354f849e78efe010d94150807"))
                .willReturn(aResponse()
                        .withStatus(401).withBody("Unauthorized")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/forecasts/v1/daily/10day/295212");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("apiKey", "A_82c9229354f849e78efe010d94150807")
                .build();
        request.setURI(uri);
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/")));
        assertEquals(401, response.getStatusLine().getStatusCode());
        assertEquals("Unauthorized", convertResponseToString(response));

    }

    @Test
    void get_shouldReturn200() throws URISyntaxException, IOException {
        //given
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/295212"))
                .withQueryParam("apiKey", equalTo("82c9229354f849e78efe010d94150807"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Обычно мы не предоставляем прогноз погоды на 10 дней из-за недостатка прав, но сегодня акция")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/forecasts/v1/daily/10day/295212");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("apiKey", "82c9229354f849e78efe010d94150807")
                .build();
        request.setURI(uri);
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/295212")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("Обычно мы не предоставляем прогноз погоды на 10 дней из-за недостатка прав, но сегодня акция",
                convertResponseToString(response));
    }
}
