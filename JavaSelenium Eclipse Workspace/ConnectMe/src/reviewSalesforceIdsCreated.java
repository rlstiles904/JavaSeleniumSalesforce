import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class reviewSalesforceIdsCreated {
 
    static final String[] USERNAME       = {"xxxxxxxx@adt.com.practice","xxxxxxxx@adt.com.adttest1","xxxxxxxx@adt.com.adttest2","xxxxxxxx@adt.com.adttest3","xxxxxxxx@adt.com.adthstage","xxxxxxxx@adt.com.adtprf"};
    static final String[] PASSWORD       = {"xxxxxxxxZvYjYOghwL4qHIYoAxx3Ph9F","xxxxxxxxREw46ixVry2jqAMDwIjrbQHru","xxxxxxxxqENPy8jZJMDO3ClTjQUEfL2au","xxxxxxxxFhkmK4G2hdUEONApeo1vuvvpb","xxxxxxxxi38HnGyA96Xi7ebN0imy1XijV","xxxxxxxxWSjSxA6lzaYROZT6ClP7mNGJX"};
    static final String[] LOGINURL       = {"https://adt--practice.cs50.my.salesforce.com","https://adt--adttest1.cs71.my.salesforce.com","https://adt--adttest2.cs50.my.salesforce.com","https://adt--adttest3.cs15.my.salesforce.com","https://adt--adthstage.cs23.my.salesforce.com","https://adt--adtprf.cs93.my.salesforce.com"};       
    static String USERNAMEx     = "";
    static String PASSWORDx     = "";
    static String LOGINURLx     = "";    
    private static String SOAP_ENDPOINT = "/services/Soap/c";
    private static String API_VERSION = "/46.0";
    private static String soapUri;   
    static Integer z = 0;
    static WebDriver driver = null;
    static String pathToFile = System.getProperty("user.dir");
    
    public static void main(String[] args) {
    	pathToFile = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
		pathToFile = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
		pathToFile = pathToFile + "\\Documents\\SalesforceIds.csv";             
    	
 		File csvFile = new File(pathToFile);  
 		if (csvFile.isFile()) {
 			try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
 				String line;
 				while ((line = br.readLine()) != null) {
 					String[] values = line.split(",");
 					if(values.length == 12) {
 						values = new String[] {values[0],values[1],values[2],values[3],values[4],values[5],values[6],values[7],values[8],values[9],values[10],values[11],"","","","","","","","","","","","",""};
 					} 
 					if(values[0].indexOf("FirstName") >= 0) {
 					} else {
 						if(values[12].length() == 18) {
 							reviewUserIds(values);
 						}
					}
				}
			} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}	 				
		}                 
    }         
    
    public static void reviewUserIds(String[] repData) {
   	
    	for (int h=0; h < repData[11].length(); h++) {
    		if (repData[11].substring(h, h+1).indexOf("0") != -1) {
    			soapUri = LOGINURL[0] + SOAP_ENDPOINT + API_VERSION;
    			LOGINURLx = LOGINURL[0];
    			USERNAMEx = USERNAME[0];
    			PASSWORDx = PASSWORD[0];
    		} else if (repData[11].substring(h, h+1).indexOf("1") != -1) {
    			soapUri = LOGINURL[1] + SOAP_ENDPOINT + API_VERSION;
    			LOGINURLx = LOGINURL[1];
    			USERNAMEx = USERNAME[1];
    			PASSWORDx = PASSWORD[1];
    		} else if (repData[11].substring(h, h+1).indexOf("2") != -1) {
    	    	soapUri = LOGINURL[2] + SOAP_ENDPOINT + API_VERSION;
    			LOGINURLx = LOGINURL[2];
    			USERNAMEx = USERNAME[2];
    			PASSWORDx = PASSWORD[2];
    	    } else if (repData[11].substring(h, h+1).indexOf("3") != -1) {
    	    	soapUri = LOGINURL[3] + SOAP_ENDPOINT + API_VERSION;
    			LOGINURLx = LOGINURL[3];
    			USERNAMEx = USERNAME[3];
    			PASSWORDx = PASSWORD[3];
    	    } else if (repData[11].substring(h, h+1).indexOf("S") != -1) {
    	    	soapUri = LOGINURL[4] + SOAP_ENDPOINT + API_VERSION;
    			LOGINURLx = LOGINURL[4];
    			USERNAMEx = USERNAME[4];
    			PASSWORDx = PASSWORD[4];
    	    } else if (repData[11].substring(h, h+1).indexOf("P") != -1) {
    	    	soapUri = LOGINURL[5] + SOAP_ENDPOINT + API_VERSION;
    			LOGINURLx = LOGINURL[5];
    			USERNAMEx = USERNAME[5];
    			PASSWORDx = PASSWORD[5];
    	    }
    	
    		System.out.println(soapUri);
   					
    		if (driver == null) {
//    			System.setProperty("webdriver.chrome.driver", "C:/Users/rstiles/Downloads/chromedriver_win32/chromedriver.exe");
    			driver = new ChromeDriver();
    			driver.get(LOGINURLx+"/"+repData[12].substring(0, repData[12].length()-3)+"?noredirect=1&isUserEntityOverride=1");
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
    		} else {
//    			WebElement body = driver.findElement(By.cssSelector("body"));
//    			body.sendKeys(Keys.CONTROL + "t");		
//    			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
    			((JavascriptExecutor)driver).executeScript("window.open()");
    			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
    			z=z+1;
    			driver.switchTo().window(tabs.get(z));
    			
    			driver.get(LOGINURLx+"/"+repData[12].substring(0, repData[12].length()-3)+"?noredirect=1&isUserEntityOverride=1");
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
    		}    				    		
    	}
 	}
}