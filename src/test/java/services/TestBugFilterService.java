/**
 * 
 */
package services;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;

import TestingTools.CleanupUtility;

/**
 * @author Aaron.Hayward
 *
 */
public class TestBugFilterService {
	private static final Logger logger = LogManager.getLogger(TestWriterDispatcherService.class);
	private final JaxbService jaxbService = new JaxbService();
	
	@AfterAll
	public static void cleanup() {
		logger.info("finished tests");
		try {
			CleanupUtility.cleanup();
			logger.info("Cleanup successful. Dont forget to cleanout the trash folder after a while: (" + System.getProperty("user.home") + "\\swampTrash" + ")");
		} catch (IOException e) {
			logger.error("Unable to cleanup.", e);
		}		
	}
	
	

}
