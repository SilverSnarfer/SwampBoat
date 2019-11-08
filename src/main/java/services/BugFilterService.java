/**
 * 
 */
package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import exceptions.BugFilterException;
import pojo.BugInstance;

/**
 * @author Aaron.Hayward
 * Responsible for filtering certain bug groups/categories/etc based on the keywords provided in filter.properties.
 */
public class BugFilterService {

	
	public enum ListType {
		BLACKLIST,
		WHITELIST
	}
	private final Properties filterSettings;
	private ListType listType;
	
	public BugFilterService(Properties filterSettings) throws BugFilterException{
		this.filterSettings = filterSettings;
		determineListType();		
	}
	
	/**
	 * Checks each {@code bugInstance} for blacklisted/whitelisted (based on preference) keywords as well as severity levels. Returns a filtered list of {@code BugInstance}s that satisfy the filter criteria.
	 * @param bugInstances
	 * @return {@code List<BugInstance>}
	 */
	public List<BugInstance> filter(List<BugInstance> bugInstances) {
		List<BugInstance> filteredList = new ArrayList<>();
		for(BugInstance bugInstance: bugInstances) {
			boolean excludeInstance = false;
			for(Object setting: filterSettings.keySet()) {
				String settingKey = (String)setting;
				String settingValue = filterSettings.getProperty(settingKey);
				if(this.listType == ListType.BLACKLIST) {
					if(settingKey.equalsIgnoreCase("blacklist.excludedClassNamePhrases")) {
						excludeInstance = checkIfUnwanted(settingValue, bugInstance.getClassName()) ? true : false;
						
					 } else if(settingKey.equalsIgnoreCase("blacklist.excludedBugGroups")) {
						 excludeInstance = checkIfUnwanted(settingValue, bugInstance.getBugGroup()) ? true : false;
						 
					 } else if(settingKey.equalsIgnoreCase("blacklist.excludedBugCodes")) {
						 excludeInstance = checkIfUnwanted(settingValue, bugInstance.getBugCode()) ? true : false;
					 }		 
				} else {
					if(settingKey.equalsIgnoreCase("whitelist.includedClassNamePhrases")) {
						excludeInstance = !(checkIfUnwanted(settingValue, bugInstance.getClassName()) ? true : false);
						
					 } else if(settingKey.equalsIgnoreCase("whitelist.includedBugGroups")) {
						 excludeInstance = !(checkIfUnwanted(settingValue, bugInstance.getBugGroup()) ? true : false);
						 
					 } else if(settingKey.equalsIgnoreCase("whitelist.includedBugCodes")) {
						 excludeInstance = !(checkIfUnwanted(settingValue, bugInstance.getBugCode()) ? true : false);
					 }		 
				}
				if(excludeInstance) {
					break;
				}	
			}
			
			if(filterSettings.getProperty("range.minSeverity") != null && filterSettings.getProperty("range.maxSeverity") != null) {
				int bugSeverity = Integer.valueOf(bugInstance.getBugSeverity());
				int min = Integer.valueOf(filterSettings.getProperty("range.minSeverity"));
				int max = Integer.valueOf(filterSettings.getProperty("range.maxSeverity"));
			
				if(bugSeverity < min || bugSeverity > max) {
					excludeInstance = true;
				}
			}
			
			if(!excludeInstance) {
				filteredList.add(bugInstance);
			}
		}
		
		return filteredList;
	}
	
	/**
	 * returns true if {@code toFilter} is found in one of the {@code filterValues}
	 * @param filterValues
	 * @param toFilter
	 * @return
	 */
	private boolean checkIfUnwanted(String filterValues, String toFilter) {
		if(filterValues.contains(",")) {
			for(String filterWord: filterValues.split(",")) {
				if(toFilter.toLowerCase(java.util.Locale.ENGLISH).contains(filterWord.toLowerCase(java.util.Locale.ENGLISH))) {
					return true;
				}	
			}	
		} else {
			if(toFilter.toLowerCase(java.util.Locale.ENGLISH).contains(filterValues.toLowerCase(java.util.Locale.ENGLISH))) {
				return true;
			}	
		}
				
		return false;
	}
	
	
	/**
	 * sets the listType enum to either 'whitelist' or 'blacklist' based on the filter config file.
	 * @throws BugFilterException
	 */
	private void determineListType() throws BugFilterException{
		ListType listType = null;
		for(Object setting: filterSettings.keySet()) {
			if(((String)setting).contains("whitelist.")) {
				if(listType == ListType.BLACKLIST) {
					throw new BugFilterException("Error: use either blacklist or whitelist in the settings file. you cannot use both");
				} else {
					listType = ListType.WHITELIST;
				}
				
			} else if(((String)setting).contains("blacklist.")) {
				if(listType == ListType.WHITELIST) {
					throw new BugFilterException("Error: use either blacklist or whitelist in the settings file. you cannot use both");
				} else {
					listType = ListType.BLACKLIST;
				}
			}
		}
		this.listType = listType;
	}
	
	

}
