package com.bhargrah.orderservice.config;

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

@Order(value = Ordered.LOWEST_PRECEDENCE)
@Component
@WebFilter(filterName = "ResponseCachingFilter", urlPatterns = "/*")
public class ResponseCachingFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(ResponseCachingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String response_id = request.getRequestId();
        int responseStatus = response.getStatus();
        logger.info("\n------------------------------ " +
                        "\nID: {} " +
                        "\nResponse-Code: {} " +
                        "\n------------------------------" ,
                response_id,responseStatus);

        filterChain.doFilter(request, response);
    }
}