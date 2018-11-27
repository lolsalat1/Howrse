package api.request;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import api.ApiException;
import api.Bot;
import api.RequestHandler;

public class GenericPostRequest {

	public static CloseableHttpResponse rawApiCall(String command, String[] params, String[] values, Bot bot) throws ApiException {
		try {
			CloseableHttpResponse post = bot.handler.post("https://www.howrse.de/" + command, bot.default_cookie, params, values);
			for(Header h : post.getHeaders("Set-Cookie")) {
				if(h.getValue().contains("sessionprod")) {
					bot.updateSessionProd(h.getValue().split("sessionprod=")[1].split(";")[0]);
				}
			}
			return post;
		} catch(Exception e) {
			throw new ApiException(e);
		}
	}
	
	public static JsonObject doApiCall(String command, String[] params, String[] values, Bot bot) throws ApiException {
		try {
			CloseableHttpResponse post = bot.handler.post("https://www.howrse.de/" + command, bot.default_cookie, params, values);
			
			for(Header h : post.getHeaders("Set-Cookie")) {
				if(h.getValue().contains("sessionprod")) {
					bot.updateSessionProd( h.getValue().split("sessionprod=")[1].split(";")[0]);
				}
			}
			
			String re = RequestHandler.read(post);
			return new JsonParser().parse(re).getAsJsonObject();
		} catch(Exception e) {
			throw new ApiException(e);
		}
	}
	
	public static String doRawApiCall(String command, String[] params, String[] values, Bot bot) throws ApiException {
		try {
			CloseableHttpResponse post = bot.handler.post("https://www.howrse.de/" + command, bot.default_cookie, params, values);
					for(Header h : post.getHeaders("Set-Cookie")) {
						if(h.getValue().contains("sessionprod")) {
							bot.updateSessionProd(h.getValue().split("sessionprod=")[1].split(";")[0]);
						}
					}
					return RequestHandler.read(post);
		} catch(Exception e) {
			throw new ApiException(e);
		}
	}
	
}
