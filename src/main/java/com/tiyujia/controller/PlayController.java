package com.tiyujia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tiyujia.model.Player;
import com.tiyujia.service.PlayService;

@RestController 
@RequestMapping(value="/play")
public class PlayController {
	@Autowired
	PlayService playService;
	
	@RequestMapping(value="/{id}",	method=RequestMethod.GET)	
	public	Player	getUser(@PathVariable	String	id)	{
		
		return playService.findByState(id);
	}
	
}
