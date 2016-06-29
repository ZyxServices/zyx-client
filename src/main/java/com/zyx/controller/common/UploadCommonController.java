package com.zyx.controller.common;

import com.zyx.constants.Constants;
import com.zyx.utils.FileUploadUtils;
import com.zyx.utils.ImagesVerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WeiMinSheng on 2016/6/28.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title CommonController.java
 */
@RestController
@RequestMapping("/v1")
@Api(description = "公共接口API。1、上传图片。")
public class UploadCommonController {
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "上传图片", notes = "上传图片到服务器返回图片地址")
    public ModelAndView upload(@RequestPart(name = "avatar") MultipartFile avatar) {

        AbstractView jsonView = new MappingJackson2JsonView();
        if (avatar == null) {
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            System.out.println("avatar  :  " + avatar);
            System.out.println("avatar.getName()  :  " + avatar.getName());
            String avatarId = FileUploadUtils.uploadFile(avatar);
            Map<String, Object> map = ImagesVerifyUtils.verify(avatarId);
            if (map != null) {
                jsonView.setAttributesMap(map);
            } else {
                map = new HashMap<>();
                map.put(Constants.STATE, Constants.SUCCESS);
                map.put(Constants.SUCCESS_MSG, "图片上传成功");
                Map<String, Object> map2 = new HashMap<>();
                map2.put("url", avatarId);
                map.put(Constants.DATA, map2);
                jsonView.setAttributesMap(map);
            }
        }
        return new ModelAndView(jsonView);
    }
}
