package com.cloud.service2.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class SpringCloudService2DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudService2DemoApplication.class, args);
	}

	@Value("${spring.application.name}")
	private String servername;
	@Value("${server.port}")
	private int port;
	
	@RequestMapping("/hello")
	public String hello(@RequestParam("name") String name) {
		return "hello," + name + "我是" + servername + "端口号是" + port;
	}
}

