package writers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import pojo.BugInstance;
import services.WriterDispatcherService;
import tools.FileWriterTool;
/**
 * Writer for SpotBugs report type. Creates a report csv and an HTML page detailing the resolutions suggested for each bug
 * @author Aaron.Hayward
 *
 */
public class SpotBugsWriter implements Writeable{
	private static final String BR = "<br/>";
	private static final String HTML_FILENAME = "spot_bugs_resolutions.html";
	
	
	
	private final File htmlFile;
	private FileWriter htmlWriter;
	
	public SpotBugsWriter(File bugReport) throws IOException{
		htmlFile = new File(bugReport.getParent() + "/" + HTML_FILENAME);
	}
	
	
	
	
	public void write(List<BugInstance> bugInstances, WriterDispatcherService dispatcherState) throws IOException{
		FileWriter csvWriter = new FileWriter(dispatcherState.getBugReportFile());;
		htmlWriter = new FileWriter(htmlFile);
		csvWriter.append(dispatcherState.getCsvReportHeader() + WriterDispatcherService.NEW_LINE);
		
		for (BugInstance bugInstance : bugInstances) {
			
			csvWriter.append(csvSafe(bugInstance.getId()) + WriterDispatcherService.DELIMITER);
			
			
			if(!bugInstance.getBugLocations().getLocations().isEmpty()) {
				csvWriter.append(FileWriterTool.buildLocationString(bugInstance.getBugLocations().getLocations(), dispatcherState) + WriterDispatcherService.DELIMITER);
			}
			
			csvWriter.append(csvSafe(bugInstance.getBugGroup()) + WriterDispatcherService.DELIMITER);
			csvWriter.append(csvSafe(bugInstance.getBugCode()) + WriterDispatcherService.DELIMITER);
			csvWriter.append(csvSafe(bugInstance.getBugSeverity()) + WriterDispatcherService.DELIMITER);

			csvWriter.append(csvSafe(bugInstance.getBugRank()) + WriterDispatcherService.DELIMITER);
			csvWriter.append(csvSafe(bugInstance.getBugMessage()) + WriterDispatcherService.DELIMITER);
			csvWriter.append(csvSafe(appendHtmlAndReturnPath(bugInstance)) + WriterDispatcherService.NEW_LINE);


		}
		csvWriter.flush();
		csvWriter.close();
		htmlWriter.flush();
		htmlWriter.close();
			
		
	}
	
	//appends to the html file belonging to the instance of this class, and returns the path of the file.
	public String appendHtmlAndReturnPath(BugInstance bugInstance) throws IOException{

		try {

			htmlWriter.append("<div style=\"background-color:#bfbfbf;\">ID:\t\t" + bugInstance.getId() + BR);
			htmlWriter.append("Bug Code:\t" + bugInstance.getBugCode() + BR);
			htmlWriter.append("Bug Group:\t" + bugInstance.getBugGroup() + BR);
			htmlWriter.append("Severity:\t" + bugInstance.getBugSeverity() + BR);
			htmlWriter.append("Message:\t" + bugInstance.getBugMessage() + BR);
			htmlWriter.append("Resolution:" + BR + bugInstance.getResolutionSuggestion() + "</div>");
		} catch (IOException e) {
			htmlWriter.flush();
			htmlWriter.close();
			throw e;
			
		}
		
		return htmlFile.getAbsolutePath();
	}
	
	private String csvSafe(String str) {
		
		if(str == null) {
			return "";
		}
		
		return "\"" +str.trim() + "\"";
	}

}
