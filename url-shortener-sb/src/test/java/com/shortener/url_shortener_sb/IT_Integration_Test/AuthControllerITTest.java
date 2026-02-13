package com.shortener.url_shortener_sb.IT_Integration_Test;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthControllerITTest extends IntegrationTestSupport {

    @Test
    void register_then_login_works() {

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

        // 2) Login -> should set cookie access_token
        AuthSession session = login(username, password);

        assertThat(session.cookieHeader()).isNotNull();
        assertThat(session.cookieHeader()).startsWith("access_token=");
    }
}
