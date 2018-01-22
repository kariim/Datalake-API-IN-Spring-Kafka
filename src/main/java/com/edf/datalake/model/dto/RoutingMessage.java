package com.edf.datalake.model.dto;

public class RoutingMessage {

    public String apiKey;
    public String topic;
    public String message;

    public RoutingMessage(String apiKey, String topic, String message) {
        this.apiKey = apiKey;
        this.topic = topic;
        this.message = message;
    }
}
