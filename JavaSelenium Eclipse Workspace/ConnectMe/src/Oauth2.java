
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
 
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
import com.sforce.ws.ConnectorConfig;
import com.sforce.soap.enterprise.DescribeSObjectResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.Field;
import com.sforce.soap.enterprise.PicklistEntry;
import com.sforce.ws.ConnectionException;
import org.json.JSONException;
 
public class Oauth2 {
 
    static final String USERNAME     = "xxxxxxxx@adt.com.practice";
    static final String PASSWORD     = "xxxxxxxxdOYWyybG4StbKC6fewTGMcN6S";
    static final String LOGINURL     = "https://adt--practice.cs50.my.salesforce.com";
    static final String CLIENTID     = "3MVG9snqYUvtJB1OdYKXZIn..NM023QRf5UoANpmBrJq3bDgV_OFmGHCOyHIW1ubOGMLUJvPcdn_m5V34XJR1";
    static final String CLIENTSECRET = "05138AAAC20EB9D33D1ED36421A3C85D8742BF02D988A6D8F833F001BD9D218B";   
    static final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";    
    private static String REST_ENDPOINT = "/services/data" ;
    private static String API_VERSION = "/v46.0" ;
    private static String baseUri;
    private static Header oauthHeader;
    private static Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
    private static String leadId ;
    private static String leadFirstName;
    private static String leadLastName;
    private static String leadEmail;
    
    private static String soapUri;
    private static String SOAP_ENDPOINT = "/services/Soap/c" ;
    private static String WS_VERSION = "/46.0" ;
    private static EnterpriseConnection connection;
    private static Field[] fields;
    private static String queryFields = "";
    
    private static PrintWriter out;

