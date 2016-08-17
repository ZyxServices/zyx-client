package com.zyx.controller.common;

import com.zyx.constants.Constants;
import com.zyx.utils.FileUploadUtils;
import com.zyx.utils.ImagesVerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by wms on 2016/6/28.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title CommonController.java
 */
@RestController
@RequestMapping("/v1")
@Api(description = "公共接口API。1、上传一张图片。2、上传多张图片")
public class UploadCommonController {
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "上传一张图片", notes = "上传图片到服务器返回图片地址")
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

    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    @ApiOperation(value = "上传多张图片，图片大的时候慎用", notes = "上传图片到服务器返回图片地址")
    public ModelAndView uploads(@RequestPart(name = "avatar") MultipartFile[] avatars) throws ExecutionException, InterruptedException {

        AbstractView jsonView = new MappingJackson2JsonView();

        if (avatars == null || avatars.length == 0) {
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            // 创建一个线程池
            ExecutorService pool = Executors.newSingleThreadExecutor();
            // 创建多个有返回值的任务
            List<Future> list = new ArrayList<>();
            for (int i = 0; i < avatars.length; i++) {
                Callable c = new MyCallable(avatars[i]);
                // 执行任务并获取Future对象
                Future f = pool.submit(c);
                list.add(i, f);
            }
            // 关闭线程池
            pool.shutdown();

            List<String> _temp = new ArrayList<>();

            // 获取所有并发任务的运行结果
            for (Future f : list) {
                // 从Future对象上获取任务的返回值，并输出到控制台
                _temp.add(f.get().toString());
            }

            Map<String, Object> map = new HashMap<>();
            map.put(Constants.STATE, Constants.SUCCESS);
            map.put(Constants.SUCCESS_MSG, "图片上传成功");
            map.put(Constants.DATA, _temp);
            jsonView.setAttributesMap(map);
        }
        return new ModelAndView(jsonView);
    }

}

class MyCallable implements Callable<Object> {
    private MultipartFile file;

    MyCallable(MultipartFile file) {
        this.file = file;
    }

    public Object call() throws Exception {
        return FileUploadUtils.uploadFile(file);
    }
}
