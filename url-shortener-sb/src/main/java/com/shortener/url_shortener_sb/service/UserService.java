package com.shortener.url_shortener_sb.service;


import com.shortener.url_shortener_sb.dtos.LoginRequest;
import com.shortener.url_shortener_sb.dtos.RegisterRequest;
import com.shortener.url_shortener_sb.models.User;
import com.shortener.url_shortener_sb.repository.UserRepository;
import com.shortener.url_shortener_sb.security.UserDetailsImpl;
import com.shortener.url_shortener_sb.security.jwt.JwtAuthenticationResponse;
import com.shortener.url_shortener_sb.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtutils;

    public User registerUser(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername().trim();
        String email = registerRequest.getEmail().toLowerCase();

        if(userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if(userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails =(UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtutils.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwt);
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(
                () -> new UsernameNotFoundException("USer not found with the username"));
    }
}






//I never manually create UserDetailsImpl during login because Spring Security does it internally.
// When AuthenticationManager.authenticate() is called, Spring invokes UserDetailsServiceImpl,which returns a UserDetailsImpl instance.
// That instance becomes the authenticated principal and is automatically available in the Authentication object.
