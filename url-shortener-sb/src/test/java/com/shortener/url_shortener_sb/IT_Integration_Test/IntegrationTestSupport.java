package com.shortener.url_shortener_sb.IT_Integration_Test;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class IntegrationTestSupport {

    @LocalServerPort
    protected int port;

    @Autowired
    protected RestTemplateBuilder restTemplateBuilder;

    protected RestTemplate rest;
    protected String baseUrl;

    @BeforeEach
    void setup() {
        baseUrl = "http://localhost:" + port;
        rest = restTemplateBuilder.build();
    }

    protected record AuthSession(String cookieHeader) {}

    protected HttpHeaders jsonHeaders() {
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_JSON);
        return h;
    }

    /**
     * Login return cookieHeader: "access_token=..."
     * Backend returns token via Set-Cookie (NOT response body).
     */
    protected AuthSession login(String username, String password) {
        String url = baseUrl + "/api/auth/public/login";

        Map<String, String> body = Map.of(
                "username", username,
                "password", password
        );

        ResponseEntity<String> res = rest.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(body, jsonHeaders()),
                String.class
        );

        if (!res.getStatusCode().is2xxSuccessful()) {
            throw new AssertionError("Login failed: " + res.getStatusCode() + " body=" + res.getBody());
        }

        String cookieHeader = extractAccessTokenCookie(res.getHeaders());
        if (cookieHeader == null) {
            throw new AssertionError("Login succeeded but access_token cookie was not set. Headers=" + res.getHeaders());
        }

        return new AuthSession(cookieHeader);
    }

    protected HttpHeaders authHeaders(AuthSession session) {
        HttpHeaders h = jsonHeaders();
        h.add(HttpHeaders.COOKIE, session.cookieHeader()); // Cookie: access_token=...
        return h;
    }

    protected String extractAccessTokenCookie(HttpHeaders headers) {
        List<String> setCookies = headers.get(HttpHeaders.SET_COOKIE);
        if (setCookies == null) return null;

        for (String sc : setCookies) {
            if (sc != null && sc.startsWith("access_token=")) {
                return sc.split(";", 2)[0]; // keep only "access_token=...."
            }
        }
        return null;
    }
}
