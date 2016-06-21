package com.tiyujia.pg;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
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
        postMethod.addParameter("state","0");
        postMethod.addParameter("type","0");
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
    public  void meeting() throws IOException {
        HttpClient client=new HttpClient();
        GetMethod getMethod=new GetMethod("http://localhost:8080/v1/circle/meetting?circleId=1&accountId=2");
        client.executeMethod(getMethod);
        //打印服务器返回的状态
        System.out.println(getMethod.getStatusLine());
        //打印返回的信息
        System.out.println(getMethod.getResponseBodyAsString());
        //释放连接
        getMethod.releaseConnection();
    }

    @Test
    public void addCern() throws IOException {
        HttpClient client=new HttpClient();
        PostMethod  postMethod=new PostMethod("http://localhost:8080/v1/cern/add");
        postMethod.addParameter("userId","1");
        postMethod.addParameter("type","2");
        postMethod.addParameter("cernTitle","3");
        postMethod.addParameter("content","4");
        postMethod.addParameter("imgUrl","5");
        postMethod.addParameter("videoUrl","6");
        postMethod.addParameter("visible","7");
        client.executeMethod(postMethod);
        //打印服务器返回的状态
        System.out.println(postMethod.getStatusLine());
        //打印返回的信息
        System.out.println(postMethod.getResponseBodyAsString());
        //释放连接
        postMethod.releaseConnection();
    }

    @Test
    public void zan() throws IOException {
        HttpClient client=new HttpClient();
        GetMethod getMethod=new GetMethod("http://localhost:8080/v1/zan/add?bodyId=1&bodyType=2&accountId=3");
        client.executeMethod(getMethod);
        //打印服务器返回的状态
        System.out.println(getMethod.getStatusLine());
        //打印返回的信息
        System.out.println(getMethod.getResponseBodyAsString());
        //释放连接
        getMethod.releaseConnection();
    }

    @Test
    public void addMyConcern() throws IOException {
        HttpClient client=new HttpClient();
        GetMethod getMethod=new GetMethod("http://localhost:8080/v1/cern/addMyConcern?concernId=2&concernType=2&accountId=3");
        client.executeMethod(getMethod);
        //打印服务器返回的状态
        System.out.println(getMethod.getStatusLine());
        //打印返回的信息
        System.out.println(getMethod.getResponseBodyAsString());
        //释放连接
        getMethod.releaseConnection();
    }

    @Test
    public void circleList() throws IOException {
        HttpClient client=new HttpClient();
        GetMethod getMethod=new GetMethod("http://localhost:8080//v1/circle/list?max=100");
        client.executeMethod(getMethod);
        //打印服务器返回的状态
        System.out.println(getMethod.getStatusLine());
        //打印返回的信息
        System.out.println(getMethod.getResponseBodyAsString());
        //释放连接
        getMethod.releaseConnection();
    }

}
