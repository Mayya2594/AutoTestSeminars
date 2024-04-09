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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetLocationKey extends AbstractTest {

    @Test
    void get_shouldReturn401() throws IOException, URISyntaxException {
        //given
        stubFor(get(urlPathEqualTo("/locations/v1/295212"))
                .withQueryParam("apiKey", notMatching("82c9229354f849e78efe010d94150807"))
                .willReturn(aResponse()
                        .withStatus(401).withBody("Unauthorized")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/locations/v1/295212");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("apiKey", "A_82c9229354f849e78efe010d94150807")
                .build();
        request.setURI(uri);
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/locations/v1/295212")));
        assertEquals(401, response.getStatusLine().getStatusCode());
        assertEquals("Unauthorized", convertResponseToString(response));

    }

    @Test
    void get_shouldReturn200() throws URISyntaxException, IOException {
        //given
        stubFor(get(urlPathEqualTo("/locations/v1/295212"))
                .withQueryParam("apiKey", containing("82c9229354f849e78efe010d94150807"))
                .willReturn(aResponse()
                        .withStatus(200).withBody("OK")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/locations/v1/295212");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("apiKey", "A_82c9229354f849e78efe010d94150807")
                .build();
        request.setURI(uri);
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/locations/v1/295212")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("OK", convertResponseToString(response));
    }
}
