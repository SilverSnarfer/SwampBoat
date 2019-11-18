/**
 * 
 */
package exceptions;
/**
 * @author Aaron.Hayward
 *
 */
public class SwampException extends Exception {

	private final String message;
	public final Enum<ExceptionCode> exceptionCode;

	/**
	 * 
	 * @param message
	 * @param code
	 */
	public SwampException(String message, Enum<ExceptionCode> code) {
		super();
		this.message = code.toString() + " - " + message;
		this.exceptionCode = code;
	}
	
	public SwampException(String message) {
		super(message);
		this.message = message;
		this.exceptionCode = null;
		
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
	
	public enum ExceptionCode {
		ConfigFile_MixedLocations,
		ConfigFile_IncompleteCustomLocations,
		ConfigFile_EmptyCustomLocations,
		ConfigFile_EmptyLocations,
		ConfigFile_EmptyFilenames,
		ConfigFile_MixedFilenames,
		ConfigFile_IncompleteDefaultFilenames,
		ConfigFile_IncompleteCustomFilenames
	}
	

}
