package com.cristianml.reservation.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/** This class is activated at the start of the API and is responsible for enabling Cross-Origin Resource Sharing (CORS).
CORS allows external clients, such as a frontend application hosted on a different domain, to access the API securely
by setting appropriate HTTP headers. It defines the allowed origins, methods, headers, and handles preflight `OPTIONS`
requests to ensure smooth communication between the backend and external clients. **/

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Ensures this filter is executed before others.
public class CORSConfig implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic (not used here).
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // Cast request and response to HTTP-specific types.
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        // Set CORS headers to allow requests from any origin.
        response.setHeader("Access-Control-Allow-Origin", "*");
        // Specify allowed HTTP methods for CORS.
        response.setHeader("Access-Control-Allow-Methods", "DELETE, GET, OPTIONS, PATCH, POST, PUT");
        // Set how long the preflight request can be cached by the browser.
        response.setHeader("Access-Control-Max-Age", "3600");
        // Specify the headers that are allowed in requests.
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");

        // If the request method is OPTIONS (preflight request), return a 200 status.
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Pass the request to the next filter in the chain.
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic (not used here).
    }
}