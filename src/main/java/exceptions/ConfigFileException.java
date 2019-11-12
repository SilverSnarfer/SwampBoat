/**
 * 
 */
package exceptions;

/**
 * @author Aaron.Hayward
 *	responsible for errors detected in the config file
 */
public class ConfigFileException extends Exception {

	private static final long serialVersionUID = 1429860294195355333L;
	/**
	 * @param message
	 */
	public ConfigFileException(String message) {
		super(message);
	}
}
