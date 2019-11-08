/**
 * 
 */
package writers;

import java.io.FileWriter;
import java.io.IOException;

import exceptions.NullReportException;
import pojo.BugCategory;
import pojo.BugSummary;
import services.WriterDispatcherService;

/**
 * @author Aaron.Hayward
 *
 */
public class SummaryWriter {

	
	/**
	 * writes the bugSummary csv for any given report
	 * @param bugSummary
	 * @param dispatcherState
	 * @param summaryHeader
	 * @throws IOException
	 * @throws NullReportException
	 */
	public void writeBugSummary(BugSummary bugSummary, WriterDispatcherService dispatcherState, String summaryHeader) throws IOException, NullReportException{
			
			
		if(bugSummary == null || bugSummary.getBugCategory() == null) {
			throw new NullReportException();
		}
		
		FileWriter fileWriter = new FileWriter(dispatcherState.getBugSummaryFile());
	
		fileWriter.append(summaryHeader + WriterDispatcherService.NEW_LINE);
		for(BugCategory category: bugSummary.getBugCategory()) {
			fileWriter.append(csvSafe(category.getGroup()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(category.getCode()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(category.getCount()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(category.getBytes()) + WriterDispatcherService.NEW_LINE);
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
