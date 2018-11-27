package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class Requests {
	
	public static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

	
	public static CloseableHttpResponse get (String url, String[] cookies) {
		HttpGet get = new HttpGet(url);
		StringBuilder c = new StringBuilder();
		for(String s : cookies) {
			c.append(s).append(";");
		}
		get.setHeader("Cookie", c.toString());
		
		
		try {
			return HttpClientBuilder.create().build().execute(get);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static CloseableHttpResponse post (String url, String[] cookies, String[] params, String[] values) {
		HttpPost post = new HttpPost(url);
		StringBuilder c = new StringBuilder();
		for(String s : cookies) {
			c.append(s).append(";");
		}
		post.setHeader("Cookie", c.toString());
		
		List<NameValuePair> p = new ArrayList<NameValuePair>();
		
		for(int i = 0; i < params.length; i++) {
			p.add(new BasicNameValuePair(params[i], values[i]));
		}
		
		
		try {
			post.setEntity(new UrlEncodedFormEntity(p));
			return HttpClientBuilder.create().build().execute(post);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
