package com.points.fetchrewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class FetchRewardsApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FetchRewardsApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "80"));
		app.run(args);
	}

}
