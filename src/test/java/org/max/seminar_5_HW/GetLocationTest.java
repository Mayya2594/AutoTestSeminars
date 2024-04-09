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

public class GetLocationTest extends AbstractTest{

    @Test
    void get_shouldReturn404() throws URISyntaxException, IOException {
        //given
        stubFor(get(urlPathEqualTo("/locations/cities/autocomplete"))
                .withQueryParam("apiKey", equalTo("82c9229354f849e78efe010d94150807"))
                .withQueryParam("q", equalTo("petrozavodsk"))
                .willReturn(aResponse()
                        .withStatus(404).withBody("Not found")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/locations/cities/autocomplete");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("apiKey", "82c9229354f849e78efe010d94150807")
                .addParameter("q", "petrozavodsk")
                .build();
        request.setURI(uri);
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/locations/cities/autocomplete")));
        assertEquals(404, response.getStatusLine().getStatusCode());
        assertEquals("Not found", convertResponseToString(response));
    }

}
