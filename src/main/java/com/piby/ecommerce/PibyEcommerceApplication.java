package com.piby.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PibyEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PibyEcommerceApplication.class, args);
	}

//	@Bean
//	InMemoryMetricRepository inMemoryMetricRepository(){
//		return new InMemoryMetricRepository();
//	}
//
//	@Bean
//	PublicMetrics publicMetrics(InMemoryMetricRepository repository){
//		return new MetricReaderPublicMetrics(repository);
//	}
}
