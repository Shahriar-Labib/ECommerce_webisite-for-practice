package com.OnlineCart.OnlineCart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.OnlineCart")
@EnableJpaRepositories(basePackages = "com.OnlineCart.repository")  // Scan repository package
@EntityScan(basePackages = "com.OnlineCart.model")
public class OnlineCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineCartApplication.class, args);
	}

}
