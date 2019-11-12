/**
 * 
 */
package exceptions;

/**
 * @author Aaron.Hayward
 * for Errors pertaining to the swamp generated xml report file
 */
public class XMLSourceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4631218728769719042L;

	/**
	 * @param message
	 */
	public XMLSourceException(String message) {
		super(message);
	}
}
