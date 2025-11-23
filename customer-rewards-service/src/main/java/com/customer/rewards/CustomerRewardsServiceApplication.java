package com.customer.rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CustomerRewardsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerRewardsServiceApplication.class, args);
	}

}
