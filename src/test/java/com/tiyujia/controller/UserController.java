package com.tiyujia.controller;

import com.mobileservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobileservice.dubborpc.UserFacade;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserFacade userFacade;
	   
	
	@RequestMapping("/")
	    @ResponseBody
	    String home() {
	        return "Hello World!";
	    }

	@RequestMapping(value = "/view/{id}")
  public User getUser(@PathVariable Long id){
	  User user =  userFacade.getById(id);
	  return user;
  }
}
