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
	private static final long serialVersionUID = 4370567196305806877L;

	/**
	 * @param message
	 */
	public BugFilterException(String message) {
		super(message);
	}
}
