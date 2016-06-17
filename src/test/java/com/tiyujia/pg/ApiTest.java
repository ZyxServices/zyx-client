package com.tiyujia.pg;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.junit.Test;

import java.io.IOException;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.tiyujia.pg
 * Create by XiaoWei on 2016/6/17
 */
public class ApiTest {

    @Test
   public void addCircle() throws IOException {
        HttpClient client=new HttpClient();
        PostMethod  postMethod=new PostMethod("http://localhost:8080/v1/circle/add");
        postMethod.addParameter("title","1");
        postMethod.addParameter("createId","2");
        postMethod.addParameter("circleMasterId","3");
        postMethod.addParameter("details","4");
        postMethod.addParameter("headImgUrl","5");
       client.executeMethod(postMethod);
        //打印服务器返回的状态
        System.out.println(postMethod.getStatusLine());
        //打印返回的信息
        System.out.println(postMethod.getResponseBodyAsString());
        //释放连接
        postMethod.releaseConnection();
    }

    @Test
    public  void addMeet() throws IOException {
        HttpClient client=new HttpClient();
        PostMethod  postMethod=new PostMethod("http://localhost:8080/v1/circle/meetting");
        postMethod.addParameter("createId","1");
        postMethod.addParameter("circleMasterId","2");
        client.executeMethod(postMethod);
        //打印服务器返回的状态
        System.out.println(postMethod.getStatusLine());
        //打印返回的信息
        System.out.println(postMethod.getResponseBodyAsString());
        //释放连接
        postMethod.releaseConnection();
    }

    @Test
    public void addCern() throws IOException {
        HttpClient client=new HttpClient();
        PostMethod  postMethod=new PostMethod("http://localhost:8080/v1/circle/add");
        postMethod.addParameter("userId","1");
        postMethod.addParameter("cernTitle","2");
        postMethod.addParameter("content","3");
        postMethod.addParameter("imgUrl","4");
        postMethod.addParameter("videoUrl","5");
        postMethod.addParameter("visible","6");
        client.executeMethod(postMethod);
        //打印服务器返回的状态
        System.out.println(postMethod.getStatusLine());
        //打印返回的信息
        System.out.println(postMethod.getResponseBodyAsString());
        //释放连接
        postMethod.releaseConnection();
    }

}
