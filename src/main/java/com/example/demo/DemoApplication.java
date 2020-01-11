package com.example.demo;

import com.example.demo.config.GatewayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private GatewayConfig gatewayConfig;

    @Autowired
    public DemoApplication(GatewayConfig gatewayConfig) {
        this.gatewayConfig = gatewayConfig;
    }

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("basic example:");
        gatewayConfig.basicExample("some string");
        System.out.println("split example:");
        gatewayConfig.splitExample(
                Arrays.asList("a", "b", "c")
        );
        System.out.println("routing example1:");
        gatewayConfig.routingExample1("routing example1");
        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        System.out.println("routing example2");
        gatewayConfig.routingExample2("routing example2");
    }
}
