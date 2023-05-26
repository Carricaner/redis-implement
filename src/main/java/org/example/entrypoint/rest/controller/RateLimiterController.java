package org.example.entrypoint.rest.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.core.usecase.ratelimiter.RateLimiterUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rate")
public class RateLimiterController {
    private final RateLimiterUsecase rateLimiterUsecase;

    @Autowired
    public RateLimiterController(RateLimiterUsecase rateLimiterUsecase) {
        this.rateLimiterUsecase = rateLimiterUsecase;
    }

    @GetMapping("/test")
    public String testRateLimiter(HttpServletRequest request) {
        return rateLimiterUsecase.isAllowed(getIpAddress(request)) ? "PASS" : "BLOCKED";
    }

    @PostMapping("/flush")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String flush(HttpServletRequest request) {
        rateLimiterUsecase.flushAllRecord(getIpAddress(request));
        return "Flushed!";
    }

    // When the request is from local, the ip will be like "0:0:0:0:0:0:0:1"
    private String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equalsIgnoreCase(ipAddress)) {
            ipAddress = "0000";
        }
        return ipAddress;
    }
}

