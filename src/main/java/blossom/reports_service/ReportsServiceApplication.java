package blossom.reports_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "blossom.reports_service")
@EnableFeignClients(basePackages = "blossom.reports_service.model")
public class ReportsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportsServiceApplication.class, args);
	}

}