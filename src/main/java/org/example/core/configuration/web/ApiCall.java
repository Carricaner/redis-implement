package org.example.core.configuration.web;

import java.time.Instant;

public record ApiCall(String endpoint, Instant timestamp, long responseTimeMs) {}
