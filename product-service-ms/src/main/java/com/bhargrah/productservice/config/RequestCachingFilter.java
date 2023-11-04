package com.bhargrah.productservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "RequestCachingFilter", urlPatterns = "/*")
public class RequestCachingFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(RequestCachingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String id = request.getRequestId();
        String address = request.getRequestURI();
        String method = request.getMethod();
        String params =  request.getQueryString();

        logger.info("\n------------------------------ " +
                        "\nID: {} " +
                        "\nAddress: {} " +
                        "\nHttp-Method: {} " +
                        "\nPayload: {} " +
                        "\n------------------------------" ,
                id,address,method,params);

        filterChain.doFilter(request, response);
    }
}