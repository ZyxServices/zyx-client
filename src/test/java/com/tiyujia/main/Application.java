package com.tiyujia.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
//@SpringBootApplication
//@ComponentScan("com.tiyujia.*")
@ComponentScan("com.*")
@Configuration 
@EnableAutoConfiguration 
//@ComponentScan
@ImportResource({"classpath:dubbo-consumer.xml"}) //加入spring的bean的xml文件   
public class Application {
   //https://github.com/mybatis/mybatis-spring-boot
	//http://blog.csdn.net/isea533/article/details/50359390
	//https://github.com/abel533/MyBatis-Spring-Boot/blob/master/pom.xml
	//https://github.com/abel533/MyBatis-Spring-Boot
	//http://spring.io/guides/tutorials/spring-boot-oauth2/
	//http://localhost:8080/cities/view/1
	//https://github.com/rajithd/spring-boot-oauth2
	//http://localhost:8080/user/view/1
	public static void main(String[] args) {
		SpringApplication.run(Application.class,	args);

	}

}
