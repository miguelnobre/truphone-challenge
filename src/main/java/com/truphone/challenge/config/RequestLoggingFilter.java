package com.truphone.challenge.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {
    public static final String MDC_TOKEN_KEY = "request-uuid";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            MDC.put(MDC_TOKEN_KEY, UUID.randomUUID().toString());

            String signatureName = request.getRequestURI();
            log.info("Request made to {} with the follow parameters: {}", signatureName, request.getParameterMap()
                    .entrySet()
                    .stream()
                    .map(this::parseParameter)
                    .collect(Collectors.toList()));

            // Continue the request
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_TOKEN_KEY);
        }
    }

    private String parseParameter(Map.Entry<String, String[]> param) {
        return "( " + param.getKey() + ": " + String.join(",", param.getValue()) + " )";
    }
}
