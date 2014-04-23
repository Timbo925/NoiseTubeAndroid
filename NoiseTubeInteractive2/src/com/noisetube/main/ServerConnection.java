package com.noisetube.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Application;

import com.google.gson.Gson;

/**
 * @author Tim
 * This class is automaticly initialized when appliation starts
 * Access trough: ServerConnection serverConnection = (ServerConnection) getApplication();
 */
public class ServerConnection extends Application {
	HttpClient client = new DefaultHttpClient();
	HttpResponse response;
	Gson gson = new Gson();

	/**
	 * @param url - url to send get request to 
	 * @param body - body formated in JSON
	 * @return - String returned form server in JSON 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public JsonResponse get(String url, String body) {
		HttpGet get = new HttpGet(url);
		JSONObject jsonObject = new JSONObject();
		JsonResponse jsonResponse = new JsonResponse();
		

		try {			
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) { //Successfull operation from server
				jsonResponse.setStatus(JsonResponse.SUCCESS);
				jsonResponse.setMessage(EntityUtils.toString(response.getEntity()));
				return jsonResponse;
			} else {
				jsonResponse.setStatus(JsonResponse.ERROR);
				return jsonResponse;
			}	
		} catch (IOException e) {
			jsonResponse.setStatus(JsonResponse.ERROR);
			e.printStackTrace();
			return jsonResponse;
		}

	}

	/**
	 * @param url - url to send get request to
	 * @return - String returned form server in JSON
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public JsonResponse get(String url) throws ClientProtocolException, IOException {
		return get(url,"");
	}

	/**
	 * @param url - Url to post to
	 * @param body - body formated in JSON
	 * @return - JsonResponse Class
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public JsonResponse post(String url, String body) {
		HttpPost post = new HttpPost(url);
		JsonResponse jsonResponse = new JsonResponse();
		try {
			
			post.setHeader("Content-type", "application/json");		
			StringEntity stringEntity = new StringEntity(gson.toJson(body).toString());
			stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(stringEntity);

			response = client.execute(post);

			if (response.getStatusLine().getStatusCode() == 200) { //Successfull operation from server
				jsonResponse.setStatus(JsonResponse.SUCCESS);
				jsonResponse.setMessage(EntityUtils.toString(response.getEntity()));
				return jsonResponse;
			} else {
				jsonResponse.setStatus(JsonResponse.ERROR);
				return jsonResponse;
			}	
		}  catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			jsonResponse.setStatus(JsonResponse.ERROR);
			jsonResponse.setMessage(JsonResponse.ERROR_NOTJSON);
			return jsonResponse;
		}	catch (IOException e) {
			e.printStackTrace();
			jsonResponse.setStatus(JsonResponse.ERROR);
			return jsonResponse;
		}
	}

	/**
	 * @param url - URL to post to
	 * @return - JsonResponse class
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public JsonResponse post(String url) throws ClientProtocolException, IOException {
		return post(url, "");
	}
}
