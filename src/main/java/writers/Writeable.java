package writers;

import java.io.IOException;
import java.util.List;

import pojo.BugInstance;
import services.WriterDispatcherService;

/**
 * Interface implemented by writers that need to write reports for full xml bug reports (No summary)
 * @author Aaron.Hayward
 *
 */
public interface Writeable {

	/**
	 * Creates a csv version of the list of bug instances. Based on the report type, this method could also produce a separate HTML file (Like SpotBugs) or a sub-directory of messages (Like Owasp) 
	 * @param bugInstances
	 * @param dispatcherState
	 * @throws IOException
	 */
	public void write(List<BugInstance> bugInstances, WriterDispatcherService dispatcherState) throws IOException;
}