    public static void main(String[] args) {
 
        HttpClient httpclient = HttpClientBuilder.create().build();
 
        // Assemble the login request URL
        String loginURL = LOGINURL +
                          GRANTSERVICE +
                          "&client_id=" + CLIENTID +
                          "&client_secret=" + CLIENTSECRET +
                          "&username=" + USERNAME +
                          "&password=" + PASSWORD;
 
        // Login requests must be POSTs
        HttpPost httpPost = new HttpPost(loginURL);
        HttpResponse response = null;
 
        try {
            // Execute the login POST request
            response = httpclient.execute(httpPost);
        } catch (ClientProtocolException cpException) {
            cpException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
 
        // verify response is HTTP OK
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("Error authenticating to Force.com: "+statusCode);
            // Error is in EntityUtils.toString(response.getEntity())
            return;
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
 
        baseUri = loginInstanceUrl + REST_ENDPOINT + API_VERSION ;
        oauthHeader = new BasicHeader("Authorization", "OAuth " + loginAccessToken) ;
        System.out.println("oauthHeader1: " + oauthHeader);
        System.out.println("\n" + response.getStatusLine());
        System.out.println("Successful login");
        System.out.println("instance URL: "+loginInstanceUrl);
        System.out.println("access token/session ID: "+loginAccessToken);
        System.out.println("baseUri: "+ baseUri);        
 
        // Run codes to query, isnert, update and delete records in Salesforce using REST API
        describeSObjects();
        queryLeads();
//        createLeads();
//        updateLeads();
//        deleteLeads();      
 
        // release connection
        httpPost.releaseConnection();
    }
 
    // Query Leads using REST HttpGet
    public static void queryLeads() {
        System.out.println("\n_______________ Lead QUERY _______________");
        try {
 
            //Set up the HTTP objects needed to make the request.
            HttpClient httpClient = HttpClientBuilder.create().build();
//            String uri = baseUri + "/query?q=Select+Id,+Username,+LastName,+FirstName,+Name,+CompanyName,+Division,+Department,+Title,+Street,+City,+State,+PostalCode,+Country,+Latitude,+Longitude,+Address,+Email,+EmailPreferencesAutoBcc,+EmailPreferencesAutoBccStayInTouch,+EmailPreferencesStayInTouchReminder,+SenderEmail,+SenderName,+Signature,+StayInTouchSubject,+StayInTouchSignature,+StayInTouchNote,+Phone,+Fax,+MobilePhone,+Alias,+CommunityNickname,+BadgeText,+IsActive,+TimeZoneSidKey,+UserRoleId,+LocaleSidKey,+ReceivesInfoEmails,+ReceivesAdminInfoEmails,+EmailEncodingKey,+ProfileId,+UserType,+LanguageLocaleKey,+EmployeeNumber,+DelegatedApproverId,+ManagerId,+LastLoginDate,+LastPasswordChangeDate,+CreatedDate,+CreatedById,+LastModifiedDate,+LastModifiedById,+SystemModstamp,+OfflineTrialExpirationDate,+OfflinePdaTrialExpirationDate,+UserPermissionsMarketingUser,+UserPermissionsOfflineUser,+UserPermissionsAvantgoUser,+UserPermissionsCallCenterAutoLogin,+UserPermissionsMobileUser,+UserPermissionsSFContentUser,+UserPermissionsKnowledgeUser,+UserPermissionsInteractionUser,+UserPermissionsSupportUser,+UserPermissionsChatterAnswersUser,+UserPreferencesActivityRemindersPopup,+UserPreferencesEventRemindersCheckboxDefault,+UserPreferencesTaskRemindersCheckboxDefault,+UserPreferencesReminderSoundOff,+UserPreferencesDisableAllFeedsEmail,+UserPreferencesDisableFollowersEmail,+UserPreferencesDisableProfilePostEmail,+UserPreferencesDisableChangeCommentEmail,+UserPreferencesDisableLaterCommentEmail,+UserPreferencesDisProfPostCommentEmail,+UserPreferencesContentNoEmail,+UserPreferencesContentEmailAsAndWhen,+UserPreferencesApexPagesDeveloperMode,+UserPreferencesHideCSNGetChatterMobileTask,+UserPreferencesDisableMentionsPostEmail,+UserPreferencesDisMentionsCommentEmail,+UserPreferencesHideCSNDesktopTask,+UserPreferencesHideChatterOnboardingSplash,+UserPreferencesHideSecondChatterOnboardingSplash,+UserPreferencesDisCommentAfterLikeEmail,+UserPreferencesDisableLikeEmail,+UserPreferencesSortFeedByComment,+UserPreferencesDisableMessageEmail,+UserPreferencesDisableBookmarkEmail,+UserPreferencesDisableSharePostEmail,+UserPreferencesEnableAutoSubForFeeds,+UserPreferencesDisableFileShareNotificationsForApi,+UserPreferencesShowTitleToExternalUsers,+UserPreferencesShowManagerToExternalUsers,+UserPreferencesShowEmailToExternalUsers,+UserPreferencesShowWorkPhoneToExternalUsers,+UserPreferencesShowMobilePhoneToExternalUsers,+UserPreferencesShowFaxToExternalUsers,+UserPreferencesShowStreetAddressToExternalUsers,+UserPreferencesShowCityToExternalUsers,+UserPreferencesShowStateToExternalUsers,+UserPreferencesShowPostalCodeToExternalUsers,+UserPreferencesShowCountryToExternalUsers,+UserPreferencesShowProfilePicToGuestUsers,+UserPreferencesShowTitleToGuestUsers,+UserPreferencesShowCityToGuestUsers,+UserPreferencesShowStateToGuestUsers,+UserPreferencesShowPostalCodeToGuestUsers,+UserPreferencesShowCountryToGuestUsers,+UserPreferencesPipelineViewHideHelpPopover,+UserPreferencesDisableEndorsementEmail,+UserPreferencesPathAssistantCollapsed,+UserPreferencesCacheDiagnostics,+UserPreferencesShowEmailToGuestUsers,+UserPreferencesShowManagerToGuestUsers,+UserPreferencesShowWorkPhoneToGuestUsers,+UserPreferencesShowMobilePhoneToGuestUsers,+UserPreferencesShowFaxToGuestUsers,+UserPreferencesShowStreetAddressToGuestUsers,+UserPreferencesLightningExperiencePreferred,+UserPreferencesPreviewLightning,+UserPreferencesHideEndUserOnboardingAssistantModal,+UserPreferencesHideLightningMigrationModal,+UserPreferencesHideSfxWelcomeMat,+UserPreferencesHideBiggerPhotoCallout,+UserPreferencesGlobalNavBarWTShown,+UserPreferencesGlobalNavGridMenuWTShown,+UserPreferencesCreateLEXAppsWTShown,+UserPreferencesFavoritesWTShown,+UserPreferencesRecordHomeSectionCollapseWTShown,+UserPreferencesRecordHomeReservedWTShown,+UserPreferencesFavoritesShowTopFavorites,+UserPreferencesExcludeMailAppAttachments,+UserPreferencesSuppressTaskSFXReminders,+UserPreferencesSuppressEventSFXReminders,+UserPreferencesPreviewCustomTheme,+UserPreferencesHasCelebrationBadge,+UserPreferencesUserDebugModePref,+UserPreferencesNewLightningReportRunPageEnabled,+ContactId,+AccountId,+CallCenterId,+Extension,+FederationIdentifier,+AboutMe,+FullPhotoUrl,+SmallPhotoUrl,+DigestFrequency,+DefaultGroupNotificationFrequency,+LastViewedDate,+LastReferencedDate,+ASAP_Agent_Id__c,+Business_Unit__c,+DeviceID__c,+DevicePhoneNumber__c,+IncomingManager__c,+User_Status__c,+RegionNumber__c,+AreaNumber__c,+DistrictNumber__c,+TownNumber__c,+Count__c,+EmployeeNumber__c,+Qualification__c,+Region_Id__c,+StartDateinCurrentJob__c,+Chatter_Champion__c,+Terms_and_Conditions_Accepted__c,+scpq__CPQUserKey__c,+scpq__CurrentInCPQSystem__c,+scpq__LastCPQSyncDate__c,+scpq__LastCPQSyncSuccessful__c,+Address_Validation__c,+Commission_ID__c,+State_License__c,+isMMBLookupSearch__c,+Approver_Level__c,+ChatterExcluded__c,+CPQEnabled__c,+DelegatedEmail__c,+Profile_Type__c,+User_License__c,+Hierarchy_Created__c,+Agent_Type__c,+SMS_Enabled_Last_Updated__c,+SMS_Enabled__c,+License_Expiration_date__c,+Manager_Name__c,+eContractEligible__c,+Profile_Name__c,+ExtraSkills__c,+Rep_Team__c,+CheckMyHierarchy__c,+SimpleSell__c,+GNCC__CC_EnvironmentUrl__c+From+User+Where+LastName+=+'Stiles'+limit+5";

            String uri = baseUri + "/query?q=Select+"+queryFields+"From+User+Where+LastName+=+'Stiles'+limit+5";
            
            System.out.println("Query URL: " + uri);
            HttpGet httpGet = new HttpGet(uri);
            System.out.println("oauthHeader2: " + oauthHeader);
            httpGet.addHeader(oauthHeader);
            httpGet.addHeader(prettyPrintHeader);
 
            // Make the request.
            HttpResponse response = httpClient.execute(httpGet);
 
            // Process the result
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String response_string = EntityUtils.toString(response.getEntity());
                try {
                    JSONObject json = new JSONObject(response_string);
                    System.out.println("JSON result of Query:\n" + json.toString(1));
                    JSONArray j = json.getJSONArray("records");
                    for (int i = 0; i < j.length(); i++){
                        leadId = json.getJSONArray("records").getJSONObject(i).getString("Id");
                        leadFirstName = json.getJSONArray("records").getJSONObject(i).getString("FirstName");
                        leadLastName = json.getJSONArray("records").getJSONObject(i).getString("LastName");
                        leadEmail = json.getJSONArray("records").getJSONObject(i).getString("Email");
                        System.out.println("Lead record is: " + i + ". " + leadId + " " + leadFirstName + " " + leadLastName + " " + leadEmail + "");
                    }
                } catch (JSONException je) {
                    je.printStackTrace();
                }
            } else {
                System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
                System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
                System.out.println(getBody(response.getEntity().getContent()));
                System.exit(-1);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }
 
    // Create Leads using REST HttpPost
    public static void createLeads() {
        System.out.println("\n_______________ Lead INSERT _______________");
 
        String uri = baseUri + "/sobjects/Lead/";
        try {
 
            //create the JSON object containing the new lead details.
            JSONObject lead = new JSONObject();
            lead.put("FirstName", "REST API");
            lead.put("LastName", "Lead");
            lead.put("Company", "asagarwal.com");
 
            System.out.println("JSON for lead record to be inserted:\n" + lead.toString(1));
 
            //Construct the objects needed for the request
            HttpClient httpClient = HttpClientBuilder.create().build();
 
            HttpPost httpPost = new HttpPost(uri);
            httpPost.addHeader(oauthHeader);
            httpPost.addHeader(prettyPrintHeader);
            // The message we are going to post
            StringEntity body = new StringEntity(lead.toString(1));
            body.setContentType("application/json");
            httpPost.setEntity(body);
 
            //Make the request
            HttpResponse response = httpClient.execute(httpPost);
 
            //Process the results
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 201) {
                String response_string = EntityUtils.toString(response.getEntity());
                JSONObject json = new JSONObject(response_string);
                // Store the retrieved lead id to use when we update the lead.
                leadId = json.getString("id");
                System.out.println("New Lead id from response: " + leadId);
            } else {
                System.out.println("Insertion unsuccessful. Status code returned is " + statusCode);
            }
        } catch (JSONException e) {
            System.out.println("Issue creating JSON or processing results");
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }
 
    // Update Leads using REST HttpPatch. We have to create the HTTPPatch, as it does not exist in the standard library
    // Since the PATCH method was only recently standardized and is not yet implemented in Apache HttpClient
    public static void updateLeads() {
        System.out.println("\n_______________ Lead UPDATE _______________");
 
        //Notice, the id for the record to update is part of the URI, not part of the JSON
        String uri = baseUri + "/sobjects/Lead/" + leadId;
        try {
            //Create the JSON object containing the updated lead last name
            //and the id of the lead we are updating.
            JSONObject lead = new JSONObject();
            lead.put("LastName", "Lead --UPDATED");
            System.out.println("JSON for update of lead record:\n" + lead.toString(1));
 
            //Set up the objects necessary to make the request.
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpClient httpClient = HttpClientBuilder.create().build();
 
            HttpPatch httpPatch = new HttpPatch(uri);
            httpPatch.addHeader(oauthHeader);
            httpPatch.addHeader(prettyPrintHeader);
            StringEntity body = new StringEntity(lead.toString(1));
            body.setContentType("application/json");
            httpPatch.setEntity(body);
 
            //Make the request
            HttpResponse response = httpClient.execute(httpPatch);
 
            //Process the response
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 204) {
                System.out.println("Updated the lead successfully.");
            } else {
                System.out.println("Lead update NOT successfully. Status code is " + statusCode);
            }
        } catch (JSONException e) {
            System.out.println("Issue creating JSON or processing results");
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }
 
    // Extend the Apache HttpPost method to implement an HttpPatch
    private static class HttpPatch extends HttpPost {
        public HttpPatch(String uri) {
            super(uri);
        }
 
        public String getMethod() {
            return "PATCH";
        }
    }
 
    // Update Leads using REST HttpDelete (We have to create the HTTPDelete, as it does not exist in the standard library.)
    public static void deleteLeads() {
        System.out.println("\n_______________ Lead DELETE _______________");
 
        //Notice, the id for the record to update is part of the URI, not part of the JSON
        String uri = baseUri + "/sobjects/Lead/" + leadId;
        try {
            //Set up the objects necessary to make the request.
            HttpClient httpClient = HttpClientBuilder.create().build();
 
            HttpDelete httpDelete = new HttpDelete(uri);
            httpDelete.addHeader(oauthHeader);
            httpDelete.addHeader(prettyPrintHeader);
 
            //Make the request
            HttpResponse response = httpClient.execute(httpDelete);
 
            //Process the response
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 204) {
                System.out.println("Deleted the lead successfully.");
            } else {
                System.out.println("Lead delete NOT successful. Status code is " + statusCode);
            }
        } catch (JSONException e) {
            System.out.println("Issue creating JSON or processing results");
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }
 
    private static String getBody(InputStream inputStream) {
        String result = "";
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inputStream)
            );
            String inputLine;
            while ( (inputLine = in.readLine() ) != null ) {
                result += inputLine;
                result += "\n";
            }
            in.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }
    
