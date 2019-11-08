/**
 * 
 */
package exceptions;

/**
 * @author Aaron.Hayward
 *
 * Handles exceptions that may occur while filtering BugInstances
 */
public class BugFilterException extends Exception {

	/**
	 * 
	 */
	public BugFilterException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public BugFilterException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public BugFilterException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BugFilterException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public BugFilterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
