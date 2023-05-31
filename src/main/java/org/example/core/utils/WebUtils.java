package org.example.core.utils;

import jakarta.servlet.http.HttpServletRequest;

public class WebUtils {
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        // When the request is from local, the ip will be like "0:0:0:0:0:0:0:1"
        if ("0:0:0:0:0:0:0:1".equalsIgnoreCase(ipAddress)) {
            ipAddress = "0000";
        }
        return ipAddress;
    }
}
