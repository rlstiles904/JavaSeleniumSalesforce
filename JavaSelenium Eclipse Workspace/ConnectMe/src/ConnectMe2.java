import org.openqa.selenium.By; 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.*;
import java.io.*;

 public class ConnectMe2 {
 
	static String pathToFile = System.getProperty("user.dir");
	 
		public static void main(String[] args) throws Exception {
			pathToFile = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
			pathToFile = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
			pathToFile = pathToFile + "\\Documents\\SalesforceIds.csv";
//			buildRepList();
			ConnectMe2 cm2 = new ConnectMe2();
			if(cm2.getRepList()) {
//				ConnectMe2 http = new ConnectMe2();
//				System.out.println("Start ConnectMe WebDriver Request");
//				http.validateCreateRep();
			} else {System.out.println("Error loading rep list");}
		}
	 
		public boolean getRepList() {
			boolean status = false;
			List<List<String>> records = new ArrayList<>();
			List<List<String>> records2 = new ArrayList<>();
			File csvFile = new File(pathToFile);  
			if (csvFile.isFile()) {
				try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
					String line;
					while ((line = br.readLine()) != null) {
						String[] values = line.split(",");
						if(values.length == 12) {
							values = new String[] {values[0],values[1],values[2],values[3],values[4],values[5],values[6],values[7],values[8],values[9],values[10],values[11],"","","","","","","","","","","","",""};
						} 
						if(values[0].indexOf("FirstName") == 0) {
							records2.add(Arrays.asList(values));
						} else {
							if(values[12].length() == 0) {
								ConnectMe2 http = new ConnectMe2();
								values = http.validateCreateRep(values);
							}
							records.add(Arrays.asList(values));
							status = true;
						}
					}
				} catch (FileNotFoundException e) {status=false;e.printStackTrace();} catch (IOException e) {status=false;e.printStackTrace();}
			
				FileWriter csvWriter;
				try {csvWriter = new FileWriter(pathToFile);				  
					status = true;
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
				} catch (IOException e1) {status=false;e1.printStackTrace();}		
				
			}
			return status;
		}
		
		public static boolean buildRepList() {
			boolean status = false;
			
			List<List<String>> rows = Arrays.asList(  
//				    Arrays.asList("Richard", "Stiles", "", "Raul", "Sumbeling", "", "", "", "123PS", ""),
				    Arrays.asList("", "", "ChandanaAkula@adt.com", "", "", "", "role", "profile","123P", "")
				);

				FileWriter csvWriter;
				try {csvWriter = new FileWriter(pathToFile);				  
					csvWriter.append("FirstName");  
					csvWriter.append(",");  
					csvWriter.append("LastName");  
					csvWriter.append(",");  
					csvWriter.append("Email");  
					csvWriter.append(",");  
					csvWriter.append("MirrorFirstName");  
					csvWriter.append(",");  
					csvWriter.append("MirrorLastName");  
					csvWriter.append(",");  
					csvWriter.append("MirrorEmail");  
					csvWriter.append(",");  
					csvWriter.append("ManagerFirstName");  
					csvWriter.append(",");  
					csvWriter.append("ManagerLastName");  
					csvWriter.append(",");  
					csvWriter.append("ManagerEmail");  
					csvWriter.append(",");  
					csvWriter.append("Role");  
					csvWriter.append(",");  
					csvWriter.append("Profile");  
					csvWriter.append(",");  
					csvWriter.append("Platforms");  
					csvWriter.append(",");  
					csvWriter.append("Status");  
					csvWriter.append(",");					
					csvWriter.append("Con-FirstName");  
					csvWriter.append(",");  
					csvWriter.append("Con-LastName");  
					csvWriter.append(",");  
					csvWriter.append("Con-Title");  
					csvWriter.append(",");  
					csvWriter.append("Con-Email");  
					csvWriter.append(",");  
					csvWriter.append("Con-HrId");  
					csvWriter.append(",");  
					csvWriter.append("Con-AccountName");  
					csvWriter.append(",");  
					csvWriter.append("Con-EmployeeType");  
					csvWriter.append(",");  
					csvWriter.append("Con-EmployeeStatus");  
					csvWriter.append(",");  
					csvWriter.append("Con-LOB");  
					csvWriter.append(",");  
					csvWriter.append("Con-Manager");  
					csvWriter.append(",");  
					csvWriter.append("Con-OfficeLocation");  
					csvWriter.append(",");  
					csvWriter.append("Con-OfficePhone");  					
					csvWriter.append("\n");
					status = true;
					for (List<String> rowData : rows) {  
						csvWriter.append(String.join(",", rowData));
						csvWriter.append("\n");
					}
					csvWriter.flush();  
					csvWriter.close();  
				} catch (IOException e1) {status=false;e1.printStackTrace();}
			
			return status;
		}
		
		public String[] validateCreateRep(String[] repData) {
//			System.setProperty("webdriver.chrome.driver", "C:/Users/rstiles/Downloads/chromedriver_win32/chromedriver.exe");

			WebDriver driver = new ChromeDriver();
			String rep = "";
			if(repData[2].length() > 0) {
				driver.get("https://connectme.adt.com/IdentityManagement/aspx/common/GlobalSearchResult.aspx?searchtype=a35e1181-6020-4c13-b0c8-e8b18692359c&content=" + repData[2].subSequence(0, repData[2].indexOf("@")));	
			} else {
				driver.get("https://connectme.adt.com/IdentityManagement/aspx/common/GlobalSearchResult.aspx?searchtype=a35e1181-6020-4c13-b0c8-e8b18692359c&content=" + repData[1].trim() + ", " + repData[0].trim());				
			}
//			try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}

			Integer fPos = driver.getPageSource().indexOf("EditPerson.aspx?id=");
			Integer lPos = driver.getPageSource().lastIndexOf("EditPerson.aspx?id=");
			Integer count = 0;
			String firstNamex = "";
			while ((fPos.intValue() == -1 && count.intValue() < 10) || (fPos.intValue() != -1 && fPos.intValue() != lPos.intValue() && count.intValue() < 10)) {
				System.out.println("problem encountered... waiting for user action");
				try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
				try {
					firstNamex = driver.findElement(By.name("ctl00$PlaceHolderMain$GlobalSearchResult$ResourceListView$listViewSearchControl$ctl00$searchContentTextBoxID")).getAttribute("value");
					fPos = driver.getPageSource().indexOf("EditPerson.aspx?id=");
					lPos = driver.getPageSource().lastIndexOf("EditPerson.aspx?id=");
				} catch (org.openqa.selenium.NoSuchElementException e) {System.out.println("error... ");}
				System.out.println("rep search keyed... "+fPos+","+lPos+"/"+ firstNamex);
				count++;				
			}
			
			if (fPos == driver.getPageSource().lastIndexOf("EditPerson.aspx?id=") && fPos != -1) {
				lPos = driver.getPageSource().substring(fPos).indexOf("'");
				
				String src = driver.getPageSource().substring(fPos, driver.getPageSource().lastIndexOf("</td><td class=\"newListViewCell ms-vb\">"));
				String officePhone = src.substring(src.lastIndexOf("</td><td class=\"newListViewCell ms-vb\">")+39);
				src = src.substring(0, src.lastIndexOf("</td><td class=\"newListViewCell ms-vb\">"));
				String officeLocation = src.substring(src.lastIndexOf("</td><td class=\"newListViewCell ms-vb\">")+39);				
				
				driver.get("https://connectme.adt.com/identitymanagement/aspx/Users/"+driver.getPageSource().substring(fPos, fPos+lPos));
//				try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}

				WebElement we1 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_EmployeeID_control_internalReadOnlyTextBox"));
				String hrId = we1.getText();
				WebElement we2 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_FirstName_control_internalReadOnlyTextBox"));
				String firstName = we2.getText();
				WebElement we3 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_LastName_control_internalReadOnlyTextBox"));
				String lastName = we3.getText();
				WebElement we4 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_AccountName_control_internalReadOnlyTextBox"));
				String accountName = we4.getText();
				WebElement we5 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_EmployeeType_control_internalReadOnlyDateTimeEditor"));
				String employeeType = we5.getText();
				WebElement we6 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_EmployeeStatus2_control_internalReadOnlyDateTimeEditor"));
				String employeeStatus = we6.getText();
				WebElement we7 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_Email_control"));
				String email = we7.getText();
//				WebElement we8 = driver.findElement(By.id("ctl00$PlaceHolderMain$EditPerson$uoc$BasicInfo_grouping$Manager_control$internalObjectPicker$ctl00$hiddenTextBox"));
//				String manager = we8.getText();
				WebElement we9 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_JobTitle_control_internalReadOnlyTextBox"));
				String jobTitle = we9.getText();
				WebElement we10 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_LOB_Unit_control_internalReadOnlyDateTimeEditor"));
				String loB = we10.getText();
				src = driver.getPageSource().substring(driver.getPageSource().lastIndexOf("tabIndex")+14);
				String manager = src.substring(0,src.indexOf("span")-2);
				manager = manager.substring(manager.indexOf(",")+1, manager.length()) + " " + manager.substring(0, manager.indexOf(","));
				
				System.out.println("Success");	
				repData[12] = "Success";	
				repData[0] = firstName;				
				repData[1] = lastName;								
				repData[2] = email;				
				repData[13] = firstName;				
				repData[14] = lastName;				
				repData[15] = jobTitle;				
				repData[16] = email;				
				repData[17] = hrId;				
				repData[18] = accountName;				
				repData[19] = employeeType;				
				repData[20] = employeeStatus;				
				repData[21] = loB;				
				repData[22] = manager;				
				repData[23] = officeLocation;				
				repData[24] = officePhone;			
				
// mirror user logic				
	        	if (repData[5].length() > 0 && repData[3].length() == 0 && repData[4].length() == 0) {
	        		driver.get("https://connectme.adt.com/IdentityManagement/aspx/common/GlobalSearchResult.aspx?searchtype=a35e1181-6020-4c13-b0c8-e8b18692359c&content=" + repData[5].subSequence(0, repData[5].indexOf("@")));   
	        		
	    			fPos = driver.getPageSource().indexOf("EditPerson.aspx?id=");
	    			lPos = driver.getPageSource().lastIndexOf("EditPerson.aspx?id=");
	    			count = 0;
	    			firstNamex = "";
	    			while ((fPos.intValue() == -1 && count.intValue() < 10) || (fPos.intValue() != -1 && fPos.intValue() != lPos.intValue() && count.intValue() < 10)) {
	    				System.out.println("problem encountered... waiting for user action");
	    				try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
	    				try {
	    					firstNamex = driver.findElement(By.name("ctl00$PlaceHolderMain$GlobalSearchResult$ResourceListView$listViewSearchControl$ctl00$searchContentTextBoxID")).getAttribute("value");
	    					fPos = driver.getPageSource().indexOf("EditPerson.aspx?id=");
	    					lPos = driver.getPageSource().lastIndexOf("EditPerson.aspx?id=");
	    				} catch (org.openqa.selenium.NoSuchElementException e) {System.out.println("error... ");}
	    				System.out.println("mirror search keyed... "+ firstNamex);
	    				count++;				
	    			}
	    			
	    			if (fPos == driver.getPageSource().lastIndexOf("EditPerson.aspx?id=") && fPos != -1) {
	    				lPos = driver.getPageSource().substring(fPos).indexOf("'");				    				
	    				driver.get("https://connectme.adt.com/identitymanagement/aspx/Users/"+driver.getPageSource().substring(fPos, fPos+lPos));
	    				we2 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_FirstName_control_internalReadOnlyTextBox"));
	    				repData[3] = we2.getText();
	    				we3 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_LastName_control_internalReadOnlyTextBox"));
	    				repData[4] = we3.getText();
	    				we7 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_Email_control"));
	    				email = we7.getText();
	    			} else {repData[12] = "MirrorFailedValidation";}
	        	} else if (repData[5].length() == 0 && repData[3].length() > 0 && repData[4].length() > 0) {
	        		driver.get("https://connectme.adt.com/IdentityManagement/aspx/common/GlobalSearchResult.aspx?searchtype=a35e1181-6020-4c13-b0c8-e8b18692359c&content=" + repData[4].trim() + ", " + repData[3].trim());	

	        		fPos = driver.getPageSource().indexOf("EditPerson.aspx?id=");
	    			lPos = driver.getPageSource().lastIndexOf("EditPerson.aspx?id=");
	    			count = 0;
	    			firstNamex = "";
	    			while ((fPos.intValue() == -1 && count.intValue() < 10) || (fPos.intValue() != -1 && fPos.intValue() != lPos.intValue() && count.intValue() < 10)) {
	    				System.out.println("problem encountered... waiting for user action");
	    				try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
	    				try {
	    					firstNamex = driver.findElement(By.name("ctl00$PlaceHolderMain$GlobalSearchResult$ResourceListView$listViewSearchControl$ctl00$searchContentTextBoxID")).getAttribute("value");
	    					fPos = driver.getPageSource().indexOf("EditPerson.aspx?id=");
	    					lPos = driver.getPageSource().lastIndexOf("EditPerson.aspx?id=");
	    				} catch (org.openqa.selenium.NoSuchElementException e) {System.out.println("error... ");}
	    				System.out.println("search keyed... "+ firstNamex);
	    				count++;				
	    			}
	    			
	    			if (fPos == driver.getPageSource().lastIndexOf("EditPerson.aspx?id=") && fPos != -1) {
	    				lPos = driver.getPageSource().substring(fPos).indexOf("'");				    				
	    				driver.get("https://connectme.adt.com/identitymanagement/aspx/Users/"+driver.getPageSource().substring(fPos, fPos+lPos));
	    				we7 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_Email_control"));
	    				repData[5] = we7.getText();
	    			} else {repData[12] = "MirrorFailedValidation";}
	        	}
				
// manager user logic				
	        	if (repData[8].length() > 0 && repData[6].length() == 0 && repData[7].length() == 0) {
	        		driver.get("https://connectme.adt.com/IdentityManagement/aspx/common/GlobalSearchResult.aspx?searchtype=a35e1181-6020-4c13-b0c8-e8b18692359c&content=" + repData[8].subSequence(0, repData[8].indexOf("@")));   
	        		
	    			fPos = driver.getPageSource().indexOf("EditPerson.aspx?id=");
	    			lPos = driver.getPageSource().lastIndexOf("EditPerson.aspx?id=");
	    			count = 0;
	    			firstNamex = "";
	    			while ((fPos.intValue() == -1 && count.intValue() < 10) || (fPos.intValue() != -1 && fPos.intValue() != lPos.intValue() && count.intValue() < 10)) {
	    				System.out.println("problem encountered... waiting for user action");
	    				try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
	    				try {
	    					firstNamex = driver.findElement(By.name("ctl00$PlaceHolderMain$GlobalSearchResult$ResourceListView$listViewSearchControl$ctl00$searchContentTextBoxID")).getAttribute("value");
	    					fPos = driver.getPageSource().indexOf("EditPerson.aspx?id=");
	    					lPos = driver.getPageSource().lastIndexOf("EditPerson.aspx?id=");
	    				} catch (org.openqa.selenium.NoSuchElementException e) {System.out.println("error... ");}
	    				System.out.println("manager search keyed... "+ firstNamex);
	    				count++;				
	    			}
	    			
	    			if (fPos == driver.getPageSource().lastIndexOf("EditPerson.aspx?id=") && fPos != -1) {
	    				lPos = driver.getPageSource().substring(fPos).indexOf("'");				    				
	    				driver.get("https://connectme.adt.com/identitymanagement/aspx/Users/"+driver.getPageSource().substring(fPos, fPos+lPos));
	    				we2 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_FirstName_control_internalReadOnlyTextBox"));
	    				repData[6] = we2.getText();
	    				we3 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_LastName_control_internalReadOnlyTextBox"));
	    				repData[7] = we3.getText();
	    				we7 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_Email_control"));
	    				email = we7.getText();
	    			} else {repData[15] = "ManagerFailedValidation";}
	        	} else if (repData[8].length() == 0 && repData[6].length() > 0 && repData[7].length() > 0) {
	        		driver.get("https://connectme.adt.com/IdentityManagement/aspx/common/GlobalSearchResult.aspx?searchtype=a35e1181-6020-4c13-b0c8-e8b18692359c&content=" + repData[7].trim() + ", " + repData[6].trim());	

	        		fPos = driver.getPageSource().indexOf("EditPerson.aspx?id=");
	    			lPos = driver.getPageSource().lastIndexOf("EditPerson.aspx?id=");
	    			count = 0;
	    			firstNamex = "";
	    			while ((fPos.intValue() == -1 && count.intValue() < 10) || (fPos.intValue() != -1 && fPos.intValue() != lPos.intValue() && count.intValue() < 10)) {
	    				System.out.println("problem encountered... waiting for user action");
	    				try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
	    				try {
	    					firstNamex = driver.findElement(By.name("ctl00$PlaceHolderMain$GlobalSearchResult$ResourceListView$listViewSearchControl$ctl00$searchContentTextBoxID")).getAttribute("value");
	    					fPos = driver.getPageSource().indexOf("EditPerson.aspx?id=");
	    					lPos = driver.getPageSource().lastIndexOf("EditPerson.aspx?id=");
	    				} catch (org.openqa.selenium.NoSuchElementException e) {System.out.println("error... ");}
	    				System.out.println("search keyed... "+ firstNamex);
	    				count++;				
	    			}
	    			
	    			if (fPos == driver.getPageSource().lastIndexOf("EditPerson.aspx?id=") && fPos != -1) {
	    				lPos = driver.getPageSource().substring(fPos).indexOf("'");				    				
	    				driver.get("https://connectme.adt.com/identitymanagement/aspx/Users/"+driver.getPageSource().substring(fPos, fPos+lPos));
	    				we7 = driver.findElement(By.id("ctl00_PlaceHolderMain_EditPerson_uoc_BasicInfo_grouping_Email_control"));
	    				repData[8] = we7.getText();
	    			} else {repData[15] = "ManagerFailedValidation";}
	        	}
				

	        	
			} else {
				System.out.println("Search value returned invalid results, nothing found or the value was not unique");
				repData[12] = "Search value returned invalid results, nothing found or the value was not unique";
			}
			
			driver.quit();
			System.out.println("Done, all closed");
			return repData;
		}
 }