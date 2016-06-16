package com.utils;

import com.zyx.constants.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SubDong on 16-6-16.
 *
 * @author SubDong
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title ImagesVerifyUtils
 * @package com.utils
 * @update 16-6-16 上午10:21
 */
public class ImagesVerifyUtils {

    public static Map<String, Object> verify(String uploadFile) {

        Map<String, Object> map = new HashMap<>();

        if (uploadFile == null || uploadFile.equals(Constants.AUTH_ERROR_902 + "")) {
            map.put(Constants.STATE, Constants.ERROR);
            map.put(Constants.ERROR_MSG, "图片上传失败,请重试");
            return map;
        }

        if (uploadFile.equals(Constants.AUTH_ERROR_903 + "")) {
            map.put(Constants.STATE, Constants.AUTH_ERROR_903);
            map.put(Constants.ERROR_MSG, "文件格式错误");
            return map;
        }

        if (uploadFile.equals(Constants.AUTH_ERROR_901 + "")) {
            map.put(Constants.STATE, Constants.AUTH_ERROR_901);
            map.put(Constants.ERROR_MSG, "图片大小不能超过5MB");
            return map;
        }
        return null;
    }
}
