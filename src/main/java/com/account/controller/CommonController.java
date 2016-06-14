package com.account.controller;

import com.constants.AuthConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WeiMinSheng on 2016/6/14.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title CommonController.java
 */
@RestController
@RequestMapping("/v1")
public class CommonController {
    @RequestMapping("/timestamp")
    public ModelAndView timestamp() {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(date);
        map.put(AuthConstants.TIMESTAMP_A, System.currentTimeMillis());
        map.put(AuthConstants.TIMESTAMP_B, str);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }
}
