package api;

import java.util.List;

import api.horse.Horse;
import api.request.CreateSessionProdRequest;
import api.request.GetHorses;
import api.request.LoginRequest;
import api.request.horse.GroomRequest;
import api.request.horse.SoakRequest;
import api.request.horse.StrokeRequest;
import api.request.horse.SuckleRequest;
import logging.Logger;

public class Bot {

	public String sessionprod;
	public List<Horse> horses;
	public Logger logger;
	public RequestHandler handler;
	public String default_cookie;
	
	public Bot() {
		init();
	}
	
	public void init () {
		handler = new RequestHandler();
		logger = new Logger();
	}
	
	public void handleHorses() {
		for(Horse h : horses) {
			updateHorse(h);
			if(h.info.canSuckle) {
				Suckle(h);
			}
			if(h.info.canSoak) {
				Drink(h);
			}
			if(h.info.canStroke) {
				Stroke(h);
			}
			if(h.info.canGroom) {
				Groom(h);
			}
		}
	}
	
	public void updateHorse(Horse horse) {
		try {
			horse.updateInfo(this);
		} catch(ApiException e) {
			logger.errln("Bot", "couldn't update horse " + horse + ":");
			e.printStackTrace();
		}
	}
	
	public void Suckle(Horse horse) {
		try {
			logger.logln("Bot", "Suckling " + horse + ". . .");
			if(SuckleRequest.sendRequest(horse, this).sucess) {
				logger.logln("Bot", "Suckled " + horse + "!");
			} else {
				logger.errln("Bot", "couldn't suckle " + horse + "(no error information)!");
			}
		} catch(ApiException e) {
			logger.errln("Bot", "couldn't suckle " + horse + ":");
			e.printStackTrace();
		}
	}
	
	public void Groom(Horse horse) {
		try {
			logger.logln("Bot", "Grooming " + horse + ". . .");
			if(GroomRequest.sendRequest(horse, this).sucess) {
				logger.logln("Bot", "Groomed " + horse + "!");
			} else {
				logger.errln("Bot", "couldn't groom " + horse + "(no error information)!");
			}
		} catch(ApiException e) {
			logger.errln("Bot", "couldn't groom " + horse + ":");
			e.printStackTrace();
		}
	}
	
	public void Drink(Horse horse) {
		try {
			logger.logln("Bot", "Soaking (drink) " + horse + ". . .");
			if(SoakRequest.sendRequest(horse, this).sucess) {
				logger.logln("Bot", "Soaked (drink) " + horse + "!");
			} else {
				logger.errln("Bot", "couldn't soak " + horse + "(no error information)!");
			}
		} catch(ApiException e) {
			logger.errln("Bot", "couldn't soak " + horse + ":");
			e.printStackTrace();
		}
	}
	
	public void Stroke(Horse horse) {
		try {
			logger.logln("Bot", "Stroking " + horse + ". . .");
			if(StrokeRequest.sendRequest(horse, this).sucess) {
				logger.logln("Bot", "Stroked " + horse + "!");
			} else {
				logger.errln("Bot", "couldn't stroke " + horse + "(no error information)!");
			}
		} catch(ApiException e) {
			logger.errln("Bot", "couldn't stroke " + horse + ":");
			e.printStackTrace();
		}
	}
	
	public void loginProcedure(String username, String password) {
		logger.name = username;
		getSessionProd();
		default_cookie = "sessionprod=" + sessionprod + ";firstlogin=1;";
		login(username, password);
		updateHorses();
	}
	
	public void updateHorses() {
		try {
			logger.logln("Bot", "Updating horses . . .");
			List<Horse> horses = GetHorses.sendRequest(this);
			this.horses = horses;
			logger.logln("Bot", "You've got " + horses.size() + " horses :");
			for(Horse h : horses) {
				System.out.println("\t" + h.toString());
			}
		} catch(ApiException e) {
			logger.errln("Bot", "couldn't update horses:");
			e.printStackTrace();
		}
	}
	
	public void updateSessionProd(String sessionprod) {
		if(sessionprod.equals("deleted"))
			return;
		this.sessionprod = sessionprod;
		logger.logln("Bot", "got new sessionprod: " + sessionprod);
		default_cookie = "sessionprod=" + sessionprod + ";firstlogin=1;";
	}
	
	public void login(String username, String password) {
		try {
			logger.logln("Bot", "Logging in . . .");
			LoginRequest.Response res = LoginRequest.sendRequest(username, password, this);
			
			if(res.sucess) {
				logger.logln("Bot", "logged in as " + username + "!");
			} else 
				logger.errln("Bot", "couldn't log in (no error information)!");
		} catch(Exception e) {
			logger.errln("Bot", "couldn't log in:");
			e.printStackTrace();
		}
	}
	
	public void getSessionProd() {
		try {
			logger.logln("Bot", "getting sessionprod");
			CreateSessionProdRequest.Response res = CreateSessionProdRequest.sendRequest(this);
			if(res.sucess) {
				sessionprod = res.sessionprod;
				logger.logln("Bot", "got sessionprod: " + res.sessionprod);
			} else 
				logger.errln("Bot", "couldn't get sessionprod (no error information)!");
		} catch(ApiException e) {
			logger.errln("Bot", "couldn't get sessionprod:");
			e.printStackTrace();
		}
	}
	
	
	
}
