package io.ayers.services.servicealbums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceAlbumsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAlbumsApplication.class, args);
    }

}
