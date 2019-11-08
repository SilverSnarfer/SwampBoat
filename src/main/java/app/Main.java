/**
 * 
 */
package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exceptions.BugFilterException;
import exceptions.BugReportException;
import exceptions.BugSummaryException;
import exceptions.CSVHeaderException;
import exceptions.ConfigFileException;
import exceptions.NullReportException;
import exceptions.XMLSourceException;
import pojo.AnalyzerReport;
import pojo.BugInstance;
import services.BugFilterService;
import services.JaxbService;
import services.WriterDispatcherService;
import tools.SwampProperties;

/**
 * @author Aaron.Hayward
 *
 */
public class Main {

	private static final JaxbService jaxbService = new JaxbService();
	private static final Logger logger = LogManager.getLogger(Main.class);
	public static void main(String[] args) {
			Properties config = new SwampProperties();
			Properties filterSettings = new Properties();
			String toUnmarshall = null;
			List<File> files = new ArrayList<>();
			boolean isBulk = false;

			try {
				config.load(new FileInputStream("src/main/resources/settings/config.properties"));
				filterSettings.load(new FileInputStream("src/main/resources/settings/filter.properties"));
			
			
			
				if(nullSafe(config.getProperty("bulkXmlFolder")).equals("")) {
					toUnmarshall = config.getProperty("xmlLocation");
				} else {
					isBulk = true;
					toUnmarshall = config.getProperty("bulkXmlFolder");
				}
				
				if(isBulk) {
					File dir = new File(toUnmarshall);
					for(File file: dir.listFiles()) {
						files.add(file);
					}
					logger.info("Processing " + files.size() + " xml files ('bulkXmlFolder' option)...");
	
				} else {
					files.add(new File(toUnmarshall));
					logger.info("Processing single xml file ('xmlLocation' option)...");
				}
				
				
				for(File file: files) {
					
					try {
						AnalyzerReport analyzerReport = jaxbService.unMarshall(file);
						WriterDispatcherService csvWriter = new WriterDispatcherService(config, analyzerReport.getToolName());
						BugFilterService bugFilter = new BugFilterService(filterSettings);
						List<BugInstance> bugInstances = analyzerReport.getBugInstances();

						if(filterSettings.size() > 0) {
							logger.info("filtering...");
							bugInstances = bugFilter.filter(bugInstances);
						}
						
						csvWriter.writeBugInstancesAndSummary(bugInstances, analyzerReport.getBugSummary());
						logger.info("Finished " + file.getAbsolutePath());
					} catch (BugFilterException | JAXBException | ConfigFileException | 
							IOException | XMLSourceException | CSVHeaderException | 
							NullReportException | BugSummaryException | BugReportException e) {
						logger.error("",e);
						break;
					}
				}
			
			} catch (IOException e) {
				logger.error("",e);
			}
			
			
			logger.info("Done");
	}
	
	private static String nullSafe(String str) {
		if(str == null) {
			return "";
		}
		return str.trim();
	}
		
}
