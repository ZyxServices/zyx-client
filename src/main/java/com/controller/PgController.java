package com.controller;

import com.zyx.pg.PgFacade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.controller
 * Create by XiaoWei on 2016/6/14
 */
@RestController
public class PgController {
    @Resource
    private PgFacade pgFacade;

    @RequestMapping(value = "/v1/circle/add", method = RequestMethod.POST)
    public ModelAndView addCircle(String title, Integer createId, Integer circleMasterId, String details, String headImgUrl) {
        Map<String, Object> map = pgFacade.insertCircle(title, createId, circleMasterId, details, headImgUrl);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }
}
