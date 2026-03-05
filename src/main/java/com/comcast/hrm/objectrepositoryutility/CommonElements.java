package com.comcast.hrm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.comcast.hrm.generic.webdriverutility.WebDriverUtility;

public class CommonElements {
	@FindBy(xpath = "//*[name()='svg' and @data-icon=\"right-from-bracket\"]")
	private WebElement logoutBtn;
	
	@FindBy(xpath = "//div[contains(@class,'toast--success')]")
	private WebElement createdPop;
	
	@FindBy(xpath = "//div[contains(@class,'toast--success')]/descendant::div[@role='alert']")
	private WebElement createdPopTextEl;
	
	public CommonElements(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	public WebElement getLogoutBtn() {
		return logoutBtn;
	}
	
	public WebElement getCreatedPop() {
		return createdPop;
	}
	
	public void verifyProjCtn(String projName) throws InterruptedException
	{
		Thread.sleep(2000);//Wait until the createdPop loads
		if(createdPopTextEl.getText().contains(projName))
		{
			System.out.println("Project Created Successfuly === PASS");
		}
		else {
			System.out.println("Project is not Created === FAIL");
		}
	}
	public void logoutApp(WebDriver driver)
	{
		WebDriverUtility wdUtility = new WebDriverUtility();
		try {
			wdUtility.waitForElementPresent(driver, createdPop);
			createdPop.click();
		}
		catch(Exception e)
		{
	
		}
		wdUtility.waitForElementToBeclickable(driver, logoutBtn);
		logoutBtn.click();
	}

}
