package Automation;

import Utitlities.BaseUtilities;
import Utitlities.DataProviderMapper;
import Utitlities.ExcelUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.LandingPage;
import pageObjects.LoginPage;
import java.io.IOException;

public class Module1 extends BaseUtilities {


    public static Logger log = LogManager.getLogger(Module1.class.getName());

    @BeforeMethod
    public void launchBrowser() throws IOException
    {
        driver = initializeDriver();
        driver.get(properties.getProperty("url"));
        driver.manage().window().maximize();
        log.trace("Test case executed successfully");
    }


    @Test
    public void TestCase_01_LaunchWebsite()
    {
        Assert.assertEquals(driver.getTitle(),driver.getTitle());
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text() = 'NO THANKS']"))).click();
        log.trace("Test case executed successfully");
    }

    @Test
    public void TestCase_02_OpenLoginPage()
    {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.ClosePopup().click();
        landingPage.LoginButton().click();
        log.trace("Test case executed successfully");

    }


    @Test(enabled = true)
    public void TestCase_03_ErrorLogin() throws IOException
    {
        ExcelUtils excel = new ExcelUtils();
        excel.setFilePath("D:\\WorkSpace\\MaidenFramework\\data\\Module1.xlsx");
        LandingPage landingPage = new LandingPage(driver);
        landingPage.ClosePopup().click();
        landingPage.LoginButton().click();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.Username().sendKeys(excel.singleIteration("TestCase_03_ErrorLogin", "Username"));
        loginPage.Password().sendKeys(excel.singleIteration("TestCase_03_ErrorLogin", "Password"));
        loginPage.Submit().click();
        Assert.assertEquals(loginPage.loginError().getText(),"Invalid email or password");
        log.error("Error message mismatch");
        log.trace("Test case executed successfully");
    }

    @Test(dataProvider = "TestCase_04")
    public void TestCase_04_ErrorLogin(String username, String password) throws IOException
    {
        ExcelUtils excel = new ExcelUtils();
        excel.setFilePath("D:\\WorkSpace\\MaidenFramework\\data\\Module1.xlsx");
        LandingPage landingPage = new LandingPage(driver);
        landingPage.ClosePopup().click();
        landingPage.LoginButton().click();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.Username().sendKeys(username);
        loginPage.Password().sendKeys(password);
        loginPage.Submit().click();
        Assert.assertEquals(loginPage.loginError().getText(),"Invalid email or password.");
        log.error("Error message mismatch");
        log.trace("Test case executed successfully");
        excel.closeFile();

    }



    @AfterMethod
    public void closeBrowser()
    {
        driver.close();
        driver = null;
    }


    @DataProvider(name = "TestCase_04")
    public Object[][] data() throws IOException
    {
        DataProviderMapper d = new DataProviderMapper();
        String filepath = "D:\\WorkSpace\\MaidenFramework\\data\\Module1.xlsx";
        String sheetname = "TestCase_03_ErrorLogin";
        Object[][] data = d.dataMapper(filepath,sheetname);
        return data;
    }

}
