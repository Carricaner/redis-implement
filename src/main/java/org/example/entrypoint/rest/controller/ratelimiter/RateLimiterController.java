package org.example.entrypoint.rest.controller.ratelimiter;

import jakarta.servlet.http.HttpServletRequest;
import org.example.core.usecase.ratelimiter.RateLimiterUsecase;
import org.example.core.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "Test";
    }

    @PostMapping("/flush")
    public String flush(HttpServletRequest request) {
        rateLimiterUsecase.flushAllRecord(WebUtils.getIpAddress(request));
        return "Flushed!";
    }
}

