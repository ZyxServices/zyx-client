package com.tiyujia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiyujia.mapper.PlayMapper;
import com.tiyujia.model.Player;
import com.tiyujia.service.PlayService;
@Service("playService")
public class PlayServiceImpl implements PlayService {
	
	@Autowired
    private PlayMapper playMapper;

	@Override
	public Player findByState(String id) {
		return playMapper.findByState(id);
	}

}
