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
			

			try {
				Properties config = new SwampProperties(new FileInputStream("src/main/resources/settings/config.properties"));
				Properties filterSettings = new SwampProperties(new FileInputStream("src/main/resources/settings/filter.properties"));
				String toUnmarshall = null;
				List<File> files = new ArrayList<>();
				boolean isBulk = false;

			
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
						List<BugInstance> bugInstances = analyzerReport.getBugInstances();
						WriterDispatcherService csvWriter = new WriterDispatcherService(config, analyzerReport.getToolName());
						BugFilterService bugFilter = new BugFilterService(filterSettings);

						if(filterSettings.size() > 0 && bugInstances != null) {
							logger.info("filtering...");
							bugInstances = bugFilter.filter(bugInstances);
						}
						 
						
						csvWriter.writeBugInstancesAndSummary(bugInstances, analyzerReport.getBugSummary());
						logger.info("Finished " + file.getAbsolutePath());
					} catch (BugFilterException | JAXBException | ConfigFileException | 
							IOException | XMLSourceException | CSVHeaderException | 
							BugSummaryException | BugReportException e) {
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
