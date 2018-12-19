package com.cloud.service.feign.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrixDashboard
@EnableCircuitBreaker
@RestController
public class SpringCloudServiceFeignDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudServiceFeignDemoApplication.class, args);
	}

	@FeignClient(name="serviceserver", fallback = errorService.class)
	public interface Serviceserver{
		@RequestMapping(value = "/hello", method = RequestMethod.GET)
		String hello(@RequestParam("name") String name);
	}

	@Component
	public class errorService implements Serviceserver{
		@Override
		public String hello(String name) {
			return "error" + name;
		}
	}
	
	@Autowired
	private Serviceserver serviceserver;
	
	@RequestMapping(value="/hello")
	public String hello() {
		return serviceserver.hello("zhangsan");
	}
	
	@Value("${name}")
	private String name;
	@Value("${age}")
	private int age;
	@Value("${version}")
	private String version;
	
	@RequestMapping("test")
	public String test() {
		return name + "-" + age + "-" + version;
	}
}

