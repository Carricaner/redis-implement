package org.example.entry.rest.pubsub.input;

public record PublishMessageRequestBody(
    String topic,
    String message
) {}
