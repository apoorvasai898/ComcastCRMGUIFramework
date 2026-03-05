package com.comcast.hrm.projecttest;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.comcast.hrm.generic.databaseutility.DataBaseUtility;
import com.comcast.hrm.generic.fileutility.ExcelUtility;
import com.comcast.hrm.generic.fileutility.JSONUtility;
import com.comcast.hrm.generic.webdriverutility.JavaUtility;
import com.comcast.hrm.generic.webdriverutility.WebDriverUtility;
import com.comcast.hrm.objectrepositoryutility.CommonElements;
import com.comcast.hrm.objectrepositoryutility.CreateProjectPage;
import com.comcast.hrm.objectrepositoryutility.HomePage;
import com.comcast.hrm.objectrepositoryutility.LoginPage;
import com.comcast.hrm.objectrepositoryutility.ProjectsPage;

public class CreateProjectTest {
	@Test
	public void createProject() throws IOException, ParseException, InterruptedException, SQLException
	{
		final Map<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("credentials_enable_service", false);
		chromePrefs.put("profile.password_manager_enabled", false);
		chromePrefs.put("profile.password_manager_leak_detection", false); // <======== This is the important one

		final ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("prefs", chromePrefs);
		
				
		//Utilities
		JSONUtility jUtility= new JSONUtility();
		WebDriverUtility wdUtility = new WebDriverUtility();
		ExcelUtility exUtility = new ExcelUtility();
		JavaUtility javaUtility = new JavaUtility();
		
		DataBaseUtility dbutility = new DataBaseUtility();
		
		//Data from the common_data.json
		String BROWSER = jUtility.getDataFromJSONFile("browser");
		String URL = jUtility.getDataFromJSONFile("url");
		String USERNAME = jUtility.getDataFromJSONFile("username");
		String PASSWORD = jUtility.getDataFromJSONFile("password");
		String timeOut = jUtility.getDataFromJSONFile("timeOut");
		//Get randomNumber
		int randNumber = javaUtility.getRandomNumber();
		//Data from Excel .xlsx file
		String projName = exUtility.getDataFromExcel("proj", 2, 0).toString() + randNumber;
		String projMngName = exUtility.getDataFromExcel("proj", 2, 1).toString();
		String projStatus = exUtility.getDataFromExcel("proj", 2, 2).toString();
		
		//Open Browser
		WebDriver driver = null;
		if(BROWSER.equals("chrome"))
		{
			driver = new ChromeDriver(chromeOptions);
		}
		else if(BROWSER.equals("edge"))
		{
			driver = new EdgeDriver();
		}
		else {
			driver = new FirefoxDriver();
		}
		
		//Implict Wait and Maximize the Browser
		wdUtility.waitForPageToLoad(driver, timeOut);
		driver.manage().window().maximize();
		
		//Navigate to the Application
		driver.get(URL);
		
		//Login to the Application
		LoginPage lp = new LoginPage(driver);
		lp.signInToApp(USERNAME, PASSWORD);
		
//		//Click on Projects link
		HomePage hp = new HomePage(driver);
		hp.getProjectsLink().click();
		
		//Click on the Add Project button
		ProjectsPage pp = new ProjectsPage(driver);
		pp.getCreateProjBtn().click();
		
		//Create Project
		CreateProjectPage cpp = new CreateProjectPage(driver);
		cpp.createProject(projName, projMngName,driver , projStatus);
		
		//Verify Project Creation in Application
		CommonElements ce = new CommonElements(driver);
		ce.verifyProjCtn(projName);
		
		//Verify Project Creation in DataBase
		dbutility.getDbConnection("jdbc:mysql://49.249.28.218:3307/ninza_hrm","root@%","root");
		ResultSet resultset = dbutility.executeSelectQuery("select * from project");
		
		dbutility.ProjectIsCreatedOndb(resultset,projName);
		dbutility.closeDBConnection();
		//Logout
		ce.logoutApp(driver);
		
		//Close the Browser
		driver.quit();
	}
}
