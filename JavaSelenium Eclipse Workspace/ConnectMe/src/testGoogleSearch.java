import org.openqa.selenium.By; 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class testGoogleSearch {
	
	public static void main(String[] args) throws Exception {
		testGoogleSearch http = new testGoogleSearch();
//		http.sendGet();
		String pathToFile = System.getProperty("user.dir");
		pathToFile = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
		pathToFile = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
		pathToFile = pathToFile + "\\Documents\\SalesforceIds.csv";
		System.out.println("Working Directory = " + pathToFile);
	}
	
	public void sendGet() {
		// Optional, if not specified, WebDriver will search your path for chromedriver.
		System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

		WebDriver driver = new ChromeDriver();
		driver.get("http://www.google.com/xhtml");
		try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("ChromeDriver");
		searchBox.submit();
		try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
		driver.quit();
	}
}