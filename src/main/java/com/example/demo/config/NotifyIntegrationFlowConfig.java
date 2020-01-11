package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

@Configuration
@IntegrationComponentScan
public class NotifyIntegrationFlowConfig {

    public static final String ROUTING_EXAMPLE_2_CHANNEL_NAME = "routingExample2ChannelName";

    @Bean
    public IntegrationFlow basicFlow() {
        return f -> f
                .<String, String>transform(str -> {
                    System.out.println(str);
                    return str;
                });
    }

    @Bean
    public IntegrationFlow splitFlow() {
        return f -> f
                .split()
                .<String, String>transform(str -> {
                    System.out.println(str);
                    return str;
                })
                .aggregate();
    }

    @Bean
    public IntegrationFlow routingFlow1() {
        return f -> f
                .handle((payload, headers) -> Arrays.asList(1, 2, 3, 4))
                .split()
                .channel(MessageChannels.executor(ForkJoinPool.commonPool()))
                .<Integer, Boolean>route(o -> o % 2 == 0, m -> m
                        .subFlowMapping(true, firstFlow())
                        .subFlowMapping(false, secondFlow()));
    }

    @Bean
    public IntegrationFlow routingFlow2() {
        return IntegrationFlows.from(ROUTING_EXAMPLE_2_CHANNEL_NAME)
                .handle((payload, headers) -> Arrays.asList(1, 2))
                .split()
                .channel(MessageChannels.executor(ForkJoinPool.commonPool()))
                .routeToRecipients(r -> r
                        .recipientFlow(firstFlow())
                        .recipientFlow(secondFlow())
                )
                .get();
    }

    private IntegrationFlow firstFlow() {
        return f -> f
                .handle(msg -> System.out.println(
                        msg.getPayload() + " in the firstFlow. Thread - " + Thread.currentThread().getName()
                ));
    }

    private IntegrationFlow secondFlow() {
        return f -> f
                .handle(msg -> System.out.println(
                        msg.getPayload() + " in the secondFlow. Thread - " + Thread.currentThread().getName()
                ));
    }
}
