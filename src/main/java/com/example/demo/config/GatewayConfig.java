package com.example.demo.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Collection;

import static com.example.demo.config.IntegrationFlowConfig.ROUTING_EXAMPLE_2_CHANNEL_NAME;

@MessagingGateway
public interface GatewayConfig {

    @Gateway(requestChannel = "basicFlow.input")
    String basicExample(String string);

    @Gateway(requestChannel = "splitFlow.input")
    Collection<String> splitExample(Collection<String> string);

    @Gateway(requestChannel = "routingFlow1.input")
    void routingExample1(String string);

    @Gateway(requestChannel = ROUTING_EXAMPLE_2_CHANNEL_NAME)
    void routingExample2(String string);
}
