/**
 * 
 */
package tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Aaron.Hayward
 * @apiNote Extension of the Properties object. Adds the ability to return the instance of the object when manually loading, and the ability to use an 'auto-load' constructor when instantiating.
 */
public class SwampProperties extends Properties {
	private static final long serialVersionUID = 4430731367937290680L;

	/**
	 * Automatically loads the properties file when passed an {@code InputStream}
	 * @param inputStream
	 * @throws IOException
	 */
	public SwampProperties(InputStream inputStream) throws IOException {
		super();
		super.load(inputStream);
	}
	
	public SwampProperties(){
		super();
	}
	
	/**
	 * Same functionality as the parent {@code Properties.load(InputStream)} but also returns the instance.
	 * @param fileInputStream
	 * @return
	 * @throws IOException
	 */
	public Properties load(FileInputStream fileInputStream) throws IOException {
		super.load(fileInputStream);
		return this;
	}

}
