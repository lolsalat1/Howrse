package api.request;

import org.apache.http.client.methods.CloseableHttpResponse;

import api.ApiException;
import api.Bot;

public class CreateSessionProdRequest {
	
	public static Response sendRequest(Bot bot) throws ApiException {
		try {
			CloseableHttpResponse res = bot.handler.get("https://www.howrse.de/site/logIn?publicite=1", "");
			Response r = new Response();
			r.sessionprod = res.getFirstHeader("Set-Cookie").getValue().split("sessionprod=")[1].split(";")[0];
			r.sucess = r.sessionprod != null;
			return r;
		} catch(Exception e) {
			throw new ApiException(e);
		}
	}
	
	public static class Response {
		
		public boolean sucess;
		public String sessionprod;
		
	}
}
