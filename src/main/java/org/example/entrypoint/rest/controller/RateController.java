package org.example.entrypoint.rest.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.core.domain.ratelimiter.RateLimiter;
import org.example.core.usecase.user.UserUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rate")
public class RateController{
    private final RateLimiter rateLimiter;
    private final UserUsecase userUsecase;

    @Autowired
    public RateController(
            @Qualifier("sliding-window") RateLimiter rateLimiter, UserUsecase userUsecase) {
        this.rateLimiter = rateLimiter;
        this.userUsecase = userUsecase;
    }

    @GetMapping("/test")
    public String testRate(HttpServletRequest request) {
        return rateLimiter.isAllowed(getIpAddress(request)) ?
                "success" : "fail";
    }

    @GetMapping("/test123")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void testRedisRepository(
            @RequestParam("username") String username,
            HttpServletRequest request) {
        userUsecase.createRole();
    }

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

