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
	private static final long serialVersionUID = -439939536232583633L;
	/**
	 * @param message
	 */
	public BugReportException(String message) {
		super(message);
	}
}
