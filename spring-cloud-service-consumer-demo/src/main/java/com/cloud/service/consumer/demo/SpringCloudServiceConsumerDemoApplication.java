package com.cloud.service.consumer.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableHystrixDashboard
@RestController
public class SpringCloudServiceConsumerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudServiceConsumerDemoApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("hello")
	@HystrixCommand(fallbackMethod="errorback")
	public String hello(String name) {
		String url = "http://serviceserver/hello?name=" + name;
		String result = restTemplate.getForObject(url, String.class);
		return result;
	}
	
	public String errorback(String name) {
		return "hello  ,"+name+"!  sorry ,error !";
	}
}

