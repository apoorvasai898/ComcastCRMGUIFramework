package com.comcast.hrm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.comcast.hrm.generic.webdriverutility.WebDriverUtility;

public class CreateProjectPage {
	@FindBy(name="projectName")
	private WebElement projNameEd;
	
	@FindBy(name="createdBy")
	private WebElement projManagerEd;
	
	@FindBy(xpath = "//label[contains(text(),'Project Sta')]/following-sibling::select")
	private WebElement statusSelect;
	
	@FindBy(xpath = "//input[contains(@value,'Add')]")
	private WebElement addProjectBtn;
	
	public CreateProjectPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	public WebElement getProjNameEd() {
		return projNameEd;
	}

	public WebElement getProjManagerEd() {
		return projManagerEd;
	}

	public WebElement getStatusSelect() {
		return statusSelect;
	}
	
	public WebElement getAddProjectBtn() {
		return addProjectBtn;
	}

	public void createProject(String projectName, String managerName ,WebDriver driver, String projStatus)
	{
		projNameEd.sendKeys(projectName);
		projManagerEd.sendKeys(managerName);
		Actions action = new Actions(driver);
		action.click(statusSelect).perform();
		WebDriverUtility wdUtility = new WebDriverUtility();
		wdUtility.select(statusSelect, projStatus);
		addProjectBtn.click();		
	}
}
