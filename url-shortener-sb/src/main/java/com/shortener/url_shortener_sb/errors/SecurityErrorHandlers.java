package com.shortener.url_shortener_sb.errors;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;

@Component
public class SecurityErrorHandlers implements AuthenticationEntryPoint, AccessDeniedHandler {

    private final ObjectMapper mapper;

    public SecurityErrorHandlers(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String traceId() {
        String t = MDC.get("traceId");
        return (t == null) ? "" : t;
    }

    private void write(HttpServletRequest request, HttpServletResponse response,int status, String message) throws IOException {
        String tid = traceId();

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("X-Trace-Id", tid);

        ApiError body = new ApiError(Instant.now(), request.getRequestURI(),status, message, traceId());

        response.getWriter().write(mapper.writeValueAsString(body));
        response.getWriter().flush();
    }

    //when authentication is missing/invalid and the endpoint requires auth.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        write(request, response,401, "Unauthorised");
    }

    //when the user is authenticated but lacks permission.
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        write(request, response, 403, "Forbidden");
    }
}
