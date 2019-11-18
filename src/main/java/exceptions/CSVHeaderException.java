package exceptions;

public class CSVHeaderException extends SwampException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3728854155921766404L;

	public CSVHeaderException(String message) {
		super(message);
	}
	
	public CSVHeaderException(String message, ExceptionCode configCode) {
		super(message, configCode);
	}
}
