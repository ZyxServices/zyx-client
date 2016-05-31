package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
@SpringBootApplication
@ComponentScan("com.*")
@ImportResource({"classpath:dubbo-consumer.xml"})
public class Client {
	public static void main(String[] args) {
		SpringApplication.run(Client.class,	args);
	}

}
