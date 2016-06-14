package com.zyx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyx.entity.shop.Goods;
import com.zyx.rpc.shop.ShopService;

//@RestController
//@RequestMapping("/shop")
public class ShopController {
	@Autowired
	private ShopService shopService;

	@RequestMapping(value = "/view/{id}")
	public Goods view(@PathVariable Long id) {
		Goods goods = shopService.getGoodsbyKey(id);
		return goods;
	}

}
