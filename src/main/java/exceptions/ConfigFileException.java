/**
 * 
 */
package exceptions;

/**
 * @author Aaron.Hayward
 *	responsible for errors detected in the config file
 */
public class ConfigFileException extends SwampException {

	private static final long serialVersionUID = 1429860294195355333L;
	public ConfigFileException(String message, ExceptionCode exceptionCode) {
		super(message, exceptionCode);
	}
	
	public ConfigFileException(String message) {
		super(message);
	}
}
