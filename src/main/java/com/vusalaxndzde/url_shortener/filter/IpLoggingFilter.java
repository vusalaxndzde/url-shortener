package com.vusalaxndzde.url_shortener.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IpLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(IpLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ipAddress = httpRequest.getRemoteAddr();
        logger.info("Request from IP: " + ipAddress);

        chain.doFilter(request, response);
    }

}
