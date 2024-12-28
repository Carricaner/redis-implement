package org.example.entry.rest.controller.pubsub;

public record PublishMessageRequestBody(
    String topic,
    String message
) {}
