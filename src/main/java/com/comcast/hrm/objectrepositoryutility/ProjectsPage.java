package com.comcast.hrm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProjectsPage {
	@FindBy(xpath = "//span[text()='Create Project']/preceding-sibling::i")
	private WebElement createProjBtn;
	
	public ProjectsPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	public WebElement getCreateProjBtn() {
		return createProjBtn;
	}

}
