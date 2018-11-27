package api;

public class ApiException extends Exception {

	private static final long serialVersionUID = 9166822616584744178L;

	public ApiException(Exception e) {
		super(e);
	}
	
}
