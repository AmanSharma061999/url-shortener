package com.shortener.url_shortener_sb.auth.controller;

import com.shortener.url_shortener_sb.auth.dto.ApiResponse;
import com.shortener.url_shortener_sb.auth.dto.LoginRequest;
import com.shortener.url_shortener_sb.auth.dto.MeResponse;
import com.shortener.url_shortener_sb.auth.dto.RegisterRequest;
import com.shortener.url_shortener_sb.auth.security.UserDetailsImpl;
import com.shortener.url_shortener_sb.auth.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final boolean cookieSecure;
    private final UserService userService;
    private final String cookieDomain;
    private final String cookieSameSite;

    public AuthController(
            UserService userService,
            @Value("${app.cookie.secure:false}") boolean cookieSecure,
            @Value("${app.cookie.domain:}") String cookieDomain,
            @Value("${app.cookie.same-site:Lax}") String cookieSameSite
    ) {
        this.userService = userService;
        this.cookieSecure = cookieSecure;
        this.cookieDomain = cookieDomain;
        this.cookieSameSite = cookieSameSite;
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(new MeResponse(false, null, null));
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetailsImpl userDetails)) {
            return ResponseEntity.status(401).body(new MeResponse(false, null, null));
        }

        var roles = userDetails.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .toList();

        return ResponseEntity.ok(new MeResponse(true, userDetails.getUsername(), roles));
    }

    @GetMapping("/internal/userid")
    public Long getUserId(@RequestParam String username) {
        return userService.findByUsername(username).getId();
    }

    @PostMapping("/public/login")
    public ResponseEntity<ApiResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        var authResponse = userService.authenticateUser(loginRequest);
        String jwt = authResponse.getToken();

        ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from("token", jwt)
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite(cookieSameSite)
                .path("/")
                .maxAge(Duration.ofDays(2));

        if (cookieDomain != null && !cookieDomain.isBlank()) {
            cookieBuilder.domain(cookieDomain);
        }

        ResponseCookie cookie = cookieBuilder.build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ApiResponse(true));
    }

    @PostMapping("/public/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.registerUser(registerRequest);
        return ResponseEntity.ok(new ApiResponse(true));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletResponse response) {
        ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite(cookieSameSite)
                .path("/")
                .maxAge(0);

        if (cookieDomain != null && !cookieDomain.isBlank()) {
            cookieBuilder.domain(cookieDomain);
        }

        ResponseCookie cookie = cookieBuilder.build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new ApiResponse(true));
    }
}