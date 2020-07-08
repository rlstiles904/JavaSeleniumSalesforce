import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.sforce.soap.enterprise.Address;
import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.DeleteResult;
import com.sforce.soap.enterprise.DescribeSObjectResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.Error;
import com.sforce.soap.enterprise.Field;
import com.sforce.soap.enterprise.PicklistEntry;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.UpsertResult;
import com.sforce.soap.enterprise.sobject.Lead;
import com.sforce.soap.enterprise.sobject.Profile;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.User;
import com.sforce.soap.enterprise.sobject.UserRole;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.ConnectionException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class findSalesforceIds {
 
    static final String[] USERNAME       = {"xxxxxxxx@adt.com.practice","xxxxxxxx@adt.com.adttest1","xxxxxxxx@adt.com.adttest2","xxxxxxxx@adt.com.adttest3","xxxxxxxx@adt.com.adthstage","xxxxxxxx@adt.com.adtprf"};
    static final String[] PASSWORD       = {"xxxxxxxxZvYjYOghwL4qHIYoAxx3Ph9F","xxxxxxxxREw46ixVry2jqAMDwIjrbQHru","xxxxxxxxqENPy8jZJMDO3ClTjQUEfL2au","xxxxxxxxFhkmK4G2hdUEONApeo1vuvvpb","xxxxxxxxi38HnGyA96Xi7ebN0imy1XijV","xxxxxxxxWSjSxA6lzaYROZT6ClP7mNGJX"};
    static final String[] LOGINURL       = {"https://adt--practice.cs50.my.salesforce.com","https://adt--adttest1.cs71.my.salesforce.com","https://adt--adttest2.cs50.my.salesforce.com","https://adt--adttest3.cs15.my.salesforce.com","https://adt--adthstage.cs23.my.salesforce.com","https://adt--adtprf.cs93.my.salesforce.com"};
       
    static final String OAUTH2_GRANTSERVICE = "/services/oauth2/token?grant_type=password";
    static final String OAUTH2_CLIENTID     = "3MVG90D5vR7Utjbqtq6jogC1Ae4CcT9KA6GQ87OZ8FrzOPkJzT65_T4f72YY9gzknDHGT_YxKBf8S_Ik2aDv2";
    static final String OAUTH2_CLIENTSECRET = "889F0FA1077AB75B8A4F76F5E6EC68F9C69C5E6FD234B651A94C5BF960A606CD";
    static String USERNAMEx     = "";
    static String PASSWORDx     = "";
    static String LOGINURLx     = "";
    
    private static String SOAP_ENDPOINT = "/services/Soap/c";
    private static String API_VERSION = "/46.0";
    private static String soapUri;
    private static Field[] fields;
    private static String queryFields = "";
    
    static ConnectorConfig config;
    static EnterpriseConnection connection;
    static WebDriver driver = null;
    static String pathToFile = System.getProperty("user.dir");
    static List<List<String>> records = new ArrayList<>();
    static Integer headerLength=0;
    
    public static void main(String[] args) {
		pathToFile = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
		pathToFile = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
		pathToFile = pathToFile + "\\Documents\\SalesforceIds.csv";
		
		ConnectMe2 http = new ConnectMe2();
		System.out.println("Start ConnectMe WebDriver Request");
		http.getRepList();
		System.out.println("End ConnectMe WebDriver Request");      	            
             
 			List<List<String>> header = new ArrayList<>();
 			File csvFile = new File(pathToFile);  
 			if (csvFile.isFile()) {
 				try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
 					String line;
 					String test = null;
 					String[] fields = null;
 					String[] tmpl = null;
 					String[] values = null;
 					while ((line = br.readLine()) != null) {
 						String[] tmp = line.split(","); 						
 						if(tmp[0].indexOf("FirstName") >= 0) {
 							fields = tmp;
 							header.add(Arrays.asList(tmp));
 							headerLength = tmp.length;
 						} else {
 							values = new String[headerLength];
 							for(int x=0; x<tmp.length; x++) {
 								if (tmp[x].length() > 0) {
 									values[x] = tmp[x];
 								} else {
 									values[x] = tmp[x];
 									test = tmp[x];
 								}
 							}
 							for (int x = tmp.length; x < headerLength; x++) {
 								values[x] = test;
 							}
 							 
 							if(values[12].length() == 7) {
 								values = searchPlatforms(values, fields);
 							}
// 						records.add(Arrays.asList(values));
 						}
 					}
 				} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
 			
 				FileWriter csvWriter;
 				try {csvWriter = new FileWriter(pathToFile);				  

 					for (List<String> recordData : header) {  
 						csvWriter.append(String.join(",", recordData));
 						csvWriter.append("\n");
 					}

 					for (List<String> recordData : records) {  
 						csvWriter.append(String.join(",", recordData));
 						csvWriter.append("\n");
 					}
 					csvWriter.flush();  
 					csvWriter.close();  
 				} catch (IOException e1) {e1.printStackTrace();}		
 				
 			}   
             
    }     
 
    private static String[] searchPlatforms(String[] repData, String[] fldData) {
    	QueryResult queryResults = null;
    	QueryResult queryResults2 = null;
    	String roleId = "";
    	String profileId = "";
    	String roleName = "";
    	String profileName = "";
    	String managerUserName = "";
    	String managerId = "";
    	String platform = "";
    	Integer iHld = 0;
    	Boolean iVal = false;
    	User[] usrSobj = new User[1];
    	User usr = new User();
    	User usr2 = new User();
    	UserRole usrRole = new UserRole();
    	Profile prf = new Profile();
    	ConnectorConfig config = new ConnectorConfig();
    	EnterpriseConnection connection = null;
    	ConnectorConfig config2 = new ConnectorConfig();
    	EnterpriseConnection connection2 = null;
    	String[] repData2 = new String[headerLength];
			for(int x=0; x<repData.length; x++) {
				repData2[x] = repData[x];
			}

        System.out.println("Search for Rep Ids...");
        
		for (int x=25; x < fldData.length; x++) {
			queryFields = queryFields + ", "+fldData[x];
		}        
		
    	for (int h=0; h < repData[11].length(); h++) {
    		SaveResult[] saveResults;
    		if (repData[11].substring(h, h+1).indexOf("0") != -1) {
    			platform = "0";
    			soapUri = LOGINURL[0] + SOAP_ENDPOINT + API_VERSION;
    			config = new ConnectorConfig();
    			config.setUsername(USERNAME[0]);
    			config.setPassword(PASSWORD[0]);
    			USERNAMEx = USERNAME[0];
    			PASSWORDx = PASSWORD[0];
    		} else if (repData[11].substring(h, h+1).indexOf("1") != -1) {
    			platform = "1";
    			soapUri = LOGINURL[1] + SOAP_ENDPOINT + API_VERSION;
    			config = new ConnectorConfig();
    			config.setUsername(USERNAME[1]);
    			config.setPassword(PASSWORD[1]);
    			USERNAMEx = USERNAME[1];
    			PASSWORDx = PASSWORD[1];
    		} else if (repData[11].substring(h, h+1).indexOf("2") != -1) {
    			platform = "2";
    	    	soapUri = LOGINURL[2] + SOAP_ENDPOINT + API_VERSION;
    	        config = new ConnectorConfig();
    	        config.setUsername(USERNAME[2]);
    	        config.setPassword(PASSWORD[2]);
    			USERNAMEx = USERNAME[2];
    			PASSWORDx = PASSWORD[2];
    	    } else if (repData[11].substring(h, h+1).indexOf("3") != -1) {
    			platform = "3";
    	    	soapUri = LOGINURL[3] + SOAP_ENDPOINT + API_VERSION;
    	        config = new ConnectorConfig();
    	        config.setUsername(USERNAME[3]);
    	        config.setPassword(PASSWORD[3]);
    			USERNAMEx = USERNAME[3];
    			PASSWORDx = PASSWORD[3];
    	    } else if (repData[11].substring(h, h+1).indexOf("S") != -1) {
    			platform = "S";
    	    	soapUri = LOGINURL[4] + SOAP_ENDPOINT + API_VERSION;
    	        config = new ConnectorConfig();
    	        config.setUsername(USERNAME[4]);
    	        config.setPassword(PASSWORD[4]);
    			USERNAMEx = USERNAME[4];
    			PASSWORDx = PASSWORD[4];
    	    } else if (repData[11].substring(h, h+1).indexOf("P") != -1) {
    			platform = "P";
    	    	soapUri = LOGINURL[5] + SOAP_ENDPOINT + API_VERSION;
    	        config = new ConnectorConfig();
    	        config.setUsername(USERNAME[5]);
    	        config.setPassword(PASSWORD[5]);
    			USERNAMEx = USERNAME[5];
    			PASSWORDx = PASSWORD[5];
    	    }
//    		describeSObjects();

    		config.setAuthEndpoint(soapUri);
                
    		try { 
        	
    			connection = new EnterpriseConnection(config);
    			    			
    			if (repData[12].length() == 7 && repData[2].length() > 0 || repData[12].length() == 7 && repData[0].length() > 0 || repData[12].length() == 7 && repData[1].length() > 0) {
    				  				
    				if (repData[2].length() > 0 && (repData[0].length() > 0 || repData[1].length() > 0)) {
    					if (soapUri.indexOf("adthstage") == -1) {
    						System.out.println("Select UserRoleId, ProfileId, ManagerId, FirstName, LastName, Email "+queryFields+" From User Where email like '%"+repData[2].subSequence(0, repData[2].indexOf("@"))+"%' or FederationIdentifier like '%"+repData[2].subSequence(0, repData[2].indexOf("@"))+"%' limit 5");
    						queryResults = connection.query("Select "+queryFields+" From User Where email like '%"+repData[2].subSequence(0, repData[2].indexOf("@"))+"%' or FederationIdentifier like '%"+repData[2].subSequence(0, repData[2].indexOf("@"))+"%' limit 5");
    					} else {
    						queryResults = connection.query("Select UserRoleId, ProfileId, ManagerId, FirstName, LastName, Email "+queryFields+"  From User Where email like '%"+repData[2].subSequence(0, repData[2].indexOf("@"))+"%' or FederationIdentifier like '%"+repData[2].subSequence(0, repData[2].indexOf("@"))+"%' limit 5");    							
    					}
    				} else if (repData[2].length() > 0 && repData[0].length() == 0 && repData[1].length() == 0) {
    					if (soapUri.indexOf("adthstage") == -1) {
    						queryResults = connection.query("Select UserRoleId, ProfileId, ManagerId, FirstName, LastName, Email "+queryFields+"  From User Where email like '%"+repData[2].subSequence(0, repData[2].indexOf("@"))+"%' or FederationIdentifier like '%"+repData[2].subSequence(0, repData[2].indexOf("@"))+"%' limit 5");        		
    					} else {
    						queryResults = connection.query("Select UserRoleId, ProfileId, ManagerId, FirstName, LastName, Email "+queryFields+"  From User Where email like '%"+repData[2].subSequence(0, repData[2].indexOf("@"))+"%' or FederationIdentifier like '%"+repData[2].subSequence(0, repData[2].indexOf("@"))+"%' limit 5");        		
    					}
    				} else if (repData[2].length() == 0 && repData[0].length() > 0 && repData[1].length() > 0) {
    					if (soapUri.indexOf("adthstage") == -1) {
    						queryResults = connection.query("Select UserRoleId, ProfileId, ManagerId, FirstName, LastName, Email "+queryFields+"  From User Where Name like '%"+repData[0]+"%' or Name like '%"+repData[1]+"%' limit 5");        		
    					} else {
    						queryResults = connection.query("Select UserRoleId, ProfileId, ManagerId, FirstName, LastName, Email "+queryFields+"  From User Where Name like '%"+repData[0]+"%' or Name like '%"+repData[1]+"%' limit 5");        		        							
    					}
    				}   				
    				
    				if (queryResults.getSize() >= 1) {
    					for (int i=0; i < queryResults.getSize();i++){    						
    						usr = (User)queryResults.getRecords()[i];    						    							
    	    				repData2[12] = usr.getId();   	   						
    	    				if (usr.getUserRoleId() != null) {
    	    					queryResults2 = connection.query("Select name From userrole Where id = '"+usr.getUserRoleId()+"' limit 5");
    	    					if (queryResults2.getSize() == 1) {
    	    						usrRole = (UserRole)queryResults2.getRecords()[0];
    	    						repData2[9] = usrRole.getName();
    	    						System.out.println("roleName: " + repData[9]);	
    	    					} 
    	    				}	
    	    				if (usr.getProfileId() != null) {
    	    					queryResults2 = connection.query("Select name From profile Where id = '"+usr.getProfileId()+"' limit 5");
    	    					if (queryResults2.getSize() == 1) {
    	    						prf = (Profile)queryResults2.getRecords()[0];
    	    						repData2[10] = prf.getName();
    	    						System.out.println("profileName: " + repData[10]);	
    	    					}   
    	    				}
    	    				if (usr.getManagerId() != null) {
    	    					queryResults2 = connection.query("Select username From user Where id = '"+usr.getManagerId()+"' limit 5");
    	    					if (queryResults2.getSize() == 1) {
    	    						usr2 = (User)queryResults2.getRecords()[0];
    	    						repData2[7] = usr2.getLastName();
    	    						repData2[6] = usr2.getFirstName();
    	    						repData2[8] = usr2.getEmail();
    	    						System.out.println("managerName: " + repData[7]);	
    	    					} 
    	    				}
    	    				
    	    				
    	    				// loop thru dynamically added fields    					
        					if (fldData.length > 25) {   						
        						for (int x = 25; x < fldData.length; x++) {
        							if (fldData[x].length() > 0 ) {
        								
    									if (fldData[x].indexOf("Id") >= 0)  {repData2[x]=usr.getId();}
    									if (fldData[x].indexOf("Username") >= 0)  {repData2[x]=usr.getUsername();}
    									if (fldData[x].indexOf("LastName") >= 0)  {repData2[x]=usr.getLastName();}
    									if (fldData[x].indexOf("FirstName") >= 0)  {repData2[x]=usr.getFirstName();}
    									if (fldData[x].indexOf("Name") >= 0)  {repData2[x]=usr.getName();}
    									if (fldData[x].indexOf("CompanyName") >= 0)  {repData2[x]=usr.getCompanyName();}
    									if (fldData[x].indexOf("Division") >= 0)  {repData2[x]=usr.getDivision();}
    									if (fldData[x].indexOf("Department") >= 0)  {repData2[x]=usr.getDepartment();}
    									if (fldData[x].indexOf("Title") >= 0)  {repData2[x]=usr.getStreet();}
    									if (fldData[x].indexOf("City") >= 0)  {repData2[x]=usr.getCity();}
    									if (fldData[x].indexOf("State") >= 0)  {repData2[x]=usr.getState();}
    									if (fldData[x].indexOf("PostalCode") >= 0)  {repData2[x]=usr.getPostalCode();}
    									if (fldData[x].indexOf("Country") >= 0)  {repData2[x]=usr.getCountry();}
    									if (fldData[x].indexOf("Latitude") >= 0)  {repData2[x]=usr.getLatitude().toString();}
    									if (fldData[x].indexOf("Longitude") >= 0)  {repData2[x]=usr.getLongitude().toString();}
    									if (fldData[x].indexOf("GeocodeAccuracy") >= 0)  {repData2[x]=usr.getGeocodeAccuracy();}
//    									if (fldData[x].indexOf("Address") >= 0)  {repData2[x]=usr.getAddress();}
    									if (fldData[x].indexOf("Email") >= 0)  {repData2[x]=usr.getEmail();}
    									if (fldData[x].indexOf("EmailPreferencesAutoBcc") >= 0)  {repData2[x]=usr.getEmailPreferencesAutoBcc().toString();}
    									if (fldData[x].indexOf("EmailPreferencesAutoBccStayInTouch") >= 0)  {repData2[x]=usr.getEmailPreferencesAutoBccStayInTouch().toString();}
    									if (fldData[x].indexOf("EmailPreferencesStayInTouchReminder") >= 0)  {repData2[x]=usr.getEmailPreferencesStayInTouchReminder().toString();}
    									if (fldData[x].indexOf("SenderEmail") >= 0)  {repData2[x]=usr.getSenderEmail();}
    									if (fldData[x].indexOf("SenderName") >= 0)  {repData2[x]=usr.getSenderName();}
    									if (fldData[x].indexOf("Signature") >= 0)  {repData2[x]=usr.getSignature();}
    									if (fldData[x].indexOf("StayInTouchSubject") >= 0)  {repData2[x]=usr.getStayInTouchSubject();}
    									if (fldData[x].indexOf("StayInTouchSignature") >= 0)  {repData2[x]=usr.getStayInTouchSignature();}
    									if (fldData[x].indexOf("StayInTouchNote") >= 0)  {repData2[x]=usr.getStayInTouchNote();}
    									if (fldData[x].indexOf("Phone") >= 0)  {repData2[x]=usr.getPhone();}
    									if (fldData[x].indexOf("Fax") >= 0)  {repData2[x]=usr.getFax();}
    									if (fldData[x].indexOf("MobilePhone") >= 0)  {repData2[x]=usr.getMobilePhone();}
    									if (fldData[x].indexOf("Alias") >= 0)  {repData2[x]=usr.getAlias();}
    									if (fldData[x].indexOf("CommunityNickname") >= 0)  {repData2[x]=usr.getCommunityNickname();}
    									if (fldData[x].indexOf("BadgeText") >= 0)  {repData2[x]=usr.getBadgeText();}
    									if (fldData[x].indexOf("IsActive") >= 0)  {repData2[x]=usr.getIsActive().toString();}
    									if (fldData[x].indexOf("TimeZoneSidKey") >= 0)  {repData2[x]=usr.getTimeZoneSidKey();}
    									if (fldData[x].indexOf("UserRoleId") >= 0)  {repData2[x]=usr.getUserRoleId();}
    									if (fldData[x].indexOf("LocaleSidKey") >= 0)  {repData2[x]=usr.getLocaleSidKey();}
    									if (fldData[x].indexOf("ReceivesInfoEmails") >= 0)  {repData2[x]=usr.getReceivesInfoEmails().toString();}
    									if (fldData[x].indexOf("ReceivesAdminInfoEmails") >= 0)  {repData2[x]=usr.getReceivesAdminInfoEmails().toString();}
    									if (fldData[x].indexOf("EmailEncodingKey") >= 0)  {repData2[x]=usr.getEmailEncodingKey();}
    									if (fldData[x].indexOf("ProfileId") >= 0)  {repData2[x]=usr.getProfileId();}
    									if (fldData[x].indexOf("UserType") >= 0)  {repData2[x]=usr.getUserType();}
    									if (fldData[x].indexOf("LanguageLocaleKey") >= 0)  {repData2[x]=usr.getLanguageLocaleKey();}
    									if (fldData[x].indexOf("EmployeeNumber") >= 0)  {repData2[x]=usr.getEmployeeNumber();}
    									if (fldData[x].indexOf("DelegatedApproverId") >= 0)  {repData2[x]=usr.getDelegatedApproverId();}
    									if (fldData[x].indexOf("ManagerId") >= 0)  {repData2[x]=usr.getManagerId();}
    									if (fldData[x].indexOf("LastLoginDate") >= 0)  {repData2[x]=usr.getLastLoginDate().toString();}
    									if (fldData[x].indexOf("LastPasswordChangeDate") >= 0)  {repData2[x]=usr.getLastPasswordChangeDate().toString();}
    									if (fldData[x].indexOf("CreatedDate") >= 0)  {repData2[x]=usr.getCreatedDate().toString();}
    									if (fldData[x].indexOf("CreatedById") >= 0)  {repData2[x]=usr.getCreatedById();}
    									if (fldData[x].indexOf("LastModifiedDate") >= 0)  {repData2[x]=usr.getLastModifiedDate().toString();}
    									if (fldData[x].indexOf("LastModifiedById") >= 0)  {repData2[x]=usr.getLastModifiedById();}
    									if (fldData[x].indexOf("SystemModstamp") >= 0)  {repData2[x]=usr.getSystemModstamp().toString();}
    									if (fldData[x].indexOf("NumberOfFailedLogins") >= 0)  {repData2[x]=usr.getNumberOfFailedLogins().toString();}
    									if (fldData[x].indexOf("OfflineTrialExpirationDate") >= 0)  {repData2[x]=usr.getOfflineTrialExpirationDate().toString();}
    									if (fldData[x].indexOf("OfflinePdaTrialExpirationDate") >= 0)  {repData2[x]=usr.getOfflinePdaTrialExpirationDate().toString();}
    									if (fldData[x].indexOf("UserPermissionsMarketingUser") >= 0)  {repData2[x]=usr.getUserPermissionsMarketingUser().toString();}
    									if (fldData[x].indexOf("UserPermissionsOfflineUser") >= 0)  {repData2[x]=usr.getUserPermissionsOfflineUser().toString();}
    									if (fldData[x].indexOf("UserPermissionsAvantgoUser") >= 0)  {repData2[x]=usr.getUserPermissionsAvantgoUser().toString();}
    									if (fldData[x].indexOf("UserPermissionsCallCenterAutoLogin") >= 0)  {repData2[x]=usr.getUserPermissionsCallCenterAutoLogin().toString();}
    									if (fldData[x].indexOf("UserPermissionsMobileUser") >= 0)  {repData2[x]=usr.getUserPermissionsMobileUser().toString();}
    									if (fldData[x].indexOf("UserPermissionsSFContentUser") >= 0)  {repData2[x]=usr.getUserPermissionsSFContentUser().toString();}
    									if (fldData[x].indexOf("UserPermissionsKnowledgeUser") >= 0)  {repData2[x]=usr.getUserPermissionsKnowledgeUser().toString();}
    									if (fldData[x].indexOf("UserPermissionsInteractionUser") >= 0)  {repData2[x]=usr.getUserPermissionsInteractionUser().toString();}
    									if (fldData[x].indexOf("UserPermissionsSupportUser") >= 0)  {repData2[x]=usr.getUserPermissionsSupportUser().toString();}
    									if (fldData[x].indexOf("UserPermissionsChatterAnswersUser") >= 0)  {repData2[x]=usr.getUserPermissionsChatterAnswersUser().toString();}
    									if (fldData[x].indexOf("UserPreferencesActivityRemindersPopup") >= 0)  {repData2[x]=usr.getUserPreferencesActivityRemindersPopup().toString();}
    									if (fldData[x].indexOf("UserPreferencesEventRemindersCheckboxDefault") >= 0)  {repData2[x]=usr.getUserPreferencesEventRemindersCheckboxDefault().toString();}
    									if (fldData[x].indexOf("UserPreferencesTaskRemindersCheckboxDefault") >= 0)  {repData2[x]=usr.getUserPreferencesTaskRemindersCheckboxDefault().toString();}
    									if (fldData[x].indexOf("UserPreferencesReminderSoundOff") >= 0)  {repData2[x]=usr.getUserPreferencesReminderSoundOff().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisableAllFeedsEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisableAllFeedsEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisableFollowersEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisableFollowersEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisableProfilePostEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisableProfilePostEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisableChangeCommentEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisableChangeCommentEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisableLaterCommentEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisableLaterCommentEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisProfPostCommentEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisProfPostCommentEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesContentNoEmail") >= 0)  {repData2[x]=usr.getUserPreferencesContentNoEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesContentEmailAsAndWhen") >= 0)  {repData2[x]=usr.getUserPreferencesContentEmailAsAndWhen().toString();}
    									if (fldData[x].indexOf("UserPreferencesApexPagesDeveloperMode") >= 0)  {repData2[x]=usr.getUserPreferencesApexPagesDeveloperMode().toString();}
    									if (fldData[x].indexOf("UserPreferencesReceiveNoNotificationsAsApprover") >= 0)  {repData2[x]=usr.getUserPreferencesReceiveNoNotificationsAsApprover().toString();}
    									if (fldData[x].indexOf("UserPreferencesReceiveNotificationsAsDelegatedApprover") >= 0)  {repData2[x]=usr.getUserPreferencesReceiveNotificationsAsDelegatedApprover().toString();}
    									if (fldData[x].indexOf("UserPreferencesHideCSNGetChatterMobileTask") >= 0)  {repData2[x]=usr.getUserPreferencesHideCSNGetChatterMobileTask().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisableMentionsPostEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisableMentionsPostEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisMentionsCommentEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisMentionsCommentEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesHideCSNDesktopTask") >= 0)  {repData2[x]=usr.getUserPreferencesHideCSNDesktopTask().toString();}
    									if (fldData[x].indexOf("UserPreferencesHideChatterOnboardingSplash") >= 0)  {repData2[x]=usr.getUserPreferencesHideChatterOnboardingSplash().toString();}
    									if (fldData[x].indexOf("UserPreferencesHideSecondChatterOnboardingSplash") >= 0)  {repData2[x]=usr.getUserPreferencesHideSecondChatterOnboardingSplash().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisCommentAfterLikeEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisCommentAfterLikeEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisableLikeEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisableLikeEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesSortFeedByComment") >= 0)  {repData2[x]=usr.getUserPreferencesSortFeedByComment().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisableMessageEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisableMessageEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisableBookmarkEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisableBookmarkEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisableSharePostEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisableSharePostEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesEnableAutoSubForFeeds") >= 0)  {repData2[x]=usr.getUserPreferencesEnableAutoSubForFeeds().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisableFileShareNotificationsForApi") >= 0)  {repData2[x]=usr.getUserPreferencesDisableFileShareNotificationsForApi().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowTitleToExternalUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowTitleToExternalUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowManagerToExternalUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowManagerToExternalUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowEmailToExternalUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowEmailToExternalUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowWorkPhoneToExternalUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowWorkPhoneToExternalUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowMobilePhoneToExternalUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowMobilePhoneToExternalUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowFaxToExternalUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowFaxToExternalUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowStreetAddressToExternalUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowStreetAddressToExternalUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowCityToExternalUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowCityToExternalUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowStateToExternalUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowStateToExternalUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowPostalCodeToExternalUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowPostalCodeToExternalUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowCountryToExternalUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowCountryToExternalUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowProfilePicToGuestUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowProfilePicToGuestUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowTitleToGuestUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowTitleToGuestUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowCityToGuestUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowCityToGuestUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowStateToGuestUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowStateToGuestUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowPostalCodeToGuestUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowPostalCodeToGuestUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowCountryToGuestUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowCountryToGuestUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesPipelineViewHideHelpPopover") >= 0)  {repData2[x]=usr.getUserPreferencesPipelineViewHideHelpPopover().toString();}
    									if (fldData[x].indexOf("UserPreferencesDisableEndorsementEmail") >= 0)  {repData2[x]=usr.getUserPreferencesDisableEndorsementEmail().toString();}
    									if (fldData[x].indexOf("UserPreferencesPathAssistantCollapsed") >= 0)  {repData2[x]=usr.getUserPreferencesPathAssistantCollapsed().toString();}
    									if (fldData[x].indexOf("UserPreferencesCacheDiagnostics") >= 0)  {repData2[x]=usr.getUserPreferencesCacheDiagnostics().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowEmailToGuestUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowEmailToGuestUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowManagerToGuestUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowManagerToGuestUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowWorkPhoneToGuestUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowWorkPhoneToGuestUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowMobilePhoneToGuestUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowMobilePhoneToGuestUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowFaxToGuestUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowFaxToGuestUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesShowStreetAddressToGuestUsers") >= 0)  {repData2[x]=usr.getUserPreferencesShowStreetAddressToGuestUsers().toString();}
    									if (fldData[x].indexOf("UserPreferencesLightningExperiencePreferred") >= 0)  {repData2[x]=usr.getUserPreferencesLightningExperiencePreferred().toString();}
    									if (fldData[x].indexOf("UserPreferencesPreviewLightning") >= 0)  {repData2[x]=usr.getUserPreferencesPreviewLightning().toString();}
    									if (fldData[x].indexOf("UserPreferencesHideEndUserOnboardingAssistantModal") >= 0)  {repData2[x]=usr.getUserPreferencesHideEndUserOnboardingAssistantModal().toString();}
    									if (fldData[x].indexOf("UserPreferencesHideLightningMigrationModal") >= 0)  {repData2[x]=usr.getUserPreferencesHideLightningMigrationModal().toString();}
    									if (fldData[x].indexOf("UserPreferencesHideSfxWelcomeMat") >= 0)  {repData2[x]=usr.getUserPreferencesHideSfxWelcomeMat().toString();}
    									if (fldData[x].indexOf("UserPreferencesHideBiggerPhotoCallout") >= 0)  {repData2[x]=usr.getUserPreferencesHideBiggerPhotoCallout().toString();}
    									if (fldData[x].indexOf("UserPreferencesGlobalNavBarWTShown") >= 0)  {repData2[x]=usr.getUserPreferencesGlobalNavBarWTShown().toString();}
    									if (fldData[x].indexOf("UserPreferencesGlobalNavGridMenuWTShown") >= 0)  {repData2[x]=usr.getUserPreferencesGlobalNavGridMenuWTShown().toString();}
    									if (fldData[x].indexOf("UserPreferencesCreateLEXAppsWTShown") >= 0)  {repData2[x]=usr.getUserPreferencesCreateLEXAppsWTShown().toString();}
    									if (fldData[x].indexOf("UserPreferencesFavoritesWTShown") >= 0)  {repData2[x]=usr.getUserPreferencesFavoritesWTShown().toString();}
    									if (fldData[x].indexOf("UserPreferencesRecordHomeSectionCollapseWTShown") >= 0)  {repData2[x]=usr.getUserPreferencesRecordHomeSectionCollapseWTShown().toString();}
    									if (fldData[x].indexOf("UserPreferencesRecordHomeReservedWTShown") >= 0)  {repData2[x]=usr.getUserPreferencesRecordHomeReservedWTShown().toString();}
    									if (fldData[x].indexOf("UserPreferencesFavoritesShowTopFavorites") >= 0)  {repData2[x]=usr.getUserPreferencesFavoritesShowTopFavorites().toString();}
    									if (fldData[x].indexOf("UserPreferencesExcludeMailAppAttachments") >= 0)  {repData2[x]=usr.getUserPreferencesExcludeMailAppAttachments().toString();}
    									if (fldData[x].indexOf("UserPreferencesSuppressTaskSFXReminders") >= 0)  {repData2[x]=usr.getUserPreferencesSuppressTaskSFXReminders().toString();}
    									if (fldData[x].indexOf("UserPreferencesSuppressEventSFXReminders") >= 0)  {repData2[x]=usr.getUserPreferencesSuppressEventSFXReminders().toString();}
    									if (fldData[x].indexOf("UserPreferencesPreviewCustomTheme") >= 0)  {repData2[x]=usr.getUserPreferencesPreviewCustomTheme().toString();}
    									if (fldData[x].indexOf("UserPreferencesHasCelebrationBadge") >= 0)  {repData2[x]=usr.getUserPreferencesHasCelebrationBadge().toString();}
    									if (fldData[x].indexOf("UserPreferencesUserDebugModePref") >= 0)  {repData2[x]=usr.getUserPreferencesUserDebugModePref().toString();}
    									if (fldData[x].indexOf("UserPreferencesSRHOverrideActivities") >= 0)  {repData2[x]=usr.getUserPreferencesSRHOverrideActivities().toString();}
    									if (fldData[x].indexOf("UserPreferencesNewLightningReportRunPageEnabled") >= 0)  {repData2[x]=usr.getUserPreferencesNewLightningReportRunPageEnabled().toString();}
    									if (fldData[x].indexOf("ContactId") >= 0)  {repData2[x]=usr.getContactId();}
    									if (fldData[x].indexOf("AccountId") >= 0)  {repData2[x]=usr.getAccountId();}
    									if (fldData[x].indexOf("CallCenterId") >= 0)  {repData2[x]=usr.getCallCenterId();}
    									if (fldData[x].indexOf("Extension") >= 0)  {repData2[x]=usr.getExtension();}
    									if (fldData[x].indexOf("FederationIdentifier") >= 0)  {repData2[x]=usr.getFederationIdentifier();}
    									if (fldData[x].indexOf("AboutMe") >= 0)  {repData2[x]=usr.getAboutMe();}
    									if (fldData[x].indexOf("FullPhotoUrl") >= 0)  {repData2[x]=usr.getFullPhotoUrl();}
    									if (fldData[x].indexOf("SmallPhotoUrl") >= 0)  {repData2[x]=usr.getSmallPhotoUrl();}
    									if (fldData[x].indexOf("IsExtIndicatorVisible") >= 0)  {repData2[x]=usr.getIsExtIndicatorVisible().toString();}
    									if (fldData[x].indexOf("OutOfOfficeMessage") >= 0)  {repData2[x]=usr.getOutOfOfficeMessage();}
    									if (fldData[x].indexOf("MediumPhotoUrl") >= 0)  {repData2[x]=usr.getMediumPhotoUrl();}
    									if (fldData[x].indexOf("DigestFrequency") >= 0)  {repData2[x]=usr.getDigestFrequency();}
    									if (fldData[x].indexOf("DefaultGroupNotificationFrequency") >= 0)  {repData2[x]=usr.getDefaultGroupNotificationFrequency();}
    									if (fldData[x].indexOf("LastViewedDate") >= 0)  {repData2[x]=usr.getLastViewedDate().toString();}
    									if (fldData[x].indexOf("LastReferencedDate") >= 0)  {repData2[x]=usr.getLastReferencedDate().toString();}
    									if (fldData[x].indexOf("BannerPhotoUrl") >= 0)  {repData2[x]=usr.getBannerPhotoUrl();}
    									if (fldData[x].indexOf("SmallBannerPhotoUrl") >= 0)  {repData2[x]=usr.getSmallBannerPhotoUrl();}
    									if (fldData[x].indexOf("MediumBannerPhotoUrl") >= 0)  {repData2[x]=usr.getMediumBannerPhotoUrl();}
    									if (fldData[x].indexOf("IsProfilePhotoActive") >= 0)  {repData2[x]=usr.getIsProfilePhotoActive().toString();}
    									if (fldData[x].indexOf("IndividualId") >= 0)  {repData2[x]=usr.getIndividualId();}
    									if (fldData[x].indexOf("ASAP_Agent_Id__c") >= 0)  {repData2[x]=usr.getASAP_Agent_Id__c().toString();}
    									if (fldData[x].indexOf("Business_Unit__c") >= 0)  {repData2[x]=usr.getBusiness_Unit__c();}
    									if (fldData[x].indexOf("DeviceID__c") >= 0)  {repData2[x]=usr.getDeviceID__c();}
    									if (fldData[x].indexOf("DevicePhoneNumber__c") >= 0)  {repData2[x]=usr.getDevicePhoneNumber__c();}
    									if (fldData[x].indexOf("IncomingManager__c") >= 0)  {repData2[x]=usr.getIncomingManager__c();}
    									if (fldData[x].indexOf("User_Status__c") >= 0)  {repData2[x]=usr.getUser_Status__c();}
    									if (fldData[x].indexOf("RegionNumber__c") >= 0)  {repData2[x]=usr.getRegionNumber__c();}
    									if (fldData[x].indexOf("AreaNumber__c") >= 0)  {repData2[x]=usr.getAreaNumber__c();}
    									if (fldData[x].indexOf("DistrictNumber__c") >= 0)  {repData2[x]=usr.getDistrictNumber__c();}
    									if (fldData[x].indexOf("TownNumber__c") >= 0)  {repData2[x]=usr.getTownNumber__c();}
    									if (fldData[x].indexOf("Count__c") >= 0)  {repData2[x]=usr.getCount__c().toString();}
    									if (fldData[x].indexOf("EmployeeNumber__c") >= 0)  {repData2[x]=usr.getEmployeeNumber__c();}
    									if (fldData[x].indexOf("Qualification__c") >= 0)  {repData2[x]=usr.getQualification__c();}
    									if (fldData[x].indexOf("Region_Id__c") >= 0)  {repData2[x]=usr.getRegion_Id__c();}
    									if (fldData[x].indexOf("StartDateinCurrentJob__c") >= 0)  {repData2[x]=usr.getStartDateinCurrentJob__c().toString();}
    									if (fldData[x].indexOf("Chatter_Champion__c") >= 0)  {repData2[x]=usr.getChatter_Champion__c().toString();}
    									if (fldData[x].indexOf("Terms_and_Conditions_Accepted__c") >= 0)  {repData2[x]=usr.getTerms_and_Conditions_Accepted__c().toString();}
    									if (fldData[x].indexOf("scpq__CPQUserKey__c") >= 0)  {repData2[x]=usr.getScpq__CPQUserKey__c();}
    									if (fldData[x].indexOf("scpq__CurrentInCPQSystem__c") >= 0)  {repData2[x]=usr.getScpq__CurrentInCPQSystem__c().toString();}
    									if (fldData[x].indexOf("scpq__LastCPQSyncDate__c") >= 0)  {repData2[x]=usr.getScpq__LastCPQSyncDate__c().toString();}
    									if (fldData[x].indexOf("scpq__LastCPQSyncSuccessful__c") >= 0)  {repData2[x]=usr.getScpq__LastCPQSyncSuccessful__c().toString();}
    									if (fldData[x].indexOf("Address_Validation__c") >= 0)  {repData2[x]=usr.getAddress_Validation__c().toString();}
    									if (fldData[x].indexOf("Commission_ID__c") >= 0)  {repData2[x]=usr.getCommission_ID__c();}
    									if (fldData[x].indexOf("State_License__c") >= 0)  {repData2[x]=usr.getState_License__c();}
    									if (fldData[x].indexOf("isMMBLookupSearch__c") >= 0)  {repData2[x]=usr.getIsMMBLookupSearch__c().toString();}
    									if (fldData[x].indexOf("Approver_Level__c") >= 0)  {repData2[x]=usr.getApprover_Level__c();}
    									if (fldData[x].indexOf("ChatterExcluded__c") >= 0)  {repData2[x]=usr.getChatterExcluded__c().toString();}
    									if (fldData[x].indexOf("CPQEnabled__c") >= 0)  {repData2[x]=usr.getCPQEnabled__c();}
    									if (fldData[x].indexOf("DelegatedEmail__c") >= 0)  {repData2[x]=usr.getDelegatedEmail__c();}
    									if (fldData[x].indexOf("Profile_Type__c") >= 0)  {repData2[x]=usr.getProfile_Type__c();}
    									if (fldData[x].indexOf("User_License__c") >= 0)  {repData2[x]=usr.getUser_License__c();}
    									if (fldData[x].indexOf("Hierarchy_Created__c") >= 0)  {repData2[x]=usr.getHierarchy_Created__c().toString();}
    									if (fldData[x].indexOf("Agent_Type__c") >= 0)  {repData2[x]=usr.getAgent_Type__c();}
    									if (fldData[x].indexOf("SMS_Enabled_Last_Updated__c") >= 0)  {repData2[x]=usr.getSMS_Enabled_Last_Updated__c().toString();}
    									if (fldData[x].indexOf("SMS_Enabled__c") >= 0)  {repData2[x]=usr.getSMS_Enabled__c().toString();}
    									if (fldData[x].indexOf("License_Expiration_date__c") >= 0)  {repData2[x]=usr.getLicense_Expiration_date__c().toString();}
    									if (fldData[x].indexOf("Manager_Name__c") >= 0)  {repData2[x]=usr.getManager_Name__c();}
    									if (fldData[x].indexOf("eContractEligible__c") >= 0)  {repData2[x]=usr.getEContractEligible__c().toString();}
    									if (fldData[x].indexOf("Profile_Name__c") >= 0)  {repData2[x]=usr.getProfile_Name__c();}
    									if (fldData[x].indexOf("ExtraSkills__c") >= 0)  {repData2[x]=usr.getExtraSkills__c();}
    									if (fldData[x].indexOf("Rep_Team__c") >= 0)  {repData2[x]=usr.getRep_Team__c();}
    									if (fldData[x].indexOf("CheckMyHierarchy__c") >= 0)  {repData2[x]=usr.getCheckMyHierarchy__c();}
    									if (fldData[x].indexOf("SimpleSell__c") >= 0)  {repData2[x]=usr.getSimpleSell__c().toString();}
    									if (fldData[x].indexOf("GNCC__CC_EnvironmentUrl__c") >= 0)  {repData2[x]=usr.getGNCC__CC_EnvironmentUrl__c();} 
        								
        							}
        						}
        					}
        					repData2[0] = usr.getFirstName();
        					repData2[1] = usr.getLastName();
        					repData2[2] = usr.getEmail();      
        					repData2[11] = platform;

//        					usr.setAlias(repData[2].substring(0, 5)+"ADT");
//        					usr.setUsername(repData[2]+config.getUsername().substring(config.getUsername().lastIndexOf(".")));
//        					usr.setCommunityNickname(repData[2].substring(0, repData[2].indexOf("@")));
//        					usr.setFederationIdentifier(repData[2].substring(0, repData[2].indexOf("@")+1) + repData[2].substring(repData[2].indexOf("@")+1).toUpperCase());
//        					usr.setEmployeeNumber__c(repData[17]);    	    				
    	    				
        					records.add(Arrays.asList(repData2));
        					repData2 = new String[headerLength];
        					for(int x=0; x<repData.length; x++) {
        						repData2[x] = repData[x];
        					}
    	    				System.out.println("Id: " + usr.getId() + " - Name: "+ usr.getFirstName()+" "+ usr.getLastName()+"  username: "+ usr.getUsername()+"  Email: "+ usr.getEmail()+"  userroleid: "+ usr.getUserRoleId()+"  profileid: "+ usr.getProfileId()+"  businessunit: "+ usr.getBusiness_Unit__c()+"  addressvalidation: "+ usr.getAddress_Validation__c()+"  ismmblook: "+ usr.getIsMMBLookupSearch__c()+"  cpqenabled: "+ usr.getCPQEnabled__c()+"  currentincpq: "+ usr.getScpq__CurrentInCPQSystem__c());    					   						
    					}     						
    				} else {repData[12] = "NotFound";}
    			
		
    			}	
    			
//    			if (repData[12].length() == 7) {	   					
//    					return repData;
//    			}
    		} catch (ConnectionException e) {e.printStackTrace();}
    	}

        try {
            connection.logout();
             System.out.println("Logged out.");
        } catch (ConnectionException ce) {
                 ce.printStackTrace();
        }
    	
        return repData;
    }      
 
    
    public static void describeSObjects() 
    {
    	ConnectorConfig config3 = new ConnectorConfig();
    	EnterpriseConnection connection3 = null;
        System.out.println("\n_______________ Get User Object Fields _______________");
        System.out.println(soapUri);
         
        try {
//         	soapUri = LOGINURL[0] + SOAP_ENDPOINT + API_VERSION;
//        	config3 = new ConnectorConfig();
        	config3.setUsername(USERNAMEx);
        	config3.setPassword(PASSWORDx);       	
        	config3.setAuthEndpoint(soapUri);
        	connection3 = new EnterpriseConnection(config3);
              // run the different examples
              DescribeSObjectResult dsor = connection3.describeSObject("User");
              Field[] fields = dsor.getFields();
//              if (dsor.isActivateable()) System.out.println("\tActivateable");
                      
              // Iterate through the fields to get properties for each field
              for(int j=0;j < fields.length; j++) {  
            	  queryFields = queryFields + fields[j].getName() + ", ";
            	  Field field = fields[j];
 
                  System.out.println("\t\t\t\t\t\t\t\t\tif (fldData[x].indexOf(\"" +field.getName()+ "\") >= 0)  {usr.set" + field.getName()+"(repData[x]);}");
            	  
//                 System.out.println("\tField: " + field.getName());
//                  System.out.println("\t\tLabel: " + field.getLabel());
//                  if (field.isCustom()) System.out.println("\t\tThis is a custom field.");
//                  System.out.println("\t\tDepHid: " + field.getDeprecatedAndHidden());
//                  System.out.println("\t\tType: " + field.getType());
//                  if (field.getLength() > 0) System.out.println("\t\tLength: " + field.getLength());
//                  if (field.getPrecision() > 0) System.out.println("\t\tPrecision: " + field.getPrecision());
                          
                  // Determine whether this is a picklist field
                  if (field.getType().toString().toUpperCase() == "PICKLIST") {                            
                	  // Determine whether there are picklist values
                      PicklistEntry[] picklistValues = field.getPicklistValues();
                      if (picklistValues != null && picklistValues[0] != null) {
//                    	  System.out.println("\t\tPicklist values = ");
                          for (int k = 0; k < picklistValues.length; k++) {
//                        	  System.out.println("\t\t\tItem: " + picklistValues[k].getLabel());
                          }
                      }
                  }

                  // Determine whether this is a reference field
                  if (field.getType().toString().toUpperCase() == "REFERENCE") {                            
                	  // Determine whether this field refers to another object
                      String[] referenceTos = field.getReferenceTo();
                      if (referenceTos != null && referenceTos[0] != null) {
                    	  System.out.println("\t\tField references the following objects:");
                          for (int k = 0; k < referenceTos.length; k++) {
//                        	  System.out.println("\t\t\t" + referenceTos[k]);
                          }             
                     }
                  }
              }
              queryFields = queryFields.substring(0, queryFields.lastIndexOf(","));
        } catch (ConnectionException e1) {
            e1.printStackTrace();
        } 
        try {
        	connection3.logout();
            	System.out.println("Logged out.");
      	} catch (ConnectionException ce) {ce.printStackTrace();}
    }      
    
}
