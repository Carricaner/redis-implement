package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.ratelimiter.RateLimiter;
import org.example.repository.redis.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rate")
public class RateController{
    private final RateLimiter rateLimiter;
    private final UserRepository userRepository;

    @Autowired
    public RateController(
            @Qualifier("sliding-window") RateLimiter rateLimiter,
            UserRepository userRepository) {
        this.rateLimiter = rateLimiter;
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public String testRate(HttpServletRequest request) {
        return rateLimiter.isAllowed(getIpAddress(request)) ?
                "success" : "fail";
    }

    @GetMapping("/test123")
    public User testRedisRepository(
            @RequestParam("username") String username,
            HttpServletRequest request) {
        System.out.println("halt");
        return userRepository.save(new User(username == null ? "default" : username));
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

