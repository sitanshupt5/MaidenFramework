package Automation;

import Utitlities.BaseUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pageObjects.LandingPage;

import java.io.IOException;

public class Module1 extends BaseUtilities {


    public static Logger log = LogManager.getLogger(Module1.class.getName());
    String module = this.getClass().getName();

    @BeforeSuite
    public void setName()
    {
        setModuleName(module);
    }


    @BeforeClass
    public void launchBrowser() throws IOException
    {
        driver = initializeDriver();
        driver.manage().window().maximize();
    }


    @Test
    public void TestCase_01_LaunchWebsite()
    {
        test = reports.createTest("TestCase_01_LaunchWebsite");
        node = test.createNode("Open URL");
        driver.get(properties.getProperty("url"));
        log.trace("Driver is initiated."+"\n"+properties.getProperty("url")+ " is now open.");
        Assert.assertEquals(driver.getTitle(),driver.getTitle());
        WebDriverWait wait = new WebDriverWait(driver, 20);
        node = test.createNode("Close Popup").pass("Popup overlay should be removed");
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text() = 'NO THANKS']")));
        log.trace("Popup modal is displayed.");
        log.trace("Test case executed successfully");
    }

    @Test
    public void TestCase_02_OpenLoginPage()
    {
        test = reports.createTest("TestCase_02_OpenLoginPage");
        LandingPage landingPage = new LandingPage(driver);
        node = test.createNode("Close Popup").pass("Popup overlay should be removed");
        landingPage.ClosePopup().click();
        log.trace("Popup modal is clicked.");
        landingPage.LoginButton().click();
        log.trace("Login Page is now opened.");
        log.trace("Test case executed successfully");

    }

    @AfterClass
    public void terminateTest()
    {
        closeBrowser();
        System.out.println("after class executed");
    }
}
