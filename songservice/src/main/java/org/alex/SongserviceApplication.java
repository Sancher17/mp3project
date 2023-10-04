package org.alex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SongserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SongserviceApplication.class, args);
	}
}