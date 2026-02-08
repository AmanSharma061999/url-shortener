package com.shortener.url_shortener_sb.controller;

import com.shortener.url_shortener_sb.dtos.LoginRequest;
import com.shortener.url_shortener_sb.dtos.RegisterRequest;
import com.shortener.url_shortener_sb.models.User;
import com.shortener.url_shortener_sb.service.UserDetailsImpl;
import com.shortener.url_shortener_sb.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of("ok",false));
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetailsImpl userDetails)) {
            return ResponseEntity.status(401).body(Map.of("ok", false));
        }

        return ResponseEntity.ok(
                Map.of(
                        "ok", true,
                        "username", userDetails.getUsername(),
                        "roles", userDetails.getAuthorities()
                )
        );
    }

    @PostMapping("/public/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        var authResponse = userService.authenticateUser(loginRequest);
        String jwt = authResponse.getToken();

        ResponseCookie cookie = ResponseCookie.from("access_token", jwt)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(60 * 60 *2 )
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("ok", true));
    }

    /*
    * ResponseCookie.from("access_token", jwt) --> Creates cookie with the name access_token and value JWT
    * .httpOnly(true) --> JS cannot read it (document.cookie won’t show it) → prevents token theft via XSS
    * .secure(false) cause we are using localhost in production we state it true.
    * .sameSite("Lax") helps reduce CSRF risk (cookie not sent on most cross-site requests).
    * .path("/") Cookie is sent to all endpoints(/api/... etc)
    */

    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        String role = registerRequest.getRole();
        if(role == null || role.isBlank()) {
            role = "ROLE_USER";
        } else if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        user.setRole(role);
        user.setEmail(registerRequest.getEmail());
        userService.registerUser(user);
        return ResponseEntity.ok("User Registered");
    }

    // Logout now CLEARS COOKIE
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")// true only in HTTP
                .maxAge(0)// delete cookie
                .build();
        // cookie.setAttribute("SameSite", "Lax"); // if you have Spring Boot support; otherwise do header (shown below)

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(Map.of("ok",true));
    }
}




/*

Spring Boot determines which configuration file to load based on the active profile
and naming convention (application-{profile}.yml), automatically merging it with the base configuration
without any code-level references.
 */