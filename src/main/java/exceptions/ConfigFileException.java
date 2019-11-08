/**
 * 
 */
package exceptions;

/**
 * @author Aaron.Hayward
 *	responsible for errors detected in the config file
 */
public class ConfigFileException extends Exception {

	/**
	 * 
	 */
	public ConfigFileException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ConfigFileException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ConfigFileException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConfigFileException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ConfigFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
