package org.example.entrypoint.rest.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.core.domain.ratelimiter.RateLimiter;
import org.example.core.usecase.rate.RateUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rate")
public class RateController{
    private final RateUsecase rateUsecase;

    @Autowired
    public RateController(RateUsecase rateUsecase) {
        this.rateUsecase = rateUsecase;
    }

    @GetMapping("/test")
    public String testRate(HttpServletRequest request) {
        return rateUsecase.isAllowed(getIpAddress(request)) ? "OK" : "NOT OK";
    }

    private String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equalsIgnoreCase(ipAddress)) {
            ipAddress = "0000";
        } else {
            ipAddress = "default";
        }
        return ipAddress;
    }
}

