package com.transport.order_service.client;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DriverClient {

    private final WebClient webClient;

    public DriverClient(WebClient.Builder builder,
                        @Value("${services.driver.url}") String baseUrl) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    public boolean isDriverActive(UUID driverId, String token) {
        try {
            webClient.get()
                    .uri("/drivers/active")
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}