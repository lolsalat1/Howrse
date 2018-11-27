package api.request.horse;

import com.google.gson.JsonObject;

import api.ApiException;
import api.Bot;
import api.horse.Horse;
import api.request.GenericPostRequest;

public class SuckleRequest {

	public static Response sendRequest(Horse horse, Bot bot) throws ApiException {
		try {
			
			horse.updateInfo(bot);
			
			if(!horse.info.canSuckle)
				throw new Exception("Cannot suckle yet!");
			
			// we gotta get some random shit ^^
			String form = horse.info.html.split("method=\"post\" id=\"form-do-suckle\">")[1].split("</form>")[0];
			
			
			String theShitName = form.split("name=\"")[1].split("\"")[0];
			String theShitVal = form.split("<input value=\"")[1].split("\"")[0];
			String scndShit = form.split("<input name=\"")[1].split("\"")[0].split("form-do-suckle")[1];
			
			bot.handler.setReferrer("https://www.howrse.de/elevage/chevaux/cheval?id=" + horse.id);
			
			JsonObject json = GenericPostRequest.doApiCall("elevage/chevaux/doSuckle", new String[] {theShitName, scndShit}, new String[] {theShitVal, ""+horse.id}, bot);
			
			Response res = new Response();
			
			res.sucess = json.get("errors").getAsJsonArray().size() == 0;
			
			
			return res;
		} catch(Exception e) {
			throw new ApiException(e);
		}
	}
	
	public static class Response {
		public boolean sucess;
	}
	
}
