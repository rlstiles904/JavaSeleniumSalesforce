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

public class createSalesforceIds {
 
    static final String[] USERNAME       = {"xxxxxxxx@adt.com.practice","xxxxxxxx@adt.com.adttest1","xxxxxxxx@adt.com.adttest2","xxxxxxxx@adt.com.adttest3","xxxxxxxx@adt.com.adthstage","xxxxxxxx@adt.com.adtprf"};
    static final String[] PASSWORD       = {"xxxxxxxxZvYjYOghwL4qHIYoAxx3Ph9F","xxxxxxxxREw46ixVry2jqAMDwIjrbQHru","xxxxxxxxqENPy8jZJMDO3ClTjQUEfL2au","xxxxxxxxFhkmK4G2hdUEONApeo1vuvvpb","xxxxxxxxi38HnGyA96Xi7ebN0imy1XijV","xxxxxxxxWSjSxA6lzaYROZT6ClP7mNGJX"};
    static final String[] LOGINURL       = {"https://adt--practice.cs50.my.salesforce.com","https://adt--adttest1.cs71.my.salesforce.com","https://adt--adttest2.cs50.my.salesforce.com","https://adt--adttest3.cs15.my.salesforce.com","https://adt--adthstage.cs23.my.salesforce.com","https://adt--adtprf.cs93.my.salesforce.com"};
    
    static final String USERNAME_1     = "xxxxxxxx@adt.com.adttest1";
    static final String PASSWORD_1     = "xxxxxxxxeVkHEbJLJyo6pt3Mi4y00quq";
    static final String LOGINURL_1     = "https://adt--adttest1.cs71.my.salesforce.com";
   
    static final String USERNAME_2     = "xxxxxxxx@adt.com.adttest2";
    static final String PASSWORD_2     = "xxxxxxxxW9idqnciHMUL2vKNMatvtgt1J";
    static final String LOGINURL_2     = "https://adt--adttest2.cs50.my.salesforce.com";

    static final String USERNAME_3     = "xxxxxxxx@adt.com.adttest3";
    static final String PASSWORD_3     = "xxxxxxxxUq4nMj8pRm4AOHYu0M1Z04ln";
    static final String LOGINURL_3     = "https://adt--adttest3.cs15.my.salesforce.com";
    
    static final String USERNAME_S     = "xxxxxxxx@adt.com.adthstage";
    static final String PASSWORD_S     = "xxxxxxxxV2c7OaE4eGCLQ4e386bngbiud";
    static final String LOGINURL_S     = "https://adt--adthstage.cs23.my.salesforce.com";
 
    static final String USERNAME_P     = "xxxxxxxx@adt.com.adtprf";
    static final String PASSWORD_P     = "xxxxxxxxAo7sNVUGsRcLWswXyQOu4m2MA";
    static final String LOGINURL_P     = "https://adt--adtprf.cs93.my.salesforce.com";
   
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
    
