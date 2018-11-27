package api.horse;

import api.ApiException;
import api.Bot;
import api.RequestHandler;

public class Horse {

	public int id;
	public String name;
	public HorseInfo info;
	
	public String toString() {
		return name + " (" + id + ")";
	}
	
	public void updateInfo(Bot bot) throws ApiException{
		try {
			String response = RequestHandler.read(bot.handler.get("https://www.howrse.de/elevage/chevaux/cheval?id=" + id, bot.default_cookie));
			HorseInfo info = new HorseInfo();
			info.html = response;
			try {
				info.canSuckle = !response.split("id=\"boutonAllaiter\"")[1].split("rel=\"nofollow\"")[0].contains("action-disabled");
			} catch(Exception e) {
				info.isGrown = true;
				info.canFeed = !response.split("id=\"boutonNourrir\"")[1].split("rel=\"nofollow\"")[0].contains("action-disabled");
			}
			info.canSoak = !response.split("id=\"boutonBoire\"")[1].split("rel=\"nofollow\"")[0].contains("action-disabled");
			info.canStroke = !response.split("id=\"boutonCaresser\"")[1].split("rel=\"nofollow\"")[0].contains("action-disabled");
			info.canGroom = !response.split("id=\"boutonPanser\"")[1].split("rel=\"nofollow\"")[0].contains("action-disabled");
			info.canEat = !response.split("id=\"boutonCarotte\"")[1].split("rel=\"nofollow\"")[0].contains("action-disabled");
			info.canPlay = !response.split("id=\"boutonJouer\"")[1].split("rel=\"nofollow\"")[0].contains("action-disabled");
			info.canSleep = !response.split("id=\"boutonCoucher\"")[1].split("rel=\"nofollow\"")[0].contains("action-disabled");
			
			this.info = info;
		} catch(Exception e) {
			throw new ApiException(e);
		}
	}
	
	public class HorseInfo {
	
		public boolean canSuckle, canSoak, canStroke, canGroom, canEat, canPlay, canSleep;
		public boolean isGrown, canFeed;
		public String html;
		@Override
		public String toString() {
			return "HorseInfo [canSuckle=" + canSuckle + ", canSoak=" + canSoak + ", canStroke=" + canStroke
					+ ", canGroom=" + canGroom + ", canEat=" + canEat + ", canPlay=" + canPlay + ", canSleep="
					+ canSleep + "]";
		}
		
		
		
	}
}
