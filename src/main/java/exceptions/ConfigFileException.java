/**
 * 
 */
package exceptions;

/**
 * @author Aaron.Hayward
 *	responsible for errors detected in the config file
 */
public class ConfigFileException extends Exception {

	//******************************************************************
	// *************** you were in the middle of making this the parent class of all exceptions.************
	private static final long serialVersionUID = 1429860294195355333L;
	private final String message;
	public final ConfigCode configCode;

	/**
	 * 
	 * @param message
	 * @param configCode
	 */
	public ConfigFileException(String message, ConfigCode configCode) {
		super();
		this.message = message;
		this.configCode = configCode;
	}
	
	public ConfigFileException(String message) {
		super(message);
		this.message = message;
		this.configCode = null;
		
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
	
	public enum ConfigCode {
		DEFAULT_AND_CUSTOM
	}
}
