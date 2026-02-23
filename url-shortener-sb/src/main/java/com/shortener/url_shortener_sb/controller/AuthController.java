package com.shortener.url_shortener_sb.controller;

import com.shortener.url_shortener_sb.dtos.LoginRequest;
import com.shortener.url_shortener_sb.dtos.RegisterRequest;
import com.shortener.url_shortener_sb.dtos.responses.ApiResponse;
import com.shortener.url_shortener_sb.dtos.responses.MeResponse;
import com.shortener.url_shortener_sb.security.UserDetailsImpl;
import com.shortener.url_shortener_sb.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final boolean cookieSecure;

    public AuthController(UserService userService, @Value("${app.cookie.secure:false}") boolean cookieSecure) {
        this.userService=userService;
        this.cookieSecure=cookieSecure;
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(new MeResponse(false,null,null));
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetailsImpl userDetails)) {
            return ResponseEntity.status(401).body(new MeResponse(false,null,null));
        }

        var roles= userDetails.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .toList();

        return ResponseEntity.ok(
                new MeResponse(true, userDetails.getUsername(), roles));
    }

    @PostMapping("/public/login")
    public ResponseEntity<ApiResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        var authResponse = userService.authenticateUser(loginRequest);
        String jwt = authResponse.getToken();

        ResponseCookie cookie = ResponseCookie.from("access_token", jwt)
                .httpOnly(true)
                .secure(true)                    // prod true
                .sameSite("None")                // required
                .domain(".linxlytics.com")       // required for subdomains
                .path("/")
                .maxAge(Duration.ofDays(2))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ApiResponse(true));
    }

    /*
    * ResponseCookie.from("access_token", jwt) --> Creates cookie with the name access_token and value JWT
    * .httpOnly(true) --> JS cannot read it (document.cookie won’t show it) → prevents token theft via XSS
    * .secure(false) cause we are using localhost in production we state it true.
    * .sameSite("Lax") helps reduce CSRF risk (cookie not sent on most cross-site requests).
    * .path("/") Cookie is sent to all endpoints(/api/... etc)
    */

    @PostMapping("/public/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        userService.registerUser(registerRequest);
        return ResponseEntity.ok(new ApiResponse(true));
    }

    // Logout now CLEARS COOKIE
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")// true only in HTTP
                .maxAge(0)// delete cookie
                .build();
        // cookie.setAttribute("SameSite", "Lax"); // if you have Spring Boot support; otherwise do header (shown below)

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new ApiResponse(true));
    }
}




/*

Spring Boot determines which configuration file to load based on the active profile
and naming convention (application-{profile}.yml), automatically merging it with the base configuration
without any code-level references.
 */