    public static void main(String[] args) {
		pathToFile = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
		pathToFile = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
		pathToFile = pathToFile + "\\Documents\\SalesforceIds.csv";
		
		ConnectMe2 http = new ConnectMe2();
		System.out.println("Start ConnectMe WebDriver Request");
		http.getRepList();
		System.out.println("End ConnectMe WebDriver Request");   
    	
//    	soapUri = LOGINURL + SOAP_ENDPOINT + API_VERSION;
//        ConnectorConfig config = new ConnectorConfig();
//        config.setUsername(USERNAME);
//        config.setPassword(PASSWORD);
//        config.setAuthEndpoint(soapUri);
 
//        try {
//        	connection = new EnterpriseConnection(config);

//        	  connection = Connector.newConnection(config);
               // display some current settings
//              System.out.println("Auth EndPoint: "+config.getAuthEndpoint());
//              System.out.println("Service EndPoint: "+config.getServiceEndpoint());
//              System.out.println("Username: "+config.getUsername());
//              System.out.println("SessionId: "+config.getSessionId());
 
              // run the different examples
//			testConnections();
//             describeSObjects();
//             queryLeads();                   // Query Leads from Salesforce
//             createLeads();                  // Create Leads in Salesforce
//             updateLeads();                  // Update Leads in Salesforce
//             deleteLeads();                  // Delete Leads in Salesforce
             
             
 			List<List<String>> records = new ArrayList<>();
 			List<List<String>> records2 = new ArrayList<>();
 			File csvFile = new File(pathToFile);  
 			if (csvFile.isFile()) {
 				try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
 					String line;
 					String[] fields = null;
 					while ((line = br.readLine()) != null) {
 						String[] values = line.split(",");
 						if(values.length == 12) {
 							values = new String[] {values[0],values[1],values[2],values[3],values[4],values[5],values[6],values[7],values[8],values[9],values[10],values[11],"","","","","","","","","","","","",""};
 						} 
 						if(values[0].indexOf("FirstName") >= 0) {
 							fields = values;
 							records2.add(Arrays.asList(values));
 						} else {
 							if(values[12].length() == 7) {
 								values = validateMirrorRep(values, fields);
 							}
 							records.add(Arrays.asList(values));

 						}
 					}
 				} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
 			
 				FileWriter csvWriter;
 				try {csvWriter = new FileWriter(pathToFile);				  

 					for (List<String> recordData : records2) {  
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
             
 
//        } catch (ConnectionException e1) {
//            e1.printStackTrace();
//        }  
 
//        try {
//             connection.logout();
//              System.out.println("Logged out.");
//       } catch (ConnectionException ce) {
//                  ce.printStackTrace();
//       }
    }     
 
    private static String[] validateMirrorRep(String[] repData, String[] fldData) {
    	QueryResult queryResults = null;
    	QueryResult queryResults2 = null;
    	String roleId = "";
    	String profileId = "";
    	String roleName = "";
    	String profileName = "";
    	String managerUserName = "";
    	String managerId = "";
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

        System.out.println("Validate and Retrieve Mirror Rep...");
        
    	for (int h=0; h < repData[11].length(); h++) {
    		SaveResult[] saveResults;
    		if (repData[11].substring(h, h+1).indexOf("0") != -1) {
    			soapUri = LOGINURL[0] + SOAP_ENDPOINT + API_VERSION;
    			config = new ConnectorConfig();
    			config.setUsername(USERNAME[0]);
    			config.setPassword(PASSWORD[0]);
    		} else if (repData[11].substring(h, h+1).indexOf("1") != -1) {
    			soapUri = LOGINURL[1] + SOAP_ENDPOINT + API_VERSION;
    			config = new ConnectorConfig();
    			config.setUsername(USERNAME[1]);
    			config.setPassword(PASSWORD[1]);
    		} else if (repData[11].substring(h, h+1).indexOf("2") != -1) {
    	    	soapUri = LOGINURL[2] + SOAP_ENDPOINT + API_VERSION;
    	        config = new ConnectorConfig();
    	        config.setUsername(USERNAME[2]);
    	        config.setPassword(PASSWORD[2]);
    	    } else if (repData[11].substring(h, h+1).indexOf("3") != -1) {
    	    	soapUri = LOGINURL[3] + SOAP_ENDPOINT + API_VERSION;
    	        config = new ConnectorConfig();
    	        config.setUsername(USERNAME[3]);
    	        config.setPassword(PASSWORD[3]);
    	    } else if (repData[11].substring(h, h+1).indexOf("S") != -1) {
    	    	soapUri = LOGINURL[4] + SOAP_ENDPOINT + API_VERSION;
    	        config = new ConnectorConfig();
    	        config.setUsername(USERNAME[4]);
    	        config.setPassword(PASSWORD[4]);
    	    } else if (repData[11].substring(h, h+1).indexOf("P") != -1) {
    	    	soapUri = LOGINURL[5] + SOAP_ENDPOINT + API_VERSION;
    	        config = new ConnectorConfig();
    	        config.setUsername(USERNAME[5]);
    	        config.setPassword(PASSWORD[5]);
    	    }
    	
    		config.setAuthEndpoint(soapUri);
                
    		try { 
        	
    			connection = new EnterpriseConnection(config);
    			
// mirror rep    			
    			if (repData[12].length() == 7 && repData[5].length() > 0 || repData[12].length() == 7 && repData[3].length() > 0 || repData[12].length() == 7 && repData[4].length() > 0) {
    				
    		    	if (fldData.length > 25) {   						
    		    		for (int x = 25; x < fldData.length; x++) {
    		    			if (fldData[x].length() > 0 && repData[x].length() > 0) {
    		    				if (fldData[x].indexOf("MirrorPlatform") >= 0)  {
    		    					if (repData[x].indexOf("0") != -1) {
    		    						soapUri = LOGINURL[0] + SOAP_ENDPOINT + API_VERSION;
    		    						config2 = new ConnectorConfig();
    		    						config2.setUsername(USERNAME[0]);
    		    						config2.setPassword(PASSWORD[0]);
    		    					} else if (repData[x].indexOf("1") != -1) {
    		    						soapUri = LOGINURL[1] + SOAP_ENDPOINT + API_VERSION;
    		    						config2 = new ConnectorConfig();
    		    						config2.setUsername(USERNAME[1]);
    		    						config2.setPassword(PASSWORD[1]);
    		    					} else if (repData[x].indexOf("2") != -1) {
    		    						soapUri = LOGINURL[2] + SOAP_ENDPOINT + API_VERSION;
    		    						config2 = new ConnectorConfig();
    		    						config2.setUsername(USERNAME[2]);
    		    						config2.setPassword(PASSWORD[2]);
    		    					} else if (repData[x].indexOf("3") != -1) {
    		    						soapUri = LOGINURL[3] + SOAP_ENDPOINT + API_VERSION;
    		    						config2 = new ConnectorConfig();
    		    						config2.setUsername(USERNAME[3]);
    		    						config2.setPassword(PASSWORD[3]);
    		    					} else if (repData[x].indexOf("S") != -1) {
    		    						soapUri = LOGINURL[4] + SOAP_ENDPOINT + API_VERSION;
    		    						config2 = new ConnectorConfig();
    		    						config2.setUsername(USERNAME[4]);
    		    						config2.setPassword(PASSWORD[4]);
    		    					} else if (repData[x].indexOf("P") != -1) {
    		    						soapUri = LOGINURL[5] + SOAP_ENDPOINT + API_VERSION;
    		    						config2 = new ConnectorConfig();
    		    						config2.setUsername(USERNAME[5]);
    		    						config2.setPassword(PASSWORD[5]);
    		    					}
    		    					config2.setAuthEndpoint(soapUri);
    		    					connection2 = new EnterpriseConnection(config2);
    						}
    					}
    				}
    			}
    				if (connection2 == null) {
    					if (repData[5].length() > 0 && (repData[3].length() > 0 || repData[4].length() > 0)) {
    						if (soapUri.indexOf("adthstage") == -1) {
    							queryResults = connection.query("Select firstname, lastname, email, userroleid, profileid, user_license__c, business_unit__c, address_validation__c, ismmblookupsearch__c, cpqenabled__c, scpq__currentincpqsystem__c, manager_name__c, TimeZoneSidKey, LocaleSidKey, EmailEncodingKey, LanguageLocaleKey From User Where email like '%"+repData[5].subSequence(0, repData[5].indexOf("@"))+"%' and (Name like '%"+repData[3]+"%' or Name like '%"+repData[4]+"%') limit 5");
    						} else {
    							queryResults = connection.query("Select firstname, lastname, email, userroleid, profileid, business_unit__c, address_validation__c, ismmblookupsearch__c, cpqenabled__c, scpq__currentincpqsystem__c, TimeZoneSidKey, LocaleSidKey, EmailEncodingKey, LanguageLocaleKey From User Where email like '%"+repData[5].subSequence(0, repData[5].indexOf("@"))+"%' and (Name like '%"+repData[3]+"%' or Name like '%"+repData[4]+"%') limit 5");    							
    						}
    					} else if (repData[5].length() > 0 && repData[3].length() == 0 && repData[4].length() == 0) {
    						if (soapUri.indexOf("adthstage") == -1) {
    							queryResults = connection.query("Select firstname, lastname, email, userroleid, profileid, user_license__c, business_unit__c, address_validation__c, ismmblookupsearch__c, cpqenabled__c, scpq__currentincpqsystem__c, manager_name__c, TimeZoneSidKey, LocaleSidKey, EmailEncodingKey, LanguageLocaleKey From User Where email like '%"+repData[5].subSequence(0, repData[5].indexOf("@"))+"%' limit 5");        		
    						} else {
    							queryResults = connection.query("Select firstname, lastname, email, userroleid, profileid, business_unit__c, address_validation__c, ismmblookupsearch__c, cpqenabled__c, scpq__currentincpqsystem__c, TimeZoneSidKey, LocaleSidKey, EmailEncodingKey, LanguageLocaleKey From User Where email like '%"+repData[5].subSequence(0, repData[5].indexOf("@"))+"%' limit 5");        		
    						}
    						} else if (repData[5].length() == 0 && repData[3].length() > 0 && repData[4].length() > 0) {
        						if (soapUri.indexOf("adthstage") == -1) {
        							queryResults = connection.query("Select firstname, lastname, email, userroleid, profileid, user_license__c, business_unit__c, address_validation__c, ismmblookupsearch__c, cpqenabled__c, scpq__currentincpqsystem__c, manager_name__c, TimeZoneSidKey, LocaleSidKey, EmailEncodingKey, LanguageLocaleKey From User Where Name like '%"+repData[3]+"%' or Name like '%"+repData[4]+"%' limit 5");        		
        						} else {
        							queryResults = connection.query("Select firstname, lastname, email, userroleid, profileid, business_unit__c, address_validation__c, ismmblookupsearch__c, cpqenabled__c, scpq__currentincpqsystem__c, TimeZoneSidKey, LocaleSidKey, EmailEncodingKey, LanguageLocaleKey From User Where Name like '%"+repData[3]+"%' or Name like '%"+repData[4]+"%' limit 5");        		        							
        						}
        					}
    				} else {
        				if (repData[5].length() > 0 && (repData[3].length() > 0 || repData[4].length() > 0)) {
    						if (soapUri.indexOf("adthstage") == -1) {
    							queryResults = connection2.query("Select firstname, lastname, email, userroleid, profileid, user_license__c, business_unit__c, address_validation__c, ismmblookupsearch__c, cpqenabled__c, scpq__currentincpqsystem__c, manager_name__c, TimeZoneSidKey, LocaleSidKey, EmailEncodingKey, LanguageLocaleKey From User Where email like '%"+repData[5].subSequence(0, repData[5].indexOf("@"))+"%' and (Name like '%"+repData[3]+"%' or Name like '%"+repData[4]+"%') limit 5");
    						} else {
    							queryResults = connection2.query("Select firstname, lastname, email, userroleid, profileid, business_unit__c, address_validation__c, ismmblookupsearch__c, cpqenabled__c, scpq__currentincpqsystem__c, TimeZoneSidKey, LocaleSidKey, EmailEncodingKey, LanguageLocaleKey From User Where email like '%"+repData[5].subSequence(0, repData[5].indexOf("@"))+"%' and (Name like '%"+repData[3]+"%' or Name like '%"+repData[4]+"%') limit 5");    							
    						}
    						} else if (repData[5].length() > 0 && repData[3].length() == 0 && repData[4].length() == 0) {
        						if (soapUri.indexOf("adthstage") == -1) {
        							queryResults = connection2.query("Select firstname, lastname, email, userroleid, profileid, user_license__c, business_unit__c, address_validation__c, ismmblookupsearch__c, cpqenabled__c, scpq__currentincpqsystem__c, manager_name__c, TimeZoneSidKey, LocaleSidKey, EmailEncodingKey, LanguageLocaleKey From User Where email like '%"+repData[5].subSequence(0, repData[5].indexOf("@"))+"%' limit 5");        		
        						} else {
           							queryResults = connection2.query("Select firstname, lastname, email, userroleid, profileid, business_unit__c, address_validation__c, ismmblookupsearch__c, cpqenabled__c, scpq__currentincpqsystem__c, TimeZoneSidKey, LocaleSidKey, EmailEncodingKey, LanguageLocaleKey From User Where email like '%"+repData[5].subSequence(0, repData[5].indexOf("@"))+"%' limit 5");        		       							
            					}
        					} else if (repData[5].length() == 0 && repData[3].length() > 0 && repData[4].length() > 0) {
        						if (soapUri.indexOf("adthstage") == -1) {
        							queryResults = connection2.query("Select firstname, lastname, email, userroleid, profileid, user_license__c, business_unit__c, address_validation__c, ismmblookupsearch__c, cpqenabled__c, scpq__currentincpqsystem__c, manager_name__c, TimeZoneSidKey, LocaleSidKey, EmailEncodingKey, LanguageLocaleKey From User Where Name like '%"+repData[3]+"%' or Name like '%"+repData[4]+"%' limit 5");        		
        						} else {
        							queryResults = connection2.query("Select firstname, lastname, email, userroleid, profileid, business_unit__c, address_validation__c, ismmblookupsearch__c, cpqenabled__c, scpq__currentincpqsystem__c, TimeZoneSidKey, LocaleSidKey, EmailEncodingKey, LanguageLocaleKey From User Where Name like '%"+repData[3]+"%' or Name like '%"+repData[4]+"%' limit 5");        		        							
        						}
        					}
   				
    				}
    				if (queryResults.getSize() > 1) {
    					for (int i=0; i < queryResults.getSize();i++){    						
    						usr = (User)queryResults.getRecords()[i];
        					usr.setId("");
    						System.out.println("Id: " + usr.getId() + " - Name: "+ usr.getFirstName()+" "+ usr.getLastName()+"  username: "+ usr.getUsername()+"  Email: "+ usr.getEmail()+"  userroleid: "+ usr.getUserRoleId()+"  profileid: "+ usr.getProfileId()+"  businessunit: "+ usr.getBusiness_Unit__c()+"  addressvalidation: "+ usr.getAddress_Validation__c()+"  ismmblook: "+ usr.getIsMMBLookupSearch__c()+"  cpqenabled: "+ usr.getCPQEnabled__c()+"  currentincpq: "+ usr.getScpq__CurrentInCPQSystem__c());
    						if (usr.getEmail().toLowerCase().indexOf(repData[5].toLowerCase()) == 0 ) {
    							iHld = i; 
    							iVal = true;
    						}
    					}     						
    				} 
    				if (!iVal) {
    					repData[12] = "MirrorNotUnique";
    			    } else {
    					usr = (User)queryResults.getRecords()[iHld];
    					usr.setId("");   	
    					
    					if (connection2 == null) {
    						if (usr.getUserRoleId().length() > 0) {
    						queryResults2 = connection.query("Select name From userrole Where id = '"+usr.getUserRoleId()+"' limit 5");
    						if (queryResults2.getSize() == 1) {
    							usrRole = (UserRole)queryResults2.getRecords()[0];
    							roleName = usrRole.getName();
    							System.out.println("roleName: " + roleName);	
    						} else {repData[12] = "InvalidRole";}
    						}	
    						if (usr.getProfileId().length() > 0) {
    						queryResults2 = connection.query("Select name From profile Where id = '"+usr.getProfileId()+"' limit 5");
    						if (queryResults2.getSize() == 1) {
    							prf = (Profile)queryResults2.getRecords()[0];
    							profileName = prf.getName();
    							System.out.println("profileName: " + profileName);	
    						} else {repData[12] = "InvalidProfile";}  
    						}
    						if (usr.getManagerId() != null) {
    						queryResults2 = connection.query("Select username From user Where id = '"+usr.getManagerId()+"' limit 5");
    						if (queryResults2.getSize() == 1) {
    							usr2 = (User)queryResults2.getRecords()[0];
    							managerUserName = usr2.getUsername();
    							System.out.println("managerUserName: " + managerUserName);	
    						} else {repData[12] = "InvalidManagerUserName";} 
    						}
    							System.out.println("Id: " + usr.getId() + " - Name: "+ usr.getFirstName()+" "+ usr.getLastName()+"  username: "+ usr.getUsername()+"  Email: "+ usr.getEmail()+"  userroleid: "+ usr.getUserRoleId()+"  profileid: "+ usr.getProfileId()+"  businessunit: "+ usr.getBusiness_Unit__c()+"  addressvalidation: "+ usr.getAddress_Validation__c()+"  ismmblook: "+ usr.getIsMMBLookupSearch__c()+"  cpqenabled: "+ usr.getCPQEnabled__c()+"  currentincpq: "+ usr.getScpq__CurrentInCPQSystem__c());
    					} else {
    						if (usr.getUserRoleId().length() > 0) {
    		    			queryResults2 = connection2.query("Select name From userrole Where id = '"+usr.getUserRoleId()+"' limit 5");
    		    			if (queryResults2.getSize() == 1) {
    		            		usrRole = (UserRole)queryResults2.getRecords()[0];
    		            		roleName = usrRole.getName();
    		            		System.out.println("roleName: " + roleName);	
    		    			} else {repData[12] = "InvalidRole";}
    						}
    						if (usr.getProfileId().length() > 0) {
    		        		queryResults2 = connection2.query("Select name From profile Where id = '"+usr.getProfileId()+"' limit 5");
    		            	if (queryResults2.getSize() == 1) {
    		            		prf = (Profile)queryResults2.getRecords()[0];
    		            		profileName = prf.getName();
    		            		System.out.println("profileName: " + profileName);	
    		            	} else {repData[12] = "InvalidProfile";} 
    						}
    						if (usr.getManagerId() != null) {
    		        		queryResults2 = connection2.query("Select username From user Where id = '"+usr.getManagerId()+"' limit 5");
    		            	if (queryResults2.getSize() == 1) {
    		            		usr2 = (User)queryResults2.getRecords()[0];
    		            		managerUserName = usr2.getUsername();
    		            		System.out.println("managerUserName: " + managerUserName);	
    		            	} else {repData[12] = "InvalidManagerUserName";}   
    						}
        					System.out.println("Id: " + usr.getId() + " - Name: "+ usr.getFirstName()+" "+ usr.getLastName()+"  username: "+ usr.getUsername()+"  Email: "+ usr.getEmail()+"  userroleid: "+ usr.getUserRoleId()+"  profileid: "+ usr.getProfileId()+"  businessunit: "+ usr.getBusiness_Unit__c()+"  addressvalidation: "+ usr.getAddress_Validation__c()+"  ismmblook: "+ usr.getIsMMBLookupSearch__c()+"  cpqenabled: "+ usr.getCPQEnabled__c()+"  currentincpq: "+ usr.getScpq__CurrentInCPQSystem__c());
            				connection2.logout();
    						
    					}
    				}
    				
    			}	
 
// lookup role and profile    	
    			if (repData[9].length() > 0) {
    				queryResults = connection.query("Select id, name From userrole Where name = '"+repData[9]+"' limit 5");
    				if (queryResults.getSize() == 1) {
            			roleId = queryResults.getRecords()[0].getId();
            			System.out.println("roleId: " + roleId);	
    				} else {repData[12] = "InvalidRole";}
    			} else if (roleName.length() > 0) {
    				queryResults = connection.query("Select id, name From userrole Where name = '"+roleName+"' limit 5");
    				if (queryResults.getSize() == 1) {
            			roleId = queryResults.getRecords()[0].getId();
            			System.out.println("roleId: " + roleId);	
    				} else {repData[12] = "InvalidRole";}    				
    			}
    			if (repData[10].length() > 0) {
        		queryResults = connection.query("Select id, name From profile Where name = '"+repData[10]+"' limit 5");
            		if (queryResults.getSize() == 1) {
            			profileId = queryResults.getRecords()[0].getId();
            			System.out.println("profileId: " + profileId);	
            		} else {repData[12] = "InvalidProfile";}
    			} else if (profileName.length() > 0) {
            		queryResults = connection.query("Select id, name From profile Where name = '"+profileName+"' limit 5");
            		if (queryResults.getSize() == 1) {
            			profileId = queryResults.getRecords()[0].getId();
            			System.out.println("profileId: " + profileId);	
            		} else {repData[12] = "InvalidProfile";}    				
    			}
 			
// manager rep
    			if (repData[12].length() == 7 && repData[8].length() > 0 || repData[12].length() == 7 && repData[6].length() > 0 || repData[12].length() == 7 && repData[7].length() > 0) {
    				if (repData[8].length() > 0 && (repData[6].length() > 0 || repData[7].length() > 0)) {
    					queryResults = connection.query("Select id, name From User Where email like '%"+repData[8].subSequence(0, repData[8].indexOf("@"))+"%' and (Name like '%"+repData[6]+"%' or Name like '%"+repData[7]+"%') limit 5");
    				} else if (repData[8].length() > 0 && repData[6].length() == 0 && repData[7].length() == 0) {
    					queryResults = connection.query("Select id, name From User Where email like '%"+repData[8].subSequence(0, repData[8].indexOf("@"))+"%' limit 5");        		
    				} else if (repData[8].length() == 0 && repData[6].length() > 0 && repData[7].length() > 0) {
    					queryResults = connection.query("Select id, name From User Where Name like '%"+repData[6]+"%' or Name like '%"+repData[7]+"%' limit 5");        		
    				}
    				if (queryResults.getSize() == 1) {
    					managerId = queryResults.getRecords()[0].getId();
    					System.out.println("managerId: " + managerId);  						
    				} else {repData[12] = "InvalidManager";}
    			} else if (managerUserName.length() > 0) {
					queryResults = connection.query("Select id, name From User Where UserName = '"+managerUserName+"' limit 5");        		
    				if (queryResults.getSize() == 1) {
    					managerId = queryResults.getRecords()[0].getId();
    					System.out.println("managerId: " + managerId);  						
    				} else {repData[12] = "InvalidManager";}
    			}

    			
    			if (repData[12].length() == 7) {	

// loop thru dynamically added fields    					
    					if (fldData.length > 25) {   						
    						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    						Date date = null;
    						Calendar cal = null;
    						for (int x = 25; x < fldData.length; x++) {
    							if (fldData[x].length() > 0 && repData[x].length() > 0) {
    								
									if (fldData[x].indexOf("Id") >= 0)  {usr.setId(repData[x]);}
									if (fldData[x].indexOf("Username") >= 0)  {usr.setUsername(repData[x]);}
									if (fldData[x].indexOf("LastName") >= 0)  {usr.setLastName(repData[x]);}
									if (fldData[x].indexOf("FirstName") >= 0)  {usr.setFirstName(repData[x]);}
									if (fldData[x].indexOf("Name") >= 0)  {usr.setName(repData[x]);}
									if (fldData[x].indexOf("CompanyName") >= 0)  {usr.setCompanyName(repData[x]);}
									if (fldData[x].indexOf("Division") >= 0)  {usr.setDivision(repData[x]);}
									if (fldData[x].indexOf("Department") >= 0)  {usr.setDepartment(repData[x]);}
									if (fldData[x].indexOf("Title") >= 0)  {usr.setTitle(repData[x]);}
									if (fldData[x].indexOf("Street") >= 0)  {usr.setStreet(repData[x]);}
									if (fldData[x].indexOf("City") >= 0)  {usr.setCity(repData[x]);}
									if (fldData[x].indexOf("State") >= 0)  {usr.setState(repData[x]);}
									if (fldData[x].indexOf("PostalCode") >= 0)  {usr.setPostalCode(repData[x]);}
									if (fldData[x].indexOf("Country") >= 0)  {usr.setCountry(repData[x]);}
									if (fldData[x].indexOf("Latitude") >= 0)  {usr.setLatitude(Double.parseDouble(repData[x]));}
									if (fldData[x].indexOf("Longitude") >= 0)  {usr.setLongitude(Double.parseDouble(repData[x]));}
									if (fldData[x].indexOf("GeocodeAccuracy") >= 0)  {usr.setGeocodeAccuracy(repData[x]);}
//									if (fldData[x].indexOf("Address") >= 0)  {usr.setAddress(repData[x]);}
									if (fldData[x].indexOf("Email") >= 0)  {usr.setEmail(repData[x]);}
									if (fldData[x].indexOf("EmailPreferencesAutoBcc") >= 0)  {usr.setEmailPreferencesAutoBcc(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("EmailPreferencesAutoBccStayInTouch") >= 0)  {usr.setEmailPreferencesAutoBccStayInTouch(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("EmailPreferencesStayInTouchReminder") >= 0)  {usr.setEmailPreferencesStayInTouchReminder(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("SenderEmail") >= 0)  {usr.setSenderEmail(repData[x]);}
									if (fldData[x].indexOf("SenderName") >= 0)  {usr.setSenderName(repData[x]);}
									if (fldData[x].indexOf("Signature") >= 0)  {usr.setSignature(repData[x]);}
									if (fldData[x].indexOf("StayInTouchSubject") >= 0)  {usr.setStayInTouchSubject(repData[x]);}
									if (fldData[x].indexOf("StayInTouchSignature") >= 0)  {usr.setStayInTouchSignature(repData[x]);}
									if (fldData[x].indexOf("StayInTouchNote") >= 0)  {usr.setStayInTouchNote(repData[x]);}
									if (fldData[x].indexOf("Phone") >= 0)  {usr.setPhone(repData[x]);}
									if (fldData[x].indexOf("Fax") >= 0)  {usr.setFax(repData[x]);}
									if (fldData[x].indexOf("MobilePhone") >= 0)  {usr.setMobilePhone(repData[x]);}
									if (fldData[x].indexOf("Alias") >= 0)  {usr.setAlias(repData[x]);}
									if (fldData[x].indexOf("CommunityNickname") >= 0)  {usr.setCommunityNickname(repData[x]);}
									if (fldData[x].indexOf("BadgeText") >= 0)  {usr.setBadgeText(repData[x]);}
									if (fldData[x].indexOf("IsActive") >= 0)  {usr.setIsActive(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("TimeZoneSidKey") >= 0)  {usr.setTimeZoneSidKey(repData[x]);}
									if (fldData[x].indexOf("UserRoleId") >= 0)  {usr.setUserRoleId(repData[x]);}
									if (fldData[x].indexOf("LocaleSidKey") >= 0)  {usr.setLocaleSidKey(repData[x]);}
									if (fldData[x].indexOf("ReceivesInfoEmails") >= 0)  {usr.setReceivesInfoEmails(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("ReceivesAdminInfoEmails") >= 0)  {usr.setReceivesAdminInfoEmails(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("EmailEncodingKey") >= 0)  {usr.setEmailEncodingKey(repData[x]);}
									if (fldData[x].indexOf("ProfileId") >= 0)  {usr.setProfileId(repData[x]);}
									if (fldData[x].indexOf("UserType") >= 0)  {usr.setUserType(repData[x]);}
									if (fldData[x].indexOf("LanguageLocaleKey") >= 0)  {usr.setLanguageLocaleKey(repData[x]);}
									if (fldData[x].indexOf("EmployeeNumber") >= 0)  {usr.setEmployeeNumber(repData[x]);}
									if (fldData[x].indexOf("DelegatedApproverId") >= 0)  {usr.setDelegatedApproverId(repData[x]);}
									if (fldData[x].indexOf("ManagerId") >= 0)  {usr.setManagerId(repData[x]);}
									if (fldData[x].indexOf("LastLoginDate") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal = Calendar.getInstance();cal.setTime(date);usr.setLastLoginDate(cal);}
									if (fldData[x].indexOf("LastPasswordChangeDate") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setLastPasswordChangeDate(cal);}
									if (fldData[x].indexOf("CreatedDate") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setCreatedDate(cal);}
									if (fldData[x].indexOf("CreatedById") >= 0)  {usr.setCreatedById(repData[x]);}
									if (fldData[x].indexOf("LastModifiedDate") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setLastModifiedDate(cal);}
									if (fldData[x].indexOf("LastModifiedById") >= 0)  {usr.setLastModifiedById(repData[x]);}
									if (fldData[x].indexOf("SystemModstamp") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setSystemModstamp(cal);}
									if (fldData[x].indexOf("NumberOfFailedLogins") >= 0)  {usr.setNumberOfFailedLogins(Integer.valueOf(repData[x]));}
									if (fldData[x].indexOf("OfflineTrialExpirationDate") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setOfflineTrialExpirationDate(cal);}
									if (fldData[x].indexOf("OfflinePdaTrialExpirationDate") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setOfflinePdaTrialExpirationDate(cal);}
									if (fldData[x].indexOf("UserPermissionsMarketingUser") >= 0)  {usr.setUserPermissionsMarketingUser(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPermissionsOfflineUser") >= 0)  {usr.setUserPermissionsOfflineUser(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPermissionsAvantgoUser") >= 0)  {usr.setUserPermissionsAvantgoUser(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPermissionsCallCenterAutoLogin") >= 0)  {usr.setUserPermissionsCallCenterAutoLogin(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPermissionsMobileUser") >= 0)  {usr.setUserPermissionsMobileUser(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPermissionsSFContentUser") >= 0)  {usr.setUserPermissionsSFContentUser(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPermissionsKnowledgeUser") >= 0)  {usr.setUserPermissionsKnowledgeUser(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPermissionsInteractionUser") >= 0)  {usr.setUserPermissionsInteractionUser(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPermissionsSupportUser") >= 0)  {usr.setUserPermissionsSupportUser(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPermissionsChatterAnswersUser") >= 0)  {usr.setUserPermissionsChatterAnswersUser(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesActivityRemindersPopup") >= 0)  {usr.setUserPreferencesActivityRemindersPopup(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesEventRemindersCheckboxDefault") >= 0)  {usr.setUserPreferencesEventRemindersCheckboxDefault(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesTaskRemindersCheckboxDefault") >= 0)  {usr.setUserPreferencesTaskRemindersCheckboxDefault(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesReminderSoundOff") >= 0)  {usr.setUserPreferencesReminderSoundOff(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisableAllFeedsEmail") >= 0)  {usr.setUserPreferencesDisableAllFeedsEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisableFollowersEmail") >= 0)  {usr.setUserPreferencesDisableFollowersEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisableProfilePostEmail") >= 0)  {usr.setUserPreferencesDisableProfilePostEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisableChangeCommentEmail") >= 0)  {usr.setUserPreferencesDisableChangeCommentEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisableLaterCommentEmail") >= 0)  {usr.setUserPreferencesDisableLaterCommentEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisProfPostCommentEmail") >= 0)  {usr.setUserPreferencesDisProfPostCommentEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesContentNoEmail") >= 0)  {usr.setUserPreferencesContentNoEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesContentEmailAsAndWhen") >= 0)  {usr.setUserPreferencesContentEmailAsAndWhen(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesApexPagesDeveloperMode") >= 0)  {usr.setUserPreferencesApexPagesDeveloperMode(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesReceiveNoNotificationsAsApprover") >= 0)  {usr.setUserPreferencesReceiveNoNotificationsAsApprover(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesReceiveNotificationsAsDelegatedApprover") >= 0)  {usr.setUserPreferencesReceiveNotificationsAsDelegatedApprover(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesHideCSNGetChatterMobileTask") >= 0)  {usr.setUserPreferencesHideCSNGetChatterMobileTask(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisableMentionsPostEmail") >= 0)  {usr.setUserPreferencesDisableMentionsPostEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisMentionsCommentEmail") >= 0)  {usr.setUserPreferencesDisMentionsCommentEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesHideCSNDesktopTask") >= 0)  {usr.setUserPreferencesHideCSNDesktopTask(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesHideChatterOnboardingSplash") >= 0)  {usr.setUserPreferencesHideChatterOnboardingSplash(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesHideSecondChatterOnboardingSplash") >= 0)  {usr.setUserPreferencesHideSecondChatterOnboardingSplash(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisCommentAfterLikeEmail") >= 0)  {usr.setUserPreferencesDisCommentAfterLikeEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisableLikeEmail") >= 0)  {usr.setUserPreferencesDisableLikeEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesSortFeedByComment") >= 0)  {usr.setUserPreferencesSortFeedByComment(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisableMessageEmail") >= 0)  {usr.setUserPreferencesDisableMessageEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisableBookmarkEmail") >= 0)  {usr.setUserPreferencesDisableBookmarkEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisableSharePostEmail") >= 0)  {usr.setUserPreferencesDisableSharePostEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesEnableAutoSubForFeeds") >= 0)  {usr.setUserPreferencesEnableAutoSubForFeeds(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisableFileShareNotificationsForApi") >= 0)  {usr.setUserPreferencesDisableFileShareNotificationsForApi(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowTitleToExternalUsers") >= 0)  {usr.setUserPreferencesShowTitleToExternalUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowManagerToExternalUsers") >= 0)  {usr.setUserPreferencesShowManagerToExternalUsers(Boolean.valueOf(Boolean.valueOf(repData[x])));}
									if (fldData[x].indexOf("UserPreferencesShowEmailToExternalUsers") >= 0)  {usr.setUserPreferencesShowEmailToExternalUsers(Boolean.valueOf(Boolean.valueOf(repData[x])));}
									if (fldData[x].indexOf("UserPreferencesShowWorkPhoneToExternalUsers") >= 0)  {usr.setUserPreferencesShowWorkPhoneToExternalUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowMobilePhoneToExternalUsers") >= 0)  {usr.setUserPreferencesShowMobilePhoneToExternalUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowFaxToExternalUsers") >= 0)  {usr.setUserPreferencesShowFaxToExternalUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowStreetAddressToExternalUsers") >= 0)  {usr.setUserPreferencesShowStreetAddressToExternalUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowCityToExternalUsers") >= 0)  {usr.setUserPreferencesShowCityToExternalUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowStateToExternalUsers") >= 0)  {usr.setUserPreferencesShowStateToExternalUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowPostalCodeToExternalUsers") >= 0)  {usr.setUserPreferencesShowPostalCodeToExternalUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowCountryToExternalUsers") >= 0)  {usr.setUserPreferencesShowCountryToExternalUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowProfilePicToGuestUsers") >= 0)  {usr.setUserPreferencesShowProfilePicToGuestUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowTitleToGuestUsers") >= 0)  {usr.setUserPreferencesShowTitleToGuestUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowCityToGuestUsers") >= 0)  {usr.setUserPreferencesShowCityToGuestUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowStateToGuestUsers") >= 0)  {usr.setUserPreferencesShowStateToGuestUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowPostalCodeToGuestUsers") >= 0)  {usr.setUserPreferencesShowPostalCodeToGuestUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowCountryToGuestUsers") >= 0)  {usr.setUserPreferencesShowCountryToGuestUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesPipelineViewHideHelpPopover") >= 0)  {usr.setUserPreferencesPipelineViewHideHelpPopover(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesDisableEndorsementEmail") >= 0)  {usr.setUserPreferencesDisableEndorsementEmail(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesPathAssistantCollapsed") >= 0)  {usr.setUserPreferencesPathAssistantCollapsed(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesCacheDiagnostics") >= 0)  {usr.setUserPreferencesCacheDiagnostics(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowEmailToGuestUsers") >= 0)  {usr.setUserPreferencesShowEmailToGuestUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowManagerToGuestUsers") >= 0)  {usr.setUserPreferencesShowManagerToGuestUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowWorkPhoneToGuestUsers") >= 0)  {usr.setUserPreferencesShowWorkPhoneToGuestUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowMobilePhoneToGuestUsers") >= 0)  {usr.setUserPreferencesShowMobilePhoneToGuestUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowFaxToGuestUsers") >= 0)  {usr.setUserPreferencesShowFaxToGuestUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesShowStreetAddressToGuestUsers") >= 0)  {usr.setUserPreferencesShowStreetAddressToGuestUsers(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesLightningExperiencePreferred") >= 0)  {usr.setUserPreferencesLightningExperiencePreferred(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesPreviewLightning") >= 0)  {usr.setUserPreferencesPreviewLightning(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesHideEndUserOnboardingAssistantModal") >= 0)  {usr.setUserPreferencesHideEndUserOnboardingAssistantModal(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesHideLightningMigrationModal") >= 0)  {usr.setUserPreferencesHideLightningMigrationModal(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesHideSfxWelcomeMat") >= 0)  {usr.setUserPreferencesHideSfxWelcomeMat(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesHideBiggerPhotoCallout") >= 0)  {usr.setUserPreferencesHideBiggerPhotoCallout(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesGlobalNavBarWTShown") >= 0)  {usr.setUserPreferencesGlobalNavBarWTShown(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesGlobalNavGridMenuWTShown") >= 0)  {usr.setUserPreferencesGlobalNavGridMenuWTShown(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesCreateLEXAppsWTShown") >= 0)  {usr.setUserPreferencesCreateLEXAppsWTShown(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesFavoritesWTShown") >= 0)  {usr.setUserPreferencesFavoritesWTShown(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesRecordHomeSectionCollapseWTShown") >= 0)  {usr.setUserPreferencesRecordHomeSectionCollapseWTShown(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesRecordHomeReservedWTShown") >= 0)  {usr.setUserPreferencesRecordHomeReservedWTShown(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesFavoritesShowTopFavorites") >= 0)  {usr.setUserPreferencesFavoritesShowTopFavorites(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesExcludeMailAppAttachments") >= 0)  {usr.setUserPreferencesExcludeMailAppAttachments(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesSuppressTaskSFXReminders") >= 0)  {usr.setUserPreferencesSuppressTaskSFXReminders(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesSuppressEventSFXReminders") >= 0)  {usr.setUserPreferencesSuppressEventSFXReminders(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesPreviewCustomTheme") >= 0)  {usr.setUserPreferencesPreviewCustomTheme(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesHasCelebrationBadge") >= 0)  {usr.setUserPreferencesHasCelebrationBadge(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesUserDebugModePref") >= 0)  {usr.setUserPreferencesUserDebugModePref(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesSRHOverrideActivities") >= 0)  {usr.setUserPreferencesSRHOverrideActivities(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("UserPreferencesNewLightningReportRunPageEnabled") >= 0)  {usr.setUserPreferencesNewLightningReportRunPageEnabled(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("ContactId") >= 0)  {usr.setContactId(repData[x]);}
									if (fldData[x].indexOf("AccountId") >= 0)  {usr.setAccountId(repData[x]);}
									if (fldData[x].indexOf("CallCenterId") >= 0)  {usr.setCallCenterId(repData[x]);}
									if (fldData[x].indexOf("Extension") >= 0)  {usr.setExtension(repData[x]);}
									if (fldData[x].indexOf("FederationIdentifier") >= 0)  {usr.setFederationIdentifier(repData[x]);}
									if (fldData[x].indexOf("AboutMe") >= 0)  {usr.setAboutMe(repData[x]);}
									if (fldData[x].indexOf("FullPhotoUrl") >= 0)  {usr.setFullPhotoUrl(repData[x]);}
									if (fldData[x].indexOf("SmallPhotoUrl") >= 0)  {usr.setSmallPhotoUrl(repData[x]);}
									if (fldData[x].indexOf("IsExtIndicatorVisible") >= 0)  {usr.setIsExtIndicatorVisible(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("OutOfOfficeMessage") >= 0)  {usr.setOutOfOfficeMessage(repData[x]);}
									if (fldData[x].indexOf("MediumPhotoUrl") >= 0)  {usr.setMediumPhotoUrl(repData[x]);}
									if (fldData[x].indexOf("DigestFrequency") >= 0)  {usr.setDigestFrequency(repData[x]);}
									if (fldData[x].indexOf("DefaultGroupNotificationFrequency") >= 0)  {usr.setDefaultGroupNotificationFrequency(repData[x]);}
									if (fldData[x].indexOf("LastViewedDate") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setLastViewedDate(cal);}
									if (fldData[x].indexOf("LastReferencedDate") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setLastReferencedDate(cal);}
									if (fldData[x].indexOf("BannerPhotoUrl") >= 0)  {usr.setBannerPhotoUrl(repData[x]);}
									if (fldData[x].indexOf("SmallBannerPhotoUrl") >= 0)  {usr.setSmallBannerPhotoUrl(repData[x]);}
									if (fldData[x].indexOf("MediumBannerPhotoUrl") >= 0)  {usr.setMediumBannerPhotoUrl(repData[x]);}
									if (fldData[x].indexOf("IsProfilePhotoActive") >= 0)  {usr.setIsProfilePhotoActive(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("IndividualId") >= 0)  {usr.setIndividualId(repData[x]);}
									if (fldData[x].indexOf("ASAP_Agent_Id__c") >= 0)  {usr.setASAP_Agent_Id__c(Double.parseDouble(repData[x]));}
									if (fldData[x].indexOf("Business_Unit__c") >= 0)  {usr.setBusiness_Unit__c(repData[x]);}
									if (fldData[x].indexOf("DeviceID__c") >= 0)  {usr.setDeviceID__c(repData[x]);}
									if (fldData[x].indexOf("DevicePhoneNumber__c") >= 0)  {usr.setDevicePhoneNumber__c(repData[x]);}
									if (fldData[x].indexOf("IncomingManager__c") >= 0)  {usr.setIncomingManager__c(repData[x]);}
									if (fldData[x].indexOf("User_Status__c") >= 0)  {usr.setUser_Status__c(repData[x]);}
									if (fldData[x].indexOf("RegionNumber__c") >= 0)  {usr.setRegionNumber__c(repData[x]);}
									if (fldData[x].indexOf("AreaNumber__c") >= 0)  {usr.setAreaNumber__c(repData[x]);}
									if (fldData[x].indexOf("DistrictNumber__c") >= 0)  {usr.setDistrictNumber__c(repData[x]);}
									if (fldData[x].indexOf("TownNumber__c") >= 0)  {usr.setTownNumber__c(repData[x]);}
									if (fldData[x].indexOf("Count__c") >= 0)  {usr.setCount__c(Double.parseDouble(repData[x]));}
									if (fldData[x].indexOf("EmployeeNumber__c") >= 0)  {usr.setEmployeeNumber__c(repData[x]);}
									if (fldData[x].indexOf("Qualification__c") >= 0)  {usr.setQualification__c(repData[x]);}
									if (fldData[x].indexOf("Region_Id__c") >= 0)  {usr.setRegion_Id__c(repData[x]);}
									if (fldData[x].indexOf("StartDateinCurrentJob__c") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setStartDateinCurrentJob__c(cal);}
									if (fldData[x].indexOf("Chatter_Champion__c") >= 0)  {usr.setChatter_Champion__c(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("Terms_and_Conditions_Accepted__c") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setTerms_and_Conditions_Accepted__c(cal);}
									if (fldData[x].indexOf("scpq__CPQUserKey__c") >= 0)  {usr.setScpq__CPQUserKey__c(repData[x]);}
									if (fldData[x].indexOf("scpq__CurrentInCPQSystem__c") >= 0)  {usr.setScpq__CurrentInCPQSystem__c(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("scpq__LastCPQSyncDate__c") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setScpq__LastCPQSyncDate__c(cal);}
									if (fldData[x].indexOf("scpq__LastCPQSyncSuccessful__c") >= 0)  {usr.setScpq__LastCPQSyncSuccessful__c(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("Address_Validation__c") >= 0)  {usr.setAddress_Validation__c(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("Commission_ID__c") >= 0)  {usr.setCommission_ID__c(repData[x]);}
									if (fldData[x].indexOf("State_License__c") >= 0)  {usr.setState_License__c(repData[x]);}
									if (fldData[x].indexOf("isMMBLookupSearch__c") >= 0)  {usr.setIsMMBLookupSearch__c(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("Approver_Level__c") >= 0)  {usr.setApprover_Level__c(repData[x]);}
									if (fldData[x].indexOf("ChatterExcluded__c") >= 0)  {usr.setChatterExcluded__c(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("CPQEnabled__c") >= 0)  {usr.setCPQEnabled__c(repData[x]);}
									if (fldData[x].indexOf("DelegatedEmail__c") >= 0)  {usr.setDelegatedEmail__c(repData[x]);}
									if (fldData[x].indexOf("Profile_Type__c") >= 0)  {usr.setProfile_Type__c(repData[x]);}
									if (fldData[x].indexOf("User_License__c") >= 0)  {usr.setUser_License__c(repData[x]);}
									if (fldData[x].indexOf("Hierarchy_Created__c") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setHierarchy_Created__c(cal);}
									if (fldData[x].indexOf("Agent_Type__c") >= 0)  {usr.setAgent_Type__c(repData[x]);}
									if (fldData[x].indexOf("SMS_Enabled_Last_Updated__c") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setSMS_Enabled_Last_Updated__c(cal);}
									if (fldData[x].indexOf("SMS_Enabled__c") >= 0)  {usr.setSMS_Enabled__c(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("License_Expiration_date__c") >= 0)  {try {date = (Date) sdf.parse(repData[x]);} catch (ParseException e) {e.printStackTrace();}cal.setTime(date);usr.setLicense_Expiration_date__c(cal);}
									if (fldData[x].indexOf("Manager_Name__c") >= 0)  {usr.setManager_Name__c(repData[x]);}
									if (fldData[x].indexOf("eContractEligible__c") >= 0)  {usr.setEContractEligible__c(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("Profile_Name__c") >= 0)  {usr.setProfile_Name__c(repData[x]);}
									if (fldData[x].indexOf("ExtraSkills__c") >= 0)  {usr.setExtraSkills__c(repData[x]);}
									if (fldData[x].indexOf("Rep_Team__c") >= 0)  {usr.setRep_Team__c(repData[x]);}
									if (fldData[x].indexOf("CheckMyHierarchy__c") >= 0)  {usr.setCheckMyHierarchy__c(repData[x]);}
									if (fldData[x].indexOf("SimpleSell__c") >= 0)  {usr.setSimpleSell__c(Boolean.valueOf(repData[x]));}
									if (fldData[x].indexOf("GNCC__CC_EnvironmentUrl__c") >= 0)  {usr.setGNCC__CC_EnvironmentUrl__c(repData[x]);} 
    								
    							}
    						}
    					}
    					usr.setFirstName(repData[0]);
    					usr.setLastName(repData[1]);
    					usr.setEmail(repData[2]);      
    					if (soapUri.indexOf("adthstage") == -1) {
    						usr.setUser_License__c("");
    					}
    					usr.setAlias(repData[2].substring(0, 5)+"ADT");
    					usr.setUsername(repData[2]+config.getUsername().substring(config.getUsername().lastIndexOf(".")));
    					usr.setCommunityNickname(repData[2].substring(0, repData[2].indexOf("@")));
    					usr.setFederationIdentifier(repData[2].substring(0, repData[2].indexOf("@")+1) + repData[2].substring(repData[2].indexOf("@")+1).toUpperCase());
    					usr.setEmployeeNumber__c(repData[17]);
    					if (roleId.length() > 0) {usr.setUserRoleId(roleId);}
    					if (profileId.length() > 0) {usr.setProfileId(profileId);}
    					if (managerId.length() > 0) {usr.setManagerId(managerId);}
   					
    					usrSobj[0] = usr;
    					repData[12] = createUserIds(usr, repData[11]);
    					return repData;
//    				}
    			}
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
    
    public static String createUserIds(User usr, String platforms) {
    	String status = "", roleName = "", profileName="", managerUserName = "";
    	ConnectorConfig config2 = new ConnectorConfig();
    	EnterpriseConnection connection2 = null;
    	User[] usrSobj = new User[1];
    	usrSobj[0] = usr;   	
    	HttpPost httpPost = null;
    	QueryResult queryResults2;
    	User usr2, usrHold=usr;
    	UserRole usrRole2;
    	Profile prf2;
    	
    	for (int h=0; h < platforms.length(); h++) {
    		UpsertResult[] upsertResults;
    		if (platforms.substring(h, h+1).indexOf("0") != -1) {
    			soapUri = LOGINURL[0] + SOAP_ENDPOINT + API_VERSION;
    			config2 = new ConnectorConfig();
    			config2.setUsername(USERNAME[0]);
    			config2.setPassword(PASSWORD[0]);
        		config2.setAuthEndpoint(soapUri);
    			LOGINURLx = LOGINURL[0];
    			USERNAMEx = USERNAME[0];
    			PASSWORDx = PASSWORD[0];
    			usr = usrHold;
    		} else if (platforms.substring(h, h+1).indexOf("1") != -1) {
    			soapUri = LOGINURL[1] + SOAP_ENDPOINT + API_VERSION;
    			config2 = new ConnectorConfig();
    			config2.setUsername(USERNAME[1]);
    			config2.setPassword(PASSWORD[1]);
        		config2.setAuthEndpoint(soapUri);
    			LOGINURLx = LOGINURL[1];
    			USERNAMEx = USERNAME[1];
    			PASSWORDx = PASSWORD[1];
    			usr = usrHold;
    		} else if (platforms.substring(h, h+1).indexOf("2") != -1) {
    	    	soapUri = LOGINURL[2] + SOAP_ENDPOINT + API_VERSION;
    	        config2 = new ConnectorConfig();
    	        config2.setUsername(USERNAME[2]);
    	        config2.setPassword(PASSWORD[2]);
        		config2.setAuthEndpoint(soapUri);
    			LOGINURLx = LOGINURL[2];
    			USERNAMEx = USERNAME[2];
    			PASSWORDx = PASSWORD[2];
    			usr = usrHold;
    	    } else if (platforms.substring(h, h+1).indexOf("3") != -1) {
    	    	soapUri = LOGINURL[3] + SOAP_ENDPOINT + API_VERSION;
    	        config2 = new ConnectorConfig();
    	        config2.setUsername(USERNAME[3]);
    	        config2.setPassword(PASSWORD[3]);
        		config2.setAuthEndpoint(soapUri);
    			LOGINURLx = LOGINURL[3];
    			USERNAMEx = USERNAME[3];
    			PASSWORDx = PASSWORD[3];
    			usr = usrHold;
    	    } else if (platforms.substring(h, h+1).indexOf("S") != -1) {
    	        HttpClient httpclient = HttpClientBuilder.create().build();
    	        String loginURL = LOGINURL[4] +
    	                          OAUTH2_GRANTSERVICE +
    	                          "&client_id=" + OAUTH2_CLIENTID +
    	                          "&client_secret=" + OAUTH2_CLIENTSECRET+
    	                          "&username=" + USERNAME[4] +
    	                          "&password=" + PASSWORD[4];
    	        httpPost = new HttpPost(loginURL);
    	        HttpResponse response = null;
    	        try {response = httpclient.execute(httpPost);} catch (ClientProtocolException cpException) {cpException.printStackTrace();} catch (IOException ioException) {ioException.printStackTrace();}
    	        final int statusCode = response.getStatusLine().getStatusCode();
    	        if (statusCode != HttpStatus.SC_OK) {
    	            System.out.println("Error authenticating to Force.com: "+statusCode);
    	        }
    	        String getResult = null;
    	        try {getResult = EntityUtils.toString(response.getEntity());} catch (IOException ioException) {ioException.printStackTrace();}
    	        JSONObject jsonObject = null;
    	        String loginAccessToken = null;
    	        String loginInstanceUrl = null;
    	        try {
    	            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
    	            loginAccessToken = jsonObject.getString("access_token");
    	            loginInstanceUrl = jsonObject.getString("instance_url");
    	        } catch (JSONException jsonException) {
    	            jsonException.printStackTrace();
    	        }
    	        System.out.println(response.getStatusLine());
    	        System.out.println("Successful login");
    	        System.out.println("  instance URL: "+loginInstanceUrl);
    	        System.out.println("  access token/session ID: "+loginAccessToken);    	        
    	        // release connection
//    	        httpPost.releaseConnection();
   	    	
    	    	soapUri = LOGINURL[4] + SOAP_ENDPOINT + API_VERSION;
    	        config2 = new ConnectorConfig();
        		config2.setAuthEndpoint(soapUri);
        		config2.setServiceEndpoint(soapUri);
        		config2.setSessionId(loginAccessToken);
    			LOGINURLx = LOGINURL[4];
    			USERNAMEx = USERNAME[4];
    			PASSWORDx = PASSWORD[4];
//    			usr.setUser_License__c(null);
//    			usr.setManager_Name__c(null);
    			usr2 = (User) usr;
    			usr = (User) usr2;
//    	    	soapUri = LOGINURL[4] + SOAP_ENDPOINT + API_VERSION;
//    	        config2 = new ConnectorConfig();
//    	        config2.setUsername(USERNAME[4]);
//    	        config2.setPassword(PASSWORD[4]);
//        		config2.setAuthEndpoint(soapUri);
//    			LOGINURLx = LOGINURL[4];
//    			USERNAMEx = USERNAME[4];
//    			PASSWORDx = PASSWORD[4];
    	    } else if (platforms.substring(h, h+1).indexOf("P") != -1) {
    	    	soapUri = LOGINURL[5] + SOAP_ENDPOINT + API_VERSION;
    	        config2 = new ConnectorConfig();
    	        config2.setUsername(USERNAME[5]);
    	        config2.setPassword(PASSWORD[5]);
        		config2.setAuthEndpoint(soapUri);
    			LOGINURLx = LOGINURL[5];
    			USERNAMEx = USERNAME[5];
    			PASSWORDx = PASSWORD[5];
    			usr = usrHold;
    	    }
    	
//    		config2.setAuthEndpoint(soapUri);
    		System.out.println(soapUri);
			System.out.println("Id: " + usr.getId() + " - Name: "+ usr.getFirstName()+" "+ usr.getLastName()+"  username: "+ usr.getUsername()+"  Email: "+ usr.getEmail()+"  userroleid: "+ usr.getUserRoleId()+"  profileid: "+ usr.getProfileId()+"  businessunit: "+ usr.getBusiness_Unit__c()+"  addressvalidation: "+ usr.getAddress_Validation__c()+"  ismmblook: "+ usr.getIsMMBLookupSearch__c()+"  cpqenabled: "+ usr.getCPQEnabled__c()+"  currentincpq: "+ usr.getScpq__CurrentInCPQSystem__c());

    		try {
    			connection2 = new EnterpriseConnection(config2);
    			
				if (roleName.length() > 0) {
    				queryResults2 = connection2.query("Select id, name From userrole Where name = '"+roleName+"' limit 5");
    				if (queryResults2.getSize() == 1) {
            			usr.setUserRoleId(queryResults2.getRecords()[0].getId());
            			System.out.println("roleId: " + usr.getUserRoleId());	
    				} else {status = "InvalidRole";}    				
    			}
				if (profileName.length() > 0) {
            		queryResults2 = connection2.query("Select id, name From profile Where name = '"+profileName+"' limit 5");
            		if (queryResults2.getSize() == 1) {
            			usr.setProfileId(queryResults2.getRecords()[0].getId());
            			System.out.println("profileId: " + usr.getProfileId());	
            		} else {status = "InvalidProfile";}    				
    			}	
				if (managerUserName.length() > 0) {
					queryResults2 = connection2.query("Select id, name From User Where UserName = '"+managerUserName+"' limit 5");        		
    				if (queryResults2.getSize() == 1) {
    					usr.setManagerId(queryResults2.getRecords()[0].getId());
    					System.out.println("managerId: " + usr.getManagerId());  						
    				} else {status = "InvalidManager";}
				}   					
   			
    			
    			
    			upsertResults = connection2.upsert("Username", usrSobj);
    			for (int i=0; i < upsertResults.length; i++) {
    				if (upsertResults[i].isSuccess()) {
    					System.out.println(i+". Successfully created record - Id: " + upsertResults[i].getId());
    					status = upsertResults[i].getId();	

// reset role, profile, manager ids for next platform iteration 
    	    			if (usr.getUserRoleId().length() > 0) {
    	    				queryResults2 = connection2.query("Select name From userrole Where id = '"+usr.getUserRoleId()+"' limit 5");
    	    				if (queryResults2.getSize() == 1) {
    	    					usrRole2 = (UserRole)queryResults2.getRecords()[0];
    	    					roleName = usrRole2.getName();
    	    					System.out.println("roleName: " + roleName);	
    	    				} else {status = "InvalidRole";}
    	    			}	
    	    			if (usr.getProfileId().length() > 0) {
    	    				queryResults2 = connection2.query("Select name From profile Where id = '"+usr.getProfileId()+"' limit 5");
    	    				if (queryResults2.getSize() == 1) {
    	    					prf2 = (Profile)queryResults2.getRecords()[0];
    	    					profileName = prf2.getName();
    	    					System.out.println("profileName: " + profileName);	
    	    				} else {status = "InvalidProfile";}  
    	    			}
    	    			if (usr.getManagerId() != null) {
    	    				queryResults2 = connection2.query("Select username From user Where id = '"+usr.getManagerId()+"' limit 5");
    	    				if (queryResults2.getSize() == 1) {
    	    					usr2 = (User)queryResults2.getRecords()[0];
    	    					managerUserName = usr2.getUsername();
    	    					System.out.println("managerUserName: " + managerUserName);	
    	    				} else {status = "InvalidManagerUserName";} 
    	    			}
    					
    					if (driver == null) {
//    						System.setProperty("webdriver.chrome.driver", "C:/Users/rstiles/Downloads/chromedriver_win32/chromedriver.exe");
    						driver = new ChromeDriver();
    						driver.get(LOGINURLx+"/"+upsertResults[i].getId().substring(0, upsertResults[i].getId().length()-3)+"?noredirect=1&isUserEntityOverride=1");
    						Integer fPos = driver.getPageSource().indexOf("Salesforce Customer Secure Login Page");
    						try {
    							if (fPos > 0) {
    								WebElement username = driver.findElement(By.name("username"));
    								username.sendKeys(USERNAMEx);
    								WebElement password = driver.findElement(By.name("pw"));
    								password.sendKeys(PASSWORDx.substring(0, 8));
    								password.submit();
    							}
    						} catch (org.openqa.selenium.NoSuchElementException e) {}
//    						driver.findElement(By.cssSelector("body")).sendKeys(USERNAMEx);
//    						driver.findElement(By.cssSelector("body")).sendKeys(Keys.TAB);
//    						driver.findElement(By.cssSelector("body")).sendKeys(PASSWORDx);
//    						driver.findElement(By.cssSelector("body")).sendKeys(Keys.ENTER);
    					} else {
//    						driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
    						WebElement body = driver.findElement(By.cssSelector("body"));
    						body.sendKeys(Keys.CONTROL + "t");
    						driver.get(LOGINURLx+"/"+upsertResults[i].getId().substring(0, upsertResults[i].getId().length()-3)+"?noredirect=1&isUserEntityOverride=1");
    						Integer fPos = driver.getPageSource().indexOf("Salesforce Customer Secure Login Page");
    						try {
    							if (fPos > 0) {
    								WebElement username = driver.findElement(By.name("username"));
    								username.sendKeys(USERNAMEx);
    								WebElement password = driver.findElement(By.name("pw"));
    								password.sendKeys(PASSWORDx.substring(0, 8));
    								password.submit();
    							}
    						} catch(org.openqa.selenium.NoSuchElementException e) {}
//    						driver.findElement(By.cssSelector("body")).sendKeys(USERNAMEx);
//    						driver.findElement(By.cssSelector("body")).sendKeys(Keys.TAB);
//    						driver.findElement(By.cssSelector("body")).sendKeys(PASSWORDx);
//    						driver.findElement(By.cssSelector("body")).sendKeys(Keys.ENTER);
//    						WebElement pwBtn = driver.findElement(By.name("pw"));
//    						pwBtn.click();
    					}
    				} else {
    					Error[] errors = upsertResults[i].getErrors();
    					for (int j=0; j <  errors.length; j++) { 
    						System.out.println("ERROR creating record: " + errors[j].getMessage()); 
//        					usr.setAlias(usr.getFirstName().substring(0, 1).toLowerCase()+usr.getLastName().substring(0, 4).toLowerCase()+"ADT");
//        					usr.setUsername(usr.getFirstName().toLowerCase()+usr.getLastName().toLowerCase()+USERNAMEx.substring(USERNAMEx.indexOf("@"), USERNAMEx.length()));
//        					usr.setCommunityNickname(usr.getFirstName().substring(0, 1).toLowerCase()+usr.getLastName().toLowerCase());
//        					System.out.println("Id: " + usr.getId() + " - Name: "+ usr.getFirstName()+" "+ usr.getLastName()+"  username: "+ usr.getUsername()+"  Email: "+ usr.getEmail()+"  userroleid: "+ usr.getUserRoleId()+"  profileid: "+ usr.getProfileId()+"  businessunit: "+ usr.getBusiness_Unit__c()+"  addressvalidation: "+ usr.getAddress_Validation__c()+"  ismmblook: "+ usr.getIsMMBLookupSearch__c()+"  cpqenabled: "+ usr.getCPQEnabled__c()+"  currentincpq: "+ usr.getScpq__CurrentInCPQSystem__c());        					
//        					usrSobj[0] = usr;

    						status = "UpdateFailed"+ errors[j].getMessage();	
    						
//    		    			saveResults = connection2.create(usrSobj);
//    		    			for (int c=0; c < saveResults.length; c++) {
//    		    				if (saveResults[c].isSuccess()) {
//    		    					System.out.println(c+". Successfully created record - Id: " + saveResults[c].getId());
//    		    					status = "Complete";	
//    		    					if (driver == null) {
//    		    						System.setProperty("webdriver.chrome.driver", "C:/Users/rstiles/Downloads/chromedriver_win32/chromedriver.exe");
//    		    						driver = new ChromeDriver();
//    		    						driver.get(LOGINURLx+"/"+saveResults[c].getId().substring(0, saveResults[c].getId().length()-3)+"?noredirect=1&isUserEntityOverride=1");	
//    		    						WebElement username = driver.findElement(By.name("username"));
//    		    						username.sendKeys(USERNAMEx);
//   		    						WebElement password = driver.findElement(By.name("pw"));
//    		    						password.sendKeys(PASSWORDx.substring(0, 8));
//    		    						password.submit();
//    		    					} else {
//    		    						driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
//    		    						driver.get(LOGINURLx+"/"+saveResults[c].getId().substring(0, saveResults[c].getId().length()-3)+"?noredirect=1&isUserEntityOverride=1");
//    		    						WebElement username = driver.findElement(By.name("username"));
//    		    						username.sendKeys(USERNAMEx);
//    		    						WebElement password = driver.findElement(By.name("pw"));
//    		    						password.sendKeys(PASSWORDx.substring(0, 8));
//    		    						password.submit();
//   		    					}
//    		    				} else {
//    		    					Error[] errors2 = saveResults[i].getErrors();
//    		    					for (int b=0; b <  errors2.length; b++) { 
//    		    						System.out.println("ERROR creating record: " + errors2[b].getMessage()); 
//    		        					System.out.println("Id: " + usr.getId() + " - Name: "+ usr.getFirstName()+" "+ usr.getLastName()+"  username: "+ usr.getUsername()+"  Email: "+ usr.getEmail()+"  userroleid: "+ usr.getUserRoleId()+"  profileid: "+ usr.getProfileId()+"  businessunit: "+ usr.getBusiness_Unit__c()+"  addressvalidation: "+ usr.getAddress_Validation__c()+"  ismmblook: "+ usr.getIsMMBLookupSearch__c()+"  cpqenabled: "+ usr.getCPQEnabled__c()+"  currentincpq: "+ usr.getScpq__CurrentInCPQSystem__c());        					

//    		    						status = "UpdateFailed"+ errors2[j].getMessage();	
//    		    					} 
//    		    				} 
//    		    			}         		

    					} 
    				} 
    			}         		

    		} catch (ConnectionException e) {e.printStackTrace();}
    	}
    	
        try {
            connection2.logout();
            if (httpPost != null) {
            	httpPost.releaseConnection();
            }
             System.out.println("Logged out.");
      } catch (ConnectionException ce) {
                 ce.printStackTrace();
      }

    	
    	return status;
 	}
 
    public static void testConnections() {
    	ConnectorConfig config2 = new ConnectorConfig();
    	EnterpriseConnection connection2 = null;
    	
    	for (int h=4; h < 5; h++) {
    		if (h==0) {
    			soapUri = LOGINURL[0] + SOAP_ENDPOINT + API_VERSION;
    			config2 = new ConnectorConfig();
    			config2.setUsername(USERNAME[0]);
    			config2.setPassword(PASSWORD[0]);
        		config2.setAuthEndpoint(soapUri);
    			LOGINURLx = LOGINURL[0];
    			USERNAMEx = USERNAME[0];
    			PASSWORDx = PASSWORD[0];
    		} else if (h==1) {
    			soapUri = LOGINURL[1] + SOAP_ENDPOINT + API_VERSION;
    			config2 = new ConnectorConfig();
    			config2.setUsername(USERNAME[1]);
    			config2.setPassword(PASSWORD[1]);
        		config2.setAuthEndpoint(soapUri);
    			LOGINURLx = LOGINURL[1];
    			USERNAMEx = USERNAME[1];
    			PASSWORDx = PASSWORD[1];
    		} else if (h==2) {
    	    	soapUri = LOGINURL[2] + SOAP_ENDPOINT + API_VERSION;
    	        config2 = new ConnectorConfig();
    	        config2.setUsername(USERNAME[2]);
    	        config2.setPassword(PASSWORD[2]);
        		config2.setAuthEndpoint(soapUri);
    			LOGINURLx = LOGINURL[2];
    			USERNAMEx = USERNAME[2];
    			PASSWORDx = PASSWORD[2];
    	    } else if (h==3) {
    	    	soapUri = LOGINURL[3] + SOAP_ENDPOINT + API_VERSION;
    	        config2 = new ConnectorConfig();
    	        config2.setUsername(USERNAME[3]);
    	        config2.setPassword(PASSWORD[3]);
        		config2.setAuthEndpoint(soapUri);
    			LOGINURLx = LOGINURL[3];
    			USERNAMEx = USERNAME[3];
    			PASSWORDx = PASSWORD[3];
    	    } else if (h==4) {
    	    	
    	    	
    	        HttpClient httpclient = HttpClientBuilder.create().build();
    	        String loginURL = LOGINURL[4] +
    	                          OAUTH2_GRANTSERVICE +
    	                          "&client_id=" + OAUTH2_CLIENTID +
    	                          "&client_secret=" + OAUTH2_CLIENTSECRET+
    	                          "&username=" + USERNAME[4] +
    	                          "&password=" + PASSWORD[4];
    	        HttpPost httpPost = new HttpPost(loginURL);
    	        HttpResponse response = null;
    	        try {response = httpclient.execute(httpPost);} catch (ClientProtocolException cpException) {cpException.printStackTrace();} catch (IOException ioException) {ioException.printStackTrace();}
    	        final int statusCode = response.getStatusLine().getStatusCode();
    	        if (statusCode != HttpStatus.SC_OK) {
    	            System.out.println("Error authenticating to Force.com: "+statusCode);
    	        }
    	        String getResult = null;
    	        try {
    	            getResult = EntityUtils.toString(response.getEntity());
    	        } catch (IOException ioException) {
    	            ioException.printStackTrace();
    	        }
    	        JSONObject jsonObject = null;
    	        String loginAccessToken = null;
    	        String loginInstanceUrl = null;
    	        try {
    	            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
    	            loginAccessToken = jsonObject.getString("access_token");
    	            loginInstanceUrl = jsonObject.getString("instance_url");
    	        } catch (JSONException jsonException) {
    	            jsonException.printStackTrace();
    	        }
    	        System.out.println(response.getStatusLine());
    	        System.out.println("Successful login");
    	        System.out.println("  instance URL: "+loginInstanceUrl);
    	        System.out.println("  access token/session ID: "+loginAccessToken);
    	        
//    	        config.setAuthEndpoint(authResult.getInstanceUrl() + serviceURI);
//    	        config.setSessionId(authResult.getAccessToken());
//    	        config.setServiceEndpoint(authResult.getInstanceUrl() + serviceURI);
    	        // release connection
//    	        httpPost.releaseConnection();

    	    	
    	    	
    	    	soapUri = LOGINURL[4] + SOAP_ENDPOINT + API_VERSION;
    	        config2 = new ConnectorConfig();
        		config2.setAuthEndpoint(soapUri);
        		config2.setServiceEndpoint(soapUri);
        		config2.setSessionId(loginAccessToken);
    			LOGINURLx = LOGINURL[4];
    			USERNAMEx = USERNAME[4];
    			PASSWORDx = PASSWORD[4];
    	    } else if (h==5) {
    	    	soapUri = LOGINURL[5] + SOAP_ENDPOINT + API_VERSION;
    	        config2 = new ConnectorConfig();
    	        config2.setUsername(USERNAME[5]);
    	        config2.setPassword(PASSWORD[5]);
        		config2.setAuthEndpoint(soapUri);
    			LOGINURLx = LOGINURL[5];
    			USERNAMEx = USERNAME[5];
    			PASSWORDx = PASSWORD[5];
    	    }
    	
//    		config2.setAuthEndpoint(soapUri);

    		try {
    			connection2 = new EnterpriseConnection(config2);
    	        QueryResult queryResults = connection2.query("SELECT Id, FirstName, LastName FROM User Where LastName = 'Stiles' LIMIT 1");
    	        if (queryResults.getSize() > 0) {
    	        	System.out.println(soapUri);
    	        	System.out.println(queryResults.toString());
    	        }   			
    		} catch (ConnectionException e) {System.out.println(soapUri);e.printStackTrace();}
    	}
    	
        try {
            connection2.logout();
             System.out.println("Logged out.");
      } catch (ConnectionException ce) {
                 ce.printStackTrace();
      }
 	}
    
    public static void describeSObjects() 
    {
    	ConnectorConfig config3 = new ConnectorConfig();
    	EnterpriseConnection connection3 = null;
        System.out.println("\n_______________ Get User Object Fields _______________");
        System.out.println(soapUri);
         
        try {
         	soapUri = LOGINURL[0] + SOAP_ENDPOINT + API_VERSION;
        	config3 = new ConnectorConfig();
        	config3.setUsername(USERNAME[0]);
        	config3.setPassword(PASSWORD[0]);       	
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
    
    // queries and displays the 5 newest leads
    private static void queryLeads() {
 
      System.out.println("Querying for the 5 newest Leads...");
      System.out.println("Select "+queryFields+" From User Where LastName = 'Stiles' limit 5");
      try {
 
        // query for the 5 newest Leads
//          QueryResult queryResults = connection.query("Select id, name, firstname, lastname From User Where LastName = 'Stiles' limit 5");
        QueryResult queryResults = connection.query("Select "+queryFields+" From User Where LastName = 'Stiles' limit 5");
//        QueryResult queryResults = connection.query("SELECT Id, FirstName, LastName, Company FROM Lead ORDER BY CreatedDate DESC LIMIT 5");
        if (queryResults.getSize() > 0) {
          for (int i=0;i < 5;i++){
            // cast the SObject to a strongly-typed Lead
//            Lead l = (Lead)queryResults.getRecords()[i];
//            System.out.println("Id: " + l.getId() + " - Name: "+ l.getFirstName()+" "+ l.getLastName()+" - Company: "+l.getCompany());
          }
          System.out.println(queryResults.toString());
        }
 
      } catch (Exception e) {
        e.printStackTrace();
      }    
 
    }
 
    // create 5 test Leads
    private static void createLeads() {
      System.out.println("Creating 5 new test Leads...");
      Lead[] records = new Lead[5];
      try {
        // create 5 test leads
    	  for (int i=0;i < 5;i++) {
    		  Lead l = new Lead();
    		  l.setFirstName("SOAP API");
    		  l.setLastName("Lead "+i);
    		  l.setCompany("asagarwal.com");
    		  records[i] = l;
    	  }
    	  // create the records in Salesforce.com
    	  SaveResult[] saveResults = connection.create(records);
    	  // check the returned results for any errors
    	  for (int i=0; i <  saveResults.length; i++) {
    		  if (saveResults[i].isSuccess()) {
    			  System.out.println(i+". Successfully created record - Id: " + saveResults[i].getId());
    		  } else {
    			  Error[] errors = saveResults[i].getErrors();
    			  for (int j=0; j <  errors.length; j++) { 
    				  System.out.println("ERROR creating record: " + errors[j].getMessage()); 
    			  } 
    		  } 
    	  } 
      } catch (Exception e) { e.printStackTrace(); } 
    } 
    
    // updates the 5 newly created Leads 
    private static void updateLeads() { System.out.println("Update the 5 new test leads..."); 
    	Lead[] records = new Lead[5]; 
    	try { QueryResult queryResults = connection.query("SELECT Id, FirstName, LastName, Company FROM Lead ORDER BY CreatedDate DESC LIMIT 5"); 
    		if (queryResults.getSize() > 0) {
    			for (int i=0;i < 5;i++){
    				// cast the SObject to a strongly-typed Lead
    				Lead l = (Lead)queryResults.getRecords()[i];
    				System.out.println("Updating Id: " + l.getId() + " - Name: "+l.getFirstName()+" "+l.getLastName());
    				// modify the name of the Lead
    				l.setLastName(l.getLastName()+" -- UPDATED");
    				records[i] = l;
    			}
    		}
        // update the records in Salesforce.com
        SaveResult[] saveResults = connection.update(records);
        // check the returned results for any errors
        for (int i=0; i <  saveResults.length; i++) {
          if (saveResults[i].isSuccess()) {
            System.out.println(i+". Successfully updated record - Id: " + saveResults[i].getId());
          } else {
            Error[] errors = saveResults[i].getErrors();
            for (int j=0; j <  errors.length; j++) { 
            	System.out.println("ERROR updating record: " + errors[j].getMessage()); 
            } 
          } 
        } 
      } catch (Exception e) { e.printStackTrace(); } 
    } 

    
    // delete the 2 newly created Leads 
    private static void deleteLeads() { 
    	System.out.println("Deleting the 2 new test Leads..."); 
    	String[] ids = new String[2]; 
    	try { QueryResult queryResults = connection.query("SELECT Id, Name FROM Lead ORDER BY CreatedDate DESC LIMIT 2"); 
    		if (queryResults.getSize() > 0) {
    			for (int i=0;i < queryResults.getRecords().length;i++) {
    				// cast the SObject to a strongly-typed Lead
    				Lead l = (Lead)queryResults.getRecords()[i];
    				// add the Lead Id to the array to be deleted
    				ids[i] = l.getId();
    				System.out.println("Deleting Id: " + l.getId() + " - Name: "+l.getFirstName()+" "+l.getLastName());
    			}
    		}
    		// delete the records in Salesforce.com by passing an array of Ids
    		DeleteResult[] deleteResults = connection.delete(ids);
    		// check the results for any errors
    		for (int i=0; i <  deleteResults.length; i++) {
    			if (deleteResults[i].isSuccess()) {
    				System.out.println(i+". Successfully deleted record - Id: " + deleteResults[i].getId());
    			} else {
    				Error[] errors = deleteResults[i].getErrors();
    				for (int j=0; j <  errors.length; j++) {
    					System.out.println("ERROR deleting record: " + errors[j].getMessage());
    				}
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}    
    }
 
  }