    public static void describeSObjects() 
    {
    	soapUri = LOGINURL + SOAP_ENDPOINT + WS_VERSION;
        System.out.println("\n_______________ Get User Object Fields _______________");
        System.out.println(soapUri);
        
        ConnectorConfig config = new ConnectorConfig();
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setAuthEndpoint(soapUri);
 
        try {
        	connection = new EnterpriseConnection(config);

               // display some current settings
              System.out.println("Auth EndPoint: "+config.getAuthEndpoint());
              System.out.println("Service EndPoint: "+config.getServiceEndpoint());
              System.out.println("Username: "+config.getUsername());
              System.out.println("SessionId: "+config.getSessionId());
 
              // run the different examples


              DescribeSObjectResult dsor = connection.describeSObject("User");
              Field[] fields = dsor.getFields();
              if (dsor.isActivateable()) System.out.println("\tActivateable");
                      
              // Iterate through the fields to get properties for each field
              for(int j=0;j < fields.length; j++) {  
            	  queryFields = queryFields + fields[j].getName() + ",+";
            	  Field field = fields[j];
                  System.out.println("\tField: " + field.getName());
                  System.out.println("\t\tLabel: " + field.getLabel());
                  if (field.isCustom()) System.out.println("\t\tThis is a custom field.");
                  System.out.println("\t\tDepHid: " + field.getDeprecatedAndHidden());
                  System.out.println("\t\tType: " + field.getType());
                  if (field.getLength() > 0) System.out.println("\t\tLength: " + field.getLength());
                  if (field.getPrecision() > 0) System.out.println("\t\tPrecision: " + field.getPrecision());
                          
                  // Determine whether this is a picklist field
                  if (field.getType().toString().toUpperCase() == "PICKLIST") {                            
                	  // Determine whether there are picklist values
                      PicklistEntry[] picklistValues = field.getPicklistValues();
                      if (picklistValues != null && picklistValues[0] != null) {
                    	  System.out.println("\t\tPicklist values = ");
                          for (int k = 0; k < picklistValues.length; k++) {
                        	  System.out.println("\t\t\tItem: " + picklistValues[k].getLabel());
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
                        	  System.out.println("\t\t\t" + referenceTos[k]);
                          }             
                     }
                  }
              }
              queryFields = queryFields.substring(0, queryFields.lastIndexOf(",")) + "+";
        } catch (ConnectionException e1) {
            e1.printStackTrace();
        }  
 
        try {
             connection.logout();
              System.out.println("Logged out.");
       } catch (ConnectionException ce) {
                  ce.printStackTrace();
       }   
    }  
}