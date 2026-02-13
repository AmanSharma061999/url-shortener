package com.shortener.url_shortener_sb.IT_Integration_Test;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class SecurityNegativeITTest extends IntegrationTestSupport {

    @Test
    void shorten_without_auth_returns_401() {
        try {
            ResponseEntity<String> res = rest.exchange(
                    baseUrl + "/api/urls/shorten",
                    HttpMethod.POST,
                    new HttpEntity<>(java.util.Map.of("originalUrl", "https://example.com"), json()),
                    String.class
            );

            // If we reach here, backend returned 2xx which is wrong
            fail("Expected 401 but got " + res.getStatusCode());
        } catch (HttpClientErrorException.Unauthorized ex) {
            assertThat(ex.getStatusCode().value()).isEqualTo(401);
            assertThat(ex.getResponseBodyAsString()).contains("\"status\":401");
            assertThat(ex.getResponseBodyAsString()).contains("Unauthorised");
        }
    }

    private HttpHeaders json() {
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_JSON);
        return h;
    }
}
