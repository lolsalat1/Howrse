package logging;

public class Logger {

	public String name = "";
	
	public void logln(String sender, String text) {
		System.out.println("[" + name + "][" + sender + "]: " + text);
	}
	
	public void errln(String sender, String text) {
		System.err.println("[" + name + "][" + sender + "]: " + text);
	}
	
}
