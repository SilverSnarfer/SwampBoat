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
import exceptions.SwampException.ExceptionCode;
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
	}
	
	
	@Test
	public void testInitializeFilesAndFoldersEmptyDestinations() {
		ToolName toolName = ToolName.OWASP;
		String timeStamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '.');
		boolean saveRuns = false; 
		ConfigFileException exception = null;
		Properties config = new Properties();
		
		try {
			saveRuns = PropertiesTool.nullSafe(config.getProperty("saveRuns")).trim().equalsIgnoreCase("true") ? true : false;
			FileWriterTool.initializeFilesAndFolders(config, toolName, timeStamp, saveRuns);
		} catch (ConfigFileException c) {
			exception = c;
		} catch (Exception e) {
			logger.error("Error during 'TestFileWriterTool.testInitializeFilesAndFoldersBadDestinations'", e);
		}
		Assertions.assertTrue(exception.exceptionCode != null && ExceptionCode.ConfigFile_EmptyLocations.equals(exception.exceptionCode));
	}
	
	@Test
	public void testInitializeFilesAndFoldersMixedDestinations() {
		ToolName toolName = ToolName.OWASP;
		String timeStamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '.');
		boolean saveRuns = false; 
		ConfigFileException exception = null;
		Properties config = new Properties();
		
		try {
			config.putAll(PropertiesTool.location_defaultAndCustom());
			saveRuns = PropertiesTool.nullSafe(config.getProperty("saveRuns")).trim().equalsIgnoreCase("true") ? true : false;
			FileWriterTool.initializeFilesAndFolders(config, toolName, timeStamp, saveRuns);
		} catch (ConfigFileException c) {
			exception = c;
		} catch (Exception e) {
			logger.error("Error during 'TestFileWriterTool.testInitializeFilesAndFoldersMixedDestinations'", e);
		}
		Assertions.assertTrue(exception.exceptionCode != null && ExceptionCode.ConfigFile_MixedLocations.equals(exception.exceptionCode));
	}
	@Test
	public void testInitializeFilesAndFoldersIncompleteCustomLocations() {
		ToolName toolName = ToolName.OWASP;
		String timeStamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '.');
		boolean saveRuns = false; 
		ConfigFileException exception = null;
		Properties config = new Properties();
		
		try {
			config.putAll(PropertiesTool.location_incompleteCustom());
			saveRuns = PropertiesTool.nullSafe(config.getProperty("saveRuns")).trim().equalsIgnoreCase("true") ? true : false;
			FileWriterTool.initializeFilesAndFolders(config, toolName, timeStamp, saveRuns);
		} catch (ConfigFileException c) {
			exception = c;
		} catch (Exception e) {
			logger.error("Error during 'TestFileWriterTool.testInitializeFilesAndFoldersEmptyCustomLocations'", e);
		}
		Assertions.assertTrue(exception.exceptionCode != null && ExceptionCode.ConfigFile_IncompleteCustomLocations.equals(exception.exceptionCode));
	}
	
	@Test
	public void testInitializeFilesAndFoldersEmptyFilenames() {
		ToolName toolName = ToolName.OWASP;
		String timeStamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '.');
		boolean saveRuns = false; 
		ConfigFileException exception = null;
		Properties config = new Properties();
		
		try {
			config.putAll(PropertiesTool.filenames_empty());
			saveRuns = PropertiesTool.nullSafe(config.getProperty("saveRuns")).trim().equalsIgnoreCase("true") ? true : false;
			FileWriterTool.initializeFilesAndFolders(config, toolName, timeStamp, saveRuns);
		} catch (ConfigFileException c) {
			exception = c;
		} catch (Exception e) {
			logger.error("Error during 'TestFileWriterTool.testInitializeFilesAndFoldersEmptyFilenames'", e);
		}
		Assertions.assertTrue(exception.exceptionCode != null && ExceptionCode.ConfigFile_EmptyFilenames.equals(exception.exceptionCode));
	}
	
	@Test
	public void testInitializeFilesAndFoldersMixedFilenames() {
		ToolName toolName = ToolName.OWASP;
		String timeStamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '.');
		boolean saveRuns = false; 
		ConfigFileException exception = null;
		Properties config = new Properties();
		
		try {
			config.putAll(PropertiesTool.filenames_mixed());
			saveRuns = PropertiesTool.nullSafe(config.getProperty("saveRuns")).trim().equalsIgnoreCase("true") ? true : false;
			FileWriterTool.initializeFilesAndFolders(config, toolName, timeStamp, saveRuns);
		} catch (ConfigFileException c) {
			exception = c;
		} catch (Exception e) {
			logger.error("Error during 'TestFileWriterTool.testInitializeFilesAndFoldersMixedFilenames'", e);
		}
		Assertions.assertTrue(exception.exceptionCode != null && ExceptionCode.ConfigFile_MixedFilenames.equals(exception.exceptionCode));
	}
	
	@Test
	public void testInitializeFilesAndFoldersIncompleteDefaultFilenames() {
		ToolName toolName = ToolName.OWASP;
		String timeStamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '.');
		boolean saveRuns = false; 
		ConfigFileException exception = null;
		Properties config = new Properties();
		
		try {
			config.putAll(PropertiesTool.filenames_incompleteDefault());
			saveRuns = PropertiesTool.nullSafe(config.getProperty("saveRuns")).trim().equalsIgnoreCase("true") ? true : false;
			FileWriterTool.initializeFilesAndFolders(config, toolName, timeStamp, saveRuns);
		} catch (ConfigFileException c) {
			exception = c;
		} catch (Exception e) {
			logger.error("Error during 'TestFileWriterTool.testInitializeFilesAndFoldersIncompleteDefaultFilenames'", e);
		}
		Assertions.assertTrue(exception.exceptionCode != null && ExceptionCode.ConfigFile_IncompleteDefaultFilenames.equals(exception.exceptionCode));
	}
	
	@Test
	public void testInitializeFilesAndFoldersIncompleteCustomFilenames() {
		ToolName toolName = ToolName.OWASP;
		String timeStamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '.');
		boolean saveRuns = false; 
		ConfigFileException exception = null;
		Properties config = new Properties();
		
		try {
			config.putAll(PropertiesTool.filenames_incompleteCustom());
			saveRuns = PropertiesTool.nullSafe(config.getProperty("saveRuns")).trim().equalsIgnoreCase("true") ? true : false;
			FileWriterTool.initializeFilesAndFolders(config, toolName, timeStamp, saveRuns);
		} catch (ConfigFileException c) {
			exception = c;
		} catch (Exception e) {
			logger.error("Error during 'TestFileWriterTool.testInitializeFilesAndFoldersIncompleteCustomFilenames'", e);
		}
		Assertions.assertTrue(exception.exceptionCode != null && ExceptionCode.ConfigFile_IncompleteCustomFilenames.equals(exception.exceptionCode));
	}
}
