package com.tiyujia.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping(value="/role")
public class RoleController {

	
	@RequestMapping(value="/{id}",	method=RequestMethod.GET)	
	public	String	getUser(@PathVariable	Long	id)	{
		
		//	...				}
		return "------"+id;
	}
}
