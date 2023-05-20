package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.FixedWindowRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RateController {

    final FixedWindowRateLimiter rateLimiter;

    @Autowired
    public RateController(FixedWindowRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equalsIgnoreCase(ipAddress)) {
            ipAddress = "000000000";
        }
        return rateLimiter.isAllowed(ipAddress) ? "success" : "fail";
    }
}

