package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import TestingTools.CleanupUtility;
import TestingTools.TestFilenames;
import pojo.AnalyzerReport;
import pojo.AnalyzerReport.ToolName;
import tools.SwampProperties;

public class TestWriterDispatcherService {
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
	
	@Test
	public void testWriterInstantiation() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.OWASP;
		try {
			Properties config = new SwampProperties(new FileInputStream(TestFilenames.goodConfig));
			new WriterDispatcherService(config, toolName);	
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWriterInstantiation'", e);
			exceptionFound = true;
		}
		
		Assert.assertFalse(exceptionFound);
	}
	
	
	@Test
	public void testWriteOWASP() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.OWASP;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(TestFilenames.goodOWASP));
			Properties config = new SwampProperties(new FileInputStream(TestFilenames.goodConfig));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWriteOWASP'", e);
			exceptionFound = true;
		}
		
		Assert.assertFalse(exceptionFound);
	}
	
	@Test
	public void testWritePMD() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.PMD;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(TestFilenames.goodPMD));
			Properties config = new SwampProperties(new FileInputStream(TestFilenames.goodConfig));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWritePMD'", e);
			exceptionFound = true;
		}
		
		Assert.assertFalse(exceptionFound);
	}
	
	@Test
	public void testWriteSpotBugs() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.SPOT_BUGS;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(TestFilenames.goodSpotBugs));
			Properties config = new SwampProperties(new FileInputStream(TestFilenames.goodConfig));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWriteSpotBugs'", e);
			exceptionFound = true;
		}
		
		Assert.assertFalse(exceptionFound);
	}
	
	@Test
	public void testWriteCheckstyle() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.CHECK_STYLE;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(TestFilenames.goodCheckstyle));
			Properties config = new SwampProperties(new FileInputStream(TestFilenames.goodConfig));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWriteCheckstyle'", e);
			exceptionFound = true;
		}
		
		Assert.assertFalse(exceptionFound);
	}

}
