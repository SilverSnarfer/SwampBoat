package main.java.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import TestingTools.Filenames;
import exceptions.BugReportException;
import pojo.AnalyzerReport;
import pojo.AnalyzerReport.ToolName;
import services.JaxbService;
import services.WriterDispatcherService;
import tools.FileWriterTool;
import wrappers.SwampProperties;

public class TestWriterDispatcherService {
	private static final Logger logger = LogManager.getLogger(TestWriterDispatcherService.class);
	private final JaxbService jaxbService = new JaxbService();
	
	@AfterAll
	public static void cleanup() {
		try {
			FileWriterTool.cleanup(new File(System.getProperty("user.home") + "/swampTest/default"), new File(System.getProperty("user.home") + "/swampTest/custom"));
			logger.info("Cleanup successful. Dont forget to cleanout the trash folder after a while: (" + System.getProperty("user.home") + "\\swampTrash" + ")");
		} catch (IOException e) {
			logger.error("Unable to cleanup.", e);
		}		
	}
	
	
	// ***********************************************
	// ************* DEFAULT LOCATIONS ***************
	// ***********************************************
	@Test
	public void testWriterInstantiation() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.OWASP;
		try {
			Properties config = new SwampProperties(new FileInputStream(Filenames.goodConfig));
			new WriterDispatcherService(config, toolName);	
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWriterInstantiation'", e);
			exceptionFound = true;
		}
		
		Assertions.assertFalse(exceptionFound);
	}
	
	
	@Test
	public void testWriteEmptyOWASP() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.OWASP;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(Filenames.emptyOwasp));
			Properties config = new SwampProperties(new FileInputStream(Filenames.goodConfig));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch(BugReportException b) {
			exceptionFound = true;
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWriteOWASP'", e);
		}
		
		Assertions.assertTrue(exceptionFound);
	}
	
	@Test
	public void testWriteOWASP() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.OWASP;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(Filenames.goodOWASP));
			Properties config = new SwampProperties(new FileInputStream(Filenames.goodConfig));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWriteOWASP'", e);
		}
		
		Assertions.assertFalse(exceptionFound);
	}
	
	@Test
	public void testWritePMD() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.PMD;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(Filenames.goodPMD));
			Properties config = new SwampProperties(new FileInputStream(Filenames.goodConfig));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWritePMD'", e);
			exceptionFound = true;
		}
		
		Assertions.assertFalse(exceptionFound);
	}
	
	@Test
	public void testWriteSpotBugs() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.SPOT_BUGS;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(Filenames.goodSpotBugs));
			Properties config = new SwampProperties(new FileInputStream(Filenames.goodConfig));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWriteSpotBugs'", e);
			exceptionFound = true;
		}
		
		Assertions.assertFalse(exceptionFound);
	}
	
	@Test
	public void testWriteCheckstyle() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.CHECK_STYLE;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(Filenames.goodCheckstyle));
			Properties config = new SwampProperties(new FileInputStream(Filenames.goodConfig));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWriteCheckstyle'", e);
			exceptionFound = true;
		}
		
		Assertions.assertFalse(exceptionFound);
	}

	// ***********************************************
	// *************  CUSTOM LOCATION  ***************
	// ***********************************************
	
	@Test
	public void testWriteOWASPCustom() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.OWASP;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(Filenames.goodOWASP));
			Properties config = new SwampProperties(new FileInputStream(Filenames.goodConfig_custom));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch(BugReportException b) {
			exceptionFound = true;
			logger.error(b);
		} catch (Exception e) {
			exceptionFound = true;
			logger.error("Error during 'TestWriterDispatcherService.testWriteOWASPCustom'", e);
		}
		
		Assertions.assertFalse(exceptionFound);
	}

	@Test
	public void testWriteEmptyOWASPCustom() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.OWASP;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(Filenames.emptyOwasp));
			Properties config = new SwampProperties(new FileInputStream(Filenames.goodConfig_custom));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch(BugReportException b) {
			exceptionFound = true;
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWriteEmptyOWASPCustom'", e);
		}
		
		Assertions.assertTrue(exceptionFound);
	}

	@Test
	public void testWritePMDCustom() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.PMD;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(Filenames.goodPMD));
			Properties config = new SwampProperties(new FileInputStream(Filenames.goodConfig_custom));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWritePMDCustom'", e);
			exceptionFound = true;
		}
		
		Assertions.assertFalse(exceptionFound);
	}

	@Test
	public void testWriteSpotBugsCustom() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.SPOT_BUGS;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(Filenames.goodSpotBugs));
			Properties config = new SwampProperties(new FileInputStream(Filenames.goodConfig_custom));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWriteSpotBugsCustom'", e);
			exceptionFound = true;
		}
		
		Assertions.assertFalse(exceptionFound);
	}

	@Test
	public void testWriteCheckstyleCustom() {
		boolean exceptionFound = false;
		ToolName toolName = ToolName.CHECK_STYLE;
		try {
			AnalyzerReport analyzerReport = jaxbService.unMarshall(new File(Filenames.goodCheckstyle));
			Properties config = new SwampProperties(new FileInputStream(Filenames.goodConfig_custom));
			WriterDispatcherService wds = new WriterDispatcherService(config, toolName);
			wds.writeBugInstancesAndSummary(analyzerReport.getBugInstances(), analyzerReport.getBugSummary());
		} catch (Exception e) {
			logger.error("Error during 'TestWriterDispatcherService.testWriteCheckstyleCustom'", e);
			exceptionFound = true;
		}
		
		Assertions.assertFalse(exceptionFound);
	}
}
