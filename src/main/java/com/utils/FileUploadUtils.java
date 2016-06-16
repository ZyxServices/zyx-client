package com.utils;

import com.zyx.constants.AuthConstants;
import com.zyx.file.FastDFSClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by SubDong on 2016/3/16.
 */
public class FileUploadUtils {
    //图片最大上传大小
    public static int IMAGES_MAX_BYTE = 5 * 1024 * 1024;


    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public static String uploadFile(MultipartFile file) {

        String allFileName = file.getOriginalFilename();
        String fileName = getFileExtension(allFileName);
        fileName = fileName.toLowerCase();
        //"gif", "jpeg", "jpg", "bmp", "png"
        String[] strings = new String[]{"png", "gif", "jpeg", "jpeg", "jpg", "bmp"};
        try {
            if (Arrays.binarySearch(strings, fileName) != -1) {


                byte[] tempFile = file.getBytes();
                String[] images = new String[]{"png", "gif", "jpeg", "jpeg", "jpg", "bmp"};
                if (Arrays.binarySearch(images, fileName) != -1 && tempFile.length > IMAGES_MAX_BYTE) {
                    return AuthConstants.AUTH_ERROR_901 + "";
                }

                String uploadFile = FastDFSClient.uploadFiles(tempFile, allFileName);
                if (uploadFile != null) {
                    return uploadFile;
                } else {
                    return AuthConstants.AUTH_ERROR_902 + "";
                }
            } else {
                return AuthConstants.AUTH_ERROR_903 + "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return AuthConstants.AUTH_ERROR_100 + "";
        }
    }

    public static String getFileExtension(String fullName) {
        FileUploadUtils.checkNotNull(fullName);
        String fileName = (new File(fullName)).getName();
        int dotIndex = fileName.lastIndexOf(46);
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }
}