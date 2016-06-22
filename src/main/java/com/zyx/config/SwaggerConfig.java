package com.zyx.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.google.common.base.Predicate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
//http://blog.csdn.net/catoop/article/details/50668896
//http://blog.csdn.net/jia20003/article/details/50700736
public class SwaggerConfig {
	
	
	/**
     * 可以定义多个组，比如本类中定义把test和demo区分开了
     * （访问页面就可以看到效果了） 
     *
     *
     */
  /* @Bean
    public Docket testApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("account")
         //       .genericModelSubstitutes(DeferredResult.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/v1/account")//api测试请求地址
                .select()
               // .paths(PathSelectors.regex("/common/.*"))//过滤的接口
                .paths(PathSelectors.regex("/*"))//过滤的接口
                .build()
                .apiInfo(testApiInfo());
    }*/
    
	
	    
   
   
  /*  @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.any()) // 对所有api进行监控
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build();
    }*/

  
    
    @Bean
    public Docket shopApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("shop")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.zyx.controller.shop"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build();
    }

    private ApiInfo testApiInfo() {
        ApiInfo apiInfo = new ApiInfo("用户接口api",//大标题
                "用户登陆，注册，。。。。。。。",//小标题
                "0.1",//版本
                "NO terms of service",
                "魏民生",//作者
                "The Apache License, Version 2.0",//链接显示文字
                "http://www.ronghuazhang.com:9090/sonarqube/ "//网站链接
        );

        return apiInfo;
    }

 
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
          .addResourceLocations("classpath:/META-INF/resources/");
     
        registry.addResourceHandler("/webjars/**")
          .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}