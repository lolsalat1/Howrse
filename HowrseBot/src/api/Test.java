package api;

public class Test {

	public static void main(String[] args) {
		try {
		Bot b = new Bot();
		b.loginProcedure("lolsalat", "l0lsalat123");
		b.handleHorses();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
