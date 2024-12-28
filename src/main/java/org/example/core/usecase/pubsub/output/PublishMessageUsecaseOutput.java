package org.example.core.usecase.pubsub.output;

public record PublishMessageUsecaseOutput(boolean isSuccessful, long ClientReceivedNumber) {}
