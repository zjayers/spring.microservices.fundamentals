package io.ayers.gateway.servercloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServerCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerCloudGatewayApplication.class, args);
    }

}
