/**
 * 
 */
package main.java.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import TestingTools.Filenames;
import TestingTools.PropertiesTool;
import exceptions.ConfigFileException;
import main.java.services.TestWriterDispatcherService;
import pojo.AnalyzerReport.ToolName;
import tools.FileWriterTool;
import wrappers.SwampProperties;

/**
 * @author Aaron.Hayward
 *
 */
public class TestFileWriterTool {
	private static final Logger logger = LogManager.getLogger(TestWriterDispatcherService.class);

	@AfterAll
	public static void cleanup() {
		try {
			FileWriterTool.cleanup(new File(System.getProperty("user.home") + "/swampTest/default"), new File(System.getProperty("user.home") + "/swampTest/custom"));
			logger.info("Cleanup successful. Dont forget to cleanout the trash folder after a while: (" + System.getProperty("user.home") + "\\swampTrash" + ")");
		} catch (IOException e) {
			logger.error("Unable to cleanup.", e);
		}		
	}
	@Test
	public void testInitializeFilesAndFolders() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.OWASP;
		String timeStamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '.');
		boolean saveRuns = false; 

		Properties config = null;
		try {
			config = new SwampProperties(new FileInputStream(Filenames.goodConfig));
			saveRuns = config.getProperty("saveRuns").trim().equalsIgnoreCase("true") ? true : false;
			FileWriterTool.initializeFilesAndFolders(config, toolName, timeStamp, saveRuns);
		} catch (Exception e) {
			exceptionFound = true;
			logger.error("Error during 'TestFileWriterTool.testInitializeFilesAndFolders'", e);
		}
		
		Assertions.assertFalse(exceptionFound);
		Assertions.assertTrue(new File(config.getProperty("defaultCsvDestination") + "/" +toolName.toString() + (!saveRuns ? "" : timeStamp)).exists());
	}
	
	
	@Test
	public void testInitializeFilesAndFoldersBadDestinations() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.OWASP;
		String timeStamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '.');
		boolean saveRuns = false; 

		Properties config = new Properties();
		
		try {
			saveRuns = PropertiesTool.nullSafe(config.getProperty("saveRuns")).trim().equalsIgnoreCase("true") ? true : false;
			FileWriterTool.initializeFilesAndFolders(config, toolName, timeStamp, saveRuns);
		} catch (ConfigFileException c) {
			exceptionFound = true;
		} catch (Exception e) {
			logger.error("Error during 'TestFileWriterTool.testInitializeFilesAndFoldersBadDestinations'", e);
		}
		
		Assertions.assertTrue(exceptionFound);
		Assertions.assertFalse(new File(PropertiesTool.nullSafe(config.getProperty("defaultCsvDestination")) + "/" +toolName.toString() + (!saveRuns ? "" : timeStamp)).exists());
	}
}
