package api.request;

import java.util.ArrayList;
import java.util.List;

import api.ApiException;
import api.Bot;
import api.horse.Horse;

public class GetHorses {

	public static List<Horse> sendRequest(Bot bot) throws ApiException {
		try {
			
			
			String response = GenericPostRequest.doRawApiCall("elevage/chevaux/searchHorse", new String[] {"go"}, new String[] {"1"}, bot);
			
			
			String[] split = response.split("<a class=\"horsename\" href=\"");
			
			
			if (split.length == 1) throw new Exception(response);
			
			ArrayList<Horse> horses = new ArrayList<Horse>();
			
			for(int i = 1; i < split.length; i++) {
				int id = Integer.parseInt(split[i].split("id=")[1].split("\"")[0]);
				String name = split[i].split(">")[1].split("<")[0];
				Horse h = new Horse();
				h.id = id;
				h.name = name;
				horses.add(h);
			}
			
			return horses;
			
		} catch(Exception e) {
			throw new ApiException(e);
		}
	}
	
}
