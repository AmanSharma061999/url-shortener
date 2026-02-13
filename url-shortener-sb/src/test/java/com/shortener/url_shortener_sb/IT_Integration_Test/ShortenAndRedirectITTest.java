package com.shortener.url_shortener_sb.IT_Integration_Test;

import com.shortener.url_shortener_sb.models.UrlMapping;
import com.shortener.url_shortener_sb.repository.UrlMappingRepository;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortenAndRedirectITTest extends IntegrationTestSupport {

    @Autowired
    UrlMappingRepository urlMappingRepository;

    @Test
    void shorten_then_redirect_returns_302_and_increments_click_count() {

        String username = "user_" + System.currentTimeMillis();
        String email = username + "@test.com";
        String password = "Pass@12345";

        // 1) Register
        ResponseEntity<String> regRes = rest.exchange(
                baseUrl + "/api/auth/public/register",
                HttpMethod.POST,
                new HttpEntity<>(Map.of(
                        "email", email,
                        "username", username,
                        "password", password
                ), jsonHeaders()),
                String.class
        );
        assertThat(regRes.getStatusCode().is2xxSuccessful()).isTrue();

        // 2) Login
        AuthSession session = login(username, password);

        // 3) Shorten (auth required)
        String original = "https://example.com/path?q=1";

        ResponseEntity<Map> shortenRes = rest.exchange(
                baseUrl + "/api/urls/shorten",
                HttpMethod.POST,
                new HttpEntity<>(Map.of("originalUrl", original), authHeaders(session)),
                Map.class
        );

        assertThat(shortenRes.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(shortenRes.getBody()).isNotNull();

        String shortUrl = String.valueOf(shortenRes.getBody().get("shortUrl"));
        assertThat(shortUrl).isNotBlank();

        UrlMapping before = urlMappingRepository.findByShortUrl(shortUrl);
        assertThat(before).isNotNull();
        int beforeCount = before.getClickCount();

        // 4) Redirect (no-follow client so we can assert Location)
        RestTemplate noFollow = noFollowRedirectRestTemplate();

        ResponseEntity<Void> redirRes = noFollow.exchange(
                baseUrl + "/s/" + shortUrl,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Void.class
        );

        assertThat(redirRes.getStatusCode().value()).isIn(301, 302, 307, 308);

        URI location = redirRes.getHeaders().getLocation();
        assertThat(location).isNotNull();
        assertThat(location.toString()).isEqualTo(original);

        UrlMapping after = urlMappingRepository.findByShortUrl(shortUrl);
        assertThat(after).isNotNull();
        assertThat(after.getClickCount()).isEqualTo(beforeCount + 1);
    }

    private RestTemplate noFollowRedirectRestTemplate() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .disableRedirectHandling()
                .build();

        HttpComponentsClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(rf);
    }
}
