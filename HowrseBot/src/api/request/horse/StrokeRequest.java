package api.request.horse;

import java.util.Random;

import com.google.gson.JsonObject;

import api.ApiException;
import api.Bot;
import api.horse.Horse;
import api.request.GenericPostRequest;

public class StrokeRequest {

	public static Response sendRequest(Horse horse, Bot bot) throws ApiException {
		try {
			
			horse.updateInfo(bot);
			
			if(!horse.info.canStroke)
				throw new Exception("Cannot stroke yet!");
			
			// we gotta get some random shit ^^ (even more than normal)
			String form = horse.info.html.split("method=\"post\" id=\"form-do-stroke\">")[1].split("</form>")[0];
			
			
			String theShitName = form.split("name=\"")[1].split("\"")[0];
			String theShitVal = form.split("<input value=\"")[1].split("\"")[0];
			String scndShit = form.split("<input name=\"")[1].split("\"")[0].split("form-do-stroke")[1];
			
			String[] split2 = form.split("<input name=\"form-do-stroke");
			
			String pxName = split2[2].split("\"")[0],
					pyName = split2[3].split("\"")[0];
			
			bot.handler.setReferrer("https://www.howrse.de/elevage/chevaux/cheval?id=" + horse.id);
			
			JsonObject json = GenericPostRequest.doApiCall("elevage/chevaux/doStroke", new String[] {theShitName, scndShit, pxName, pyName}, new String[] {theShitVal, ""+horse.id, "" + new Random().nextInt(50), "" + new Random().nextInt(50)}, bot);
			
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
