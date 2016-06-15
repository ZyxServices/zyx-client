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

public class testLiveClient {

	public static void main(String[] args) {
		try {
//			testLiveClient.testMultipartPost();
//			testUpdatePost();
//			testGetListPost();
			testGetByIdtPost();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testMultipartPost() throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:8080/v1/live/create");
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
        HttpPost httpPost = new HttpPost("http://localhost:8080/v1/live/update");
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
        HttpPost httpPost = new HttpPost("http://localhost:8080/v1/live/list");
        try {
        	CloseableHttpClient client = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("id", "41"));
//            params.add(new BasicNameValuePair("isPublic", "true"));
            params.add(new BasicNameValuePair("type", "1"));
//            params.add(new BasicNameValuePair("start", "1465892150000"));
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
        HttpPost httpPost = new HttpPost("http://localhost:8080/v1/live/get");
        try {
        	CloseableHttpClient client = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", "41"));
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity( params, "UTF-8");
			httpPost.setEntity(httpEntity );
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println("*************get****************");
            System.out.println("************"+EntityUtils.toString(response.getEntity()));
        } finally {
            httpPost.releaseConnection();
        }
    }
}
