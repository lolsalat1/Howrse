package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;

public class RequestHandler {

	public RequestHandler() {
		client = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
		headers.add(new BasicNameValuePair("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0"));
	//	headers.add(new BasicNameValuePair("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
	//	headers.add(new BasicNameValuePair("Accept-Language", "de,en-US;q=0.7,en;q=0.3"));
	//	headers.add(new BasicNameValuePair("Accept-Encoding", "gzip, deflate, br"));
		headers.add(new BasicNameValuePair("Host", "www.howrse.de"));
	//	headers.add(new BasicNameValuePair("Pragma", "no-cache"));
		headers.add(new BasicNameValuePair("X-Requested-With", "XMLHttpRequest"));
	}
	
	public void setReferrer(String ref) {
		headers.removeIf(new Predicate<NameValuePair>() {

			@Override
			public boolean test(NameValuePair t) {
				return t.getName().equals("Referer");
			}
			
		});
		
		headers.add(new BasicNameValuePair("Referer", ref));
	}
	
	public HttpClient client;
	public List<NameValuePair> headers = new ArrayList<NameValuePair>();
	
	public static String read(CloseableHttpResponse res) throws IOException {
		
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(res.getEntity().getContent()))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        } finally {
        	res.close();
        }
    }
	public CloseableHttpResponse get (String url, String cookies) {
		HttpGet get = new HttpGet(url);
		get.setHeader("Cookie", cookies);
		try {
			return HttpClientBuilder.create().build().execute(get);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public CloseableHttpResponse post (String url, String cookies, String[] params, String[] values) {
		
		
		HttpPost post = new HttpPost(url);
		post.setHeader("Cookie", cookies);
		
		List<NameValuePair> p = new ArrayList<NameValuePair>();
		
		for(int i = 0; i < params.length; i++) {
			p.add(new BasicNameValuePair(params[i], values[i]));
		}
		for(NameValuePair n : headers) {
			post.setHeader(n.getName(), n.getValue());
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
