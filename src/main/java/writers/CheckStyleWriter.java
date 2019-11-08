/**
 * 
 */
package writers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import pojo.BugInstance;
import services.WriterDispatcherService;
import tools.FileWriterTool;

/**
 * @author Aaron.Hayward
 *
 */
public class CheckStyleWriter implements Writeable{	
	public void write(List<BugInstance> bugInstances, WriterDispatcherService dispatcherState) throws IOException {
		FileWriter fileWriter = new FileWriter(dispatcherState.getBugReportFile());
		
			
		fileWriter.append(dispatcherState.getCsvReportHeader() + WriterDispatcherService.NEW_LINE);
		
		for (BugInstance bugInstance : bugInstances) {
			
			fileWriter.append(csvSafe(bugInstance.getId()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(bugInstance.getClassName()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(FileWriterTool.buildLocationString(bugInstance.getBugLocations().getLocations(), dispatcherState) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(bugInstance.getBugGroup()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(bugInstance.getBugCode()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(bugInstance.getBugSeverity()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(bugInstance.getBugMessage()) + WriterDispatcherService.NEW_LINE);
			
		}
		
		fileWriter.flush();
		fileWriter.close();
			
	}
	private String csvSafe(String str) {
		
		if(str == null) {
			return "";
		}
		
		return "\"" +str.trim() + "\"";
	}
	
}
