/**
 * 
 */
package exceptions;

/**
 * @author Aaron.Hayward
 *
 */
public class BugReportException extends Exception {

	/**
	 * 
	 */
	public BugReportException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public BugReportException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public BugReportException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BugReportException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public BugReportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
