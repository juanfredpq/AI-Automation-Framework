package com.ai.automation.framework.pages;

import com.ai.automation.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    @FindBy(css = "h1")
    private WebElement heroTitle;

    public HomePage(WebDriver driver, int waitSeconds) {
        super(driver, waitSeconds);
        PageFactory.initElements(driver, this);
    }

    public String getHeroTitle() {
        return getText(heroTitle);
    }
}
