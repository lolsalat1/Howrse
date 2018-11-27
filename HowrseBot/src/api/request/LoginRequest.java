package api.request;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import api.ApiException;
import api.Bot;
import api.RequestHandler;

public class LoginRequest {

	public static Response sendRequest(String username, String password, Bot bot) throws ApiException {
		CloseableHttpResponse response = GenericPostRequest.rawApiCall("site/doLogIn", new String[] {"12321", "isBoxStyle", "login", "password", "redirection"}, new String[] {bot.sessionprod, "", username, password, "https://www.howrse.de/"}, bot);
		JsonObject json;
		try {
			json = new JsonParser().parse(RequestHandler.read(response)).getAsJsonObject();
		} catch (Exception e) {
			throw new ApiException(e);
		}
		Response res = new Response();
		res.sucess = json.get("errors").getAsJsonArray().size() == 0;
		res.errorsText = json.get("errorsText").getAsString();
		res.sessionprod = response.getFirstHeader("Set-Cookie").getValue().split("=")[1].split(";")[0];
		return res;
	}

	public static class Response {
		public boolean sucess;
		public String sessionprod;
		public String errorsText;
	}
	
}
