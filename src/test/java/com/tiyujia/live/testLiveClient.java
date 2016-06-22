package com.tiyujia.live;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.zyx.vo.common.TimeAreaVo;

public class testLiveClient {

	private static String IP_ADDRESS = "http://114.112.98.72:8089/";
	public static void main(String[] args) {
		try {
//			testLiveClient.testCreateLivePost();
//			testUpdatePost();
			testGetListPost();
//			testGetByIdtPost();
//			testTextCreatePost();
//			testGetTextListPost();
//			testGetTextPost();
//			testDeleteTextPost();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testCreateLivePost() throws IOException {
        HttpPost httpPost = new HttpPost(IP_ADDRESS+"/v1/live/create");
        try {
        	CloseableHttpClient client = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("isPublic", "true"));
            params.add(new BasicNameValuePair("type", "1"));
            params.add(new BasicNameValuePair("start", "1465892150000"));
            params.add(new BasicNameValuePair("end", "1465898000000"));
            params.add(new BasicNameValuePair("lab", "1"));
            params.add(new BasicNameValuePair("title", "test live"));
            params.add(new BasicNameValuePair("userId", "1"));
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity( params, "UTF-8");
			httpPost.setEntity(httpEntity );
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } finally {
            httpPost.releaseConnection();
        }
    }
	
	public static void testUpdatePost() throws IOException {
        HttpPost httpPost = new HttpPost(IP_ADDRESS+"/v1/live/update");
        try {
        	CloseableHttpClient client = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", "41"));
            params.add(new BasicNameValuePair("isPublic", "true"));
            params.add(new BasicNameValuePair("type", "1"));
            params.add(new BasicNameValuePair("start", "1465892150000"));
            params.add(new BasicNameValuePair("end", "1465898000000"));
            params.add(new BasicNameValuePair("lab", "1"));
            params.add(new BasicNameValuePair("title", "test live update"));
            params.add(new BasicNameValuePair("userId", "1"));
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity( params, "UTF-8");
			httpPost.setEntity(httpEntity );
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } finally {
            httpPost.releaseConnection();
        }
    }
	
	public static void testGetListPost() throws IOException {
        HttpPost httpPost = new HttpPost(IP_ADDRESS+"/v1/live/list");
        try {
        	CloseableHttpClient client = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("id", "41"));
//            params.add(new BasicNameValuePair("isPublic", "true"));
//            params.add(new BasicNameValuePair("type", "1"));
            TimeAreaVo createArea =  new TimeAreaVo();
            createArea.setStart(1465883462800L);
            createArea.setEnd(1465883462820L);
            params.add(new BasicNameValuePair("createTime", JSON.toJSONString(createArea)));
//            params.add(new BasicNameValuePair("end", "1465898000000"));
//            params.add(new BasicNameValuePair("lab", "1"));
//            params.add(new BasicNameValuePair("title", "test live update"));
//            params.add(new BasicNameValuePair("userId", "1"));
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity( params, "UTF-8");
			httpPost.setEntity(httpEntity );
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println("*************get list****************");
            System.out.println("************"+EntityUtils.toString(response.getEntity()));
        } finally {
            httpPost.releaseConnection();
        }
    }
	
	public static void testGetByIdtPost() throws IOException {
        HttpPost httpPost = new HttpPost(IP_ADDRESS+"/v1/live/get");
        try {
        	CloseableHttpClient client = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", "37"));
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity( params, "UTF-8");
			httpPost.setEntity(httpEntity );
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println("*************get****************");
            System.out.println("************"+EntityUtils.toString(response.getEntity()));
        } finally {
            httpPost.releaseConnection();
        }
    }
	
	public static void testTextCreatePost() throws IOException {
        HttpPost httpPost = new HttpPost(IP_ADDRESS+"/v1/live/text/create");
        try {
        	CloseableHttpClient client = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("liveId", "37"));
            params.add(new BasicNameValuePair("content", "text text text"));
            params.add(new BasicNameValuePair("imgUrl", "img img img"));
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity( params, "UTF-8");
			httpPost.setEntity(httpEntity );
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } finally {
            httpPost.releaseConnection();
        }
    }
	
	public static void testGetTextListPost() throws IOException {
        HttpPost httpPost = new HttpPost(IP_ADDRESS+"/v1/live/text/list");
        try {
        	CloseableHttpClient client = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            TimeAreaVo createArea =  new TimeAreaVo();
            createArea.setStart(1466060000000L);
            createArea.setEnd(1466060549000L);
            params.add(new BasicNameValuePair("createTime", JSON.toJSONString(createArea)));
//            params.add(new BasicNameValuePair("liveId", "1"));
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity( params, "UTF-8");
			httpPost.setEntity(httpEntity );
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println("*************get list****************");
            System.out.println("************"+EntityUtils.toString(response.getEntity()));
        } finally {
            httpPost.releaseConnection();
        }
    }
	
	public static void testGetTextPost() throws IOException {
        HttpPost httpPost = new HttpPost(IP_ADDRESS+"/v1/live/text/get");
        try {
        	CloseableHttpClient client = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", "1"));
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity( params, "UTF-8");
			httpPost.setEntity(httpEntity );
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println("*************get list****************");
            System.out.println("************"+EntityUtils.toString(response.getEntity()));
        } finally {
            httpPost.releaseConnection();
        }
    }
	public static void testDeleteTextPost() throws IOException {
        HttpPost httpPost = new HttpPost(IP_ADDRESS+"/v1/live/text/delete");
        try {
        	CloseableHttpClient client = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", "1"));
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity( params, "UTF-8");
			httpPost.setEntity(httpEntity );
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println("*************get list****************");
            System.out.println("************"+EntityUtils.toString(response.getEntity()));
        } finally {
            httpPost.releaseConnection();
        }
    }
	
	public static void testGetBarragePost() throws IOException {
        HttpPost httpPost = new HttpPost(IP_ADDRESS+"/v1/live/barrage/list");
        try {
        	CloseableHttpClient client = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            TimeAreaVo createArea =  new TimeAreaVo();
            createArea.setStart(1466060000000L);
            createArea.setEnd(1466060549000L);
            params.add(new BasicNameValuePair("createTime", JSON.toJSONString(createArea)));
//            params.add(new BasicNameValuePair("liveId", "1"));
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity( params, "UTF-8");
			httpPost.setEntity(httpEntity );
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println("*************get list****************");
            System.out.println("************"+EntityUtils.toString(response.getEntity()));
        } finally {
            httpPost.releaseConnection();
        }
    }
}
