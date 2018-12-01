package test;

import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

import api.API.SERVER_COUNTRY;
import api.Breed;
import bot.BasicBreedTasksAsync;
import bot.Bot;
import utils.Return;
import utils.Sleeper;

public class Main {

	public static void main(String[] args) {
		
		String username = JOptionPane.showInputDialog("Username");
		String password = JOptionPane.showInputDialog("Password");
		SERVER_COUNTRY locale = SERVER_COUNTRY.DE;
		
		Bot bot = new Bot(username, password, locale);
		
		bot.account.api.requests.setTimeout(300);
		
		
		Return<HashMap<Integer,Breed>> b = bot.getBreeds();
		
		if(!b.sucess) {
			System.err.println("ERROR");
			System.exit(-1);
		}
		
		Breed[] breeds = new Breed[b.data.size()];
		
		Iterator<Breed> br = b.data.values().iterator();
		
		for(int i = 0; i < b.data.size(); i++)
			breeds[i] = br.next();
		
		Breed s = (Breed) JOptionPane.showInputDialog(null, "Choose breed", "breed selector", JOptionPane.PLAIN_MESSAGE, null, breeds, "default");
		
		Return<BasicBreedTasksAsync> ret = bot.basicBreedTasks(s.id, true, true, true, true, true, true, true, true, 1000, new Runnable() {

			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, "FINISHED!", "Bot", JOptionPane.PLAIN_MESSAGE);
				bot.logout();
			}
			
		});
		
		if(!ret.sucess) {
			System.err.println("ERROR");
			System.exit(-1);
		}
		
		ret.data.start();
		
		boolean b1 = true;
		
		while(b1) {
			Sleeper.sleep(1000);
			System.out.println("progress: " + ret.data.getProgress() + "%");
			System.out.println("ETA: " + (ret.data.getEta() / 1000) + "sec.");
			b1 = ret.data.running();
		}
		
	}
	
}
