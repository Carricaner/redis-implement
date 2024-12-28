package org.example.entry.rest.ringbuffer;

import java.util.ArrayList;
import java.util.List;
import org.example.core.configuration.web.ApiCall;
import org.example.core.configuration.web.ApiMetricsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ring-buffer")
public class RingBufferController {
  private final ApiMetricsService apiMetricsService;

  public RingBufferController(ApiMetricsService apiMetricsService) {
    this.apiMetricsService = apiMetricsService;
  }

  @GetMapping("/api-records")
  public List<ApiCall> getApiMetrics() {
    List<ApiCall> result = new ArrayList<>();
    for (ApiCall apiCall : apiMetricsService.getRecentApiCalls()) {
      result.add(apiCall);
    }
    return result;
  }
}